package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.app.desPensaBackEnd.enums.TipoRefeicao;
import com.app.desPensaBackEnd.model.dto.cardapio.*;
import com.app.desPensaBackEnd.model.entity.*;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import com.app.desPensaBackEnd.view.repository.CardapioRepository;
import com.app.desPensaBackEnd.view.repository.InstituicaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardapioGeneratorService {

    @Value("${spoonacular.api.key}")
    private String apiKey;

    private final String API_FIND_BY_INGREDIENTS = "https://api.spoonacular.com/recipes/findByIngredients";

    private final AlimentoRepository alimentoRepository;
    private final CardapioRepository cardapioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final EstoqueService estoqueService;

    public CardapioGeneratorService(AlimentoRepository alimentoRepository,
                                    CardapioRepository cardapioRepository,
                                    InstituicaoRepository instituicaoRepository,
                                    EstoqueService estoqueService) {
        this.alimentoRepository = alimentoRepository;
        this.cardapioRepository = cardapioRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.estoqueService = estoqueService;
    }

    // -----------------------------------------------------------------------
    // 1. SUGERIR RECEITAS (AGORA COM ALEATORIEDADE E CONTEXTO)
    // -----------------------------------------------------------------------
    public List<SugestaoCardapioDTO> sugerirReceitas(Long estoqueId, TipoRefeicao tipoRefeicao) {

        // 1. Busca TUDO do banco que tem quantidade
        List<AlimentoEntity> todosAlimentos = alimentoRepository.findByEstoqueIdEstoqueAndQuantidadeGreaterThan(estoqueId, 0);

        if (todosAlimentos.isEmpty()) {
            throw new RuntimeException("Estoque vazio! Adicione alimentos para gerar sugestões.");
        }

        // 2. FILTRAGEM INTELIGENTE DE INGREDIENTES
        // Filtra os ingredientes baseados no tipo de refeição (ex: não mandar carne crua pro café da manhã)
        List<String> ingredientesSelecionados = todosAlimentos.stream()
                .filter(a -> a.getNome() != null && !a.getNome().isBlank())
                .filter(a -> filtrarIngredientePorContexto(a.getCategoria(), tipoRefeicao))
                .map(AlimentoEntity::getNome)
                .map(String::toLowerCase)
                .map(String::trim)
                .distinct() // Evita duplicatas (ex: Arroz marca A e Arroz marca B vira só "arroz")
                .limit(15) // Envia mais ingredientes para a API ter mais opções
                .collect(Collectors.toList());

        // Fallback: Se o filtro for muito restrito e não sobrar nada, usa tudo que tem.
        if (ingredientesSelecionados.isEmpty()) {
            ingredientesSelecionados = todosAlimentos.stream()
                    .map(AlimentoEntity::getNome)
                    .limit(5)
                    .collect(Collectors.toList());
        }

        String ingredientesParam = String.join(",", ingredientesSelecionados);

        // 3. BUSCA NA API (Pedindo MAIS resultados para poder randomizar)
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_FIND_BY_INGREDIENTS)
                .queryParam("ingredients", ingredientesParam)
                .queryParam("number", 20) // <--- TRUQUE: Pede 20 receitas
                .queryParam("ranking", 1) // 1 = Maximizar uso dos ingredientes (Geladeira Mode)
                .queryParam("ignorePantry", true)
                .queryParam("apiKey", apiKey);

        try {
            SpoonacularRecipeDTO[] response = restTemplate.getForObject(builder.toUriString(), SpoonacularRecipeDTO[].class);

            if (response == null || response.length == 0) {
                return new ArrayList<>();
            }

            // 4. EMBARALHAMENTO (SHUFFLE)
            // Converte array para lista para poder manipular
            List<SpoonacularRecipeDTO> listaReceitas = new ArrayList<>(Arrays.asList(response));

            // Se tivermos mais de 3 opções, embaralhamos para o usuário ver coisas diferentes
            if (listaReceitas.size() > 1) {
                Collections.shuffle(listaReceitas);
            }

            // Pegamos apenas as top 3 DEPOIS de embaralhar
            List<SpoonacularRecipeDTO> top3Aleatorio = listaReceitas.stream()
                    .limit(12)
                    .collect(Collectors.toList());

            // 5. Converte para DTO de visualização
            List<SugestaoCardapioDTO> sugestoes = new ArrayList<>();
            for (SpoonacularRecipeDTO rec : top3Aleatorio) {
                SugestaoCardapioDTO s = new SugestaoCardapioDTO();
                s.setIdReceitaApi(rec.getId());
                s.setNomeReceita(rec.getTitle());
                s.setUrlImagem(rec.getImage());
                // Opcional: Adicionar contagem de ingredientes usados/faltantes aqui para mostrar no front
                sugestoes.add(s);
            }
            return sugestoes;

        } catch (Exception e) {
            e.printStackTrace();
            // Em produção, use um Logger, não sysout/stacktrace
            throw new RuntimeException("Erro ao comunicar com serviço de receitas.");
        }
    }

    /**
     * Lógica avançada para decidir quais ingredientes enviar para a API
     * baseado no momento do dia (Almoço vs Café).
     */
    private boolean filtrarIngredientePorContexto(CategoriaAlimento categoria, TipoRefeicao tipo) {
        if (categoria == null) return true; // Na dúvida, manda.
        if (tipo == null) return true;

        String cat = categoria.name().toUpperCase();

        // Lógica para ALMOÇO e JANTAR (Foco em comida "de sal")
        if (tipo == TipoRefeicao.ALMOCO || tipo == TipoRefeicao.JANTA) {
            // Evita frutas doces ou ingredientes de confeitaria como base principal
            // (A não ser que sua CategoriaAlimento seja muito específica, adapte aqui)
            return !cat.equals("DOCE") && !cat.equals("FRUTA");
        }

        // Lógica para CAFÉ DA MANHÃ e LANCHE
        if (tipo == TipoRefeicao.CAFE ) {
            return cat.equals("LATICINIO") ||
                    cat.equals("CEREAL") ||
                    cat.equals("FRUTA") ||
                    cat.equals("PADARIA") ||
                    cat.equals("DOCE");
        }

        return true;
    }

    // -----------------------------------------------------------------------
    // 2. ESCOLHER E SALVAR (POST) - Mantido similar, com pequenas proteções
    // -----------------------------------------------------------------------
    @Transactional
    public CardapioEntity salvarCardapioEscolhido(Long idReceitaEscolhida, Long idInstituicao, TipoRefeicao tipo) {

        String urlDetalhes = "https://api.spoonacular.com/recipes/" + idReceitaEscolhida + "/information?includeNutrition=false&apiKey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();

        DetalheReceitaDTO detalhes;
        try {
            detalhes = restTemplate.getForObject(urlDetalhes, DetalheReceitaDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao buscar receita: " + e.getMessage());
        }

        if (detalhes == null) throw new RuntimeException("Receita não encontrada na API.");

        InstituicaoEntity instituicao = instituicaoRepository.findById(idInstituicao)
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));

        CardapioEntity cardapio = new CardapioEntity();
        cardapio.setDataGeracaoCardapio(LocalDate.now());
        cardapio.setTipoRefeicao(tipo);
        cardapio.setInstituicao(instituicao);
        cardapio.setNomeReceitaEscolhida(detalhes.getTitle());
        cardapio.setIdReceitaApi(detalhes.getId());

        List<ItemCardapio> ingredientesUsados = new ArrayList<>();
        List<AlimentoEntity> estoqueAtual = alimentoRepository.findAll();

        for (IngredienteDTO ingApi : detalhes.getIngredientesEstendidos()) {
            // Busca "Fuzzy" simples
            AlimentoEntity alimentoBanco = estoqueAtual.stream()
                    .filter(a -> matchIngrediente(a.getNome(), ingApi.getName()))
                    .findFirst().orElse(null);

            if (alimentoBanco != null) {
                ItemCardapio item = new ItemCardapio();
                item.setAlimento(alimentoBanco);
                item.setQuantidadeUsada(ingApi.getAmount());
                item.setUnidade(ingApi.getUnit());
                item.setCardapio(cardapio);
                ingredientesUsados.add(item);

                int qtdParaBaixar = (int) Math.ceil(ingApi.getAmount());
                // Proteção para não baixar negativo
                if(qtdParaBaixar < 1) qtdParaBaixar = 1;

                estoqueService.consumirAlimento(alimentoBanco.getIdAlimento(), qtdParaBaixar);
            }
        }

        cardapio.setIngredientesUsados(ingredientesUsados);
        return cardapioRepository.save(cardapio);
    }

    // Método auxiliar para comparar nomes (pode evoluir para Levenshtein no futuro)
    private boolean matchIngrediente(String nomeBanco, String nomeApi) {
        if (nomeBanco == null || nomeApi == null) return false;
        String b = nomeBanco.toLowerCase();
        String a = nomeApi.toLowerCase();
        return b.equals(a) || b.contains(a) || a.contains(b);
    }

    public DetalheReceitaDTO buscarDetalhesReceita(Long idReceita) {
        String url = "https://api.spoonacular.com/recipes/" + idReceita + "/information?includeNutrition=false&apiKey=" + apiKey;
        return new RestTemplate().getForObject(url, DetalheReceitaDTO.class);
    }
}