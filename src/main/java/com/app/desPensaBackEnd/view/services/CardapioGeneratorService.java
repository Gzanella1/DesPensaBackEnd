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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioGeneratorService {

    @Value("${spoonacular.api.key}")
    private String apiKey;

    // ENDPOINT DA "GELADEIRA" (O QUE TEM NA DESPENSA)
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
    // 1. SUGERIR RECEITAS (GET) - Usando findByIngredients
    // -----------------------------------------------------------------------
    public List<SugestaoCardapioDTO> sugerirReceitas(Long estoqueId, String intolerancias) {

        // 1. Busca TUDO do banco (com quantidade positiva)
        List<AlimentoEntity> todosAlimentos = alimentoRepository.findByEstoqueIdEstoqueAndQuantidadeGreaterThan(estoqueId, 0);

        if (todosAlimentos.isEmpty()) {
            throw new RuntimeException("Estoque vazio! Adicione alimentos.");
        }

        // 2. FILTRO DE CATEGORIA (Para não misturar sobremesa com almoço)
        // Se for buscar Janta/Almoço, ignoramos frutas e doces na busca
        List<String> ingredientesPrincipais = todosAlimentos.stream()
                .filter(a -> a.getNome() != null && !a.getNome().isEmpty())
                .filter(a -> isIngredientePrincipal(a.getCategoria())) // Usa o Enum para filtrar Morango
                .map(AlimentoEntity::getNome)
                .map(String::toLowerCase)
                .limit(10) // Pode mandar mais ingredientes nesse endpoint (até 10 é seguro)
                .collect(Collectors.toList());

        String ingredientesParam;

        // Lógica de Fallback: Se só tiver fruta, manda fruta. Se tiver comida, manda comida.
        if (!ingredientesPrincipais.isEmpty()) {
            ingredientesParam = String.join(",", ingredientesPrincipais);
        } else {
            ingredientesParam = todosAlimentos.stream()
                    .map(AlimentoEntity::getNome)
                    .map(String::toLowerCase)
                    .limit(5)
                    .collect(Collectors.joining(","));
        }

        // 3. Monta a URL (findByIngredients)
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_FIND_BY_INGREDIENTS)
                .queryParam("ingredients", ingredientesParam)
                .queryParam("number", 3)
                .queryParam("ranking", 1) // 1 = Maximizar o uso do que eu tenho (Geladeira Mode)
                .queryParam("ignorePantry", true)
                .queryParam("apiKey", apiKey);

        String uri = builder.toUriString();
        System.out.println("--------------------------------------------------");
        System.out.println("Ingredientes enviados: " + ingredientesParam);
        System.out.println("URL API GERADA: " + uri);
        System.out.println("--------------------------------------------------");

        try {
            // Esse endpoint retorna um ARRAY, não um objeto complexo.
            SpoonacularRecipeDTO[] response = restTemplate.getForObject(uri, SpoonacularRecipeDTO[].class);

            if (response == null || response.length == 0) {
                // Retorna lista vazia em vez de erro 500
                return new ArrayList<>();
            }

            // 4. Converte para DTO de visualização
            List<SugestaoCardapioDTO> sugestoes = new ArrayList<>();
            for (SpoonacularRecipeDTO rec : response) {
                SugestaoCardapioDTO s = new SugestaoCardapioDTO();
                s.setIdReceitaApi(rec.getId());
                s.setNomeReceita(rec.getTitle());
                s.setUrlImagem(rec.getImage());
                sugestoes.add(s);
            }
            return sugestoes;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro na API Externa: " + e.getMessage());
        }
    }

    // Método para ignorar Morango/Doces na busca de prato principal
    private boolean isIngredientePrincipal(CategoriaAlimento categoria) {
        if (categoria == null) return true;
        String cat = categoria.name().toUpperCase();
        return cat.equals("PROTEINA") ||
                cat.equals("CEREAL") ||
                cat.equals("LEGUME") ||
                cat.equals("VERDURA") ||
                cat.equals("CARNE") ||
                cat.equals("LATICINIO");
    }

    // -----------------------------------------------------------------------
    // 2. ESCOLHER E SALVAR (POST) - Baixa Estoque
    // -----------------------------------------------------------------------
    @Transactional
    public CardapioEntity salvarCardapioEscolhido(Long idReceitaEscolhida, Long idInstituicao, TipoRefeicao tipo) {

        // 1. Busca detalhes técnicos (Ingredientes e quantidades)
        // endpoint /information é necessário para saber QUANTO baixar
        RestTemplate restTemplate = new RestTemplate();
        String urlDetalhes = "https://api.spoonacular.com/recipes/" + idReceitaEscolhida + "/information?includeNutrition=false&apiKey=" + apiKey;

        DetalheReceitaDTO detalhes;
        try {
            detalhes = restTemplate.getForObject(urlDetalhes, DetalheReceitaDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar detalhes da receita: " + e.getMessage());
        }

        if (detalhes == null) throw new RuntimeException("Detalhes não encontrados.");

        // 2. Cria cabeçalho
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

        // 3. Match com estoque e Baixa
        for (IngredienteDTO ingApi : detalhes.getIngredientesEstendidos()) {

            // Busca flexível
            AlimentoEntity alimentoBanco = estoqueAtual.stream()
                    .filter(a -> a.getNome() != null &&
                            (a.getNome().equalsIgnoreCase(ingApi.getName()) ||
                                    a.getNome().toLowerCase().contains(ingApi.getName().toLowerCase()) ||
                                    ingApi.getName().toLowerCase().contains(a.getNome().toLowerCase())))
                    .findFirst().orElse(null);

            if (alimentoBanco != null) {
                ItemCardapio item = new ItemCardapio();
                item.setAlimento(alimentoBanco);
                item.setQuantidadeUsada(ingApi.getAmount());
                item.setUnidade(ingApi.getUnit());
                item.setCardapio(cardapio);
                ingredientesUsados.add(item);

                // Chama o EstoqueService para baixar e gerar alerta
                int qtdParaBaixar = (int) Math.ceil(ingApi.getAmount());
                estoqueService.consumirAlimento(alimentoBanco, qtdParaBaixar);
            }
        }

        cardapio.setIngredientesUsados(ingredientesUsados);
        return cardapioRepository.save(cardapio);
    }
}