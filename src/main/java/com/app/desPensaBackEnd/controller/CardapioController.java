package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.enums.TipoRefeicao;
import com.app.desPensaBackEnd.model.dto.cardapio.SugestaoCardapioDTO;
import com.app.desPensaBackEnd.model.entity.CardapioEntity;
import com.app.desPensaBackEnd.view.services.CardapioGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cardapios")
public class CardapioController {

    private final CardapioGeneratorService cardapioService;

    // Injeção de dependência
    public CardapioController(CardapioGeneratorService cardapioService) {
        this.cardapioService = cardapioService;
    }

    // -------------------------------------------------------
    // PASSO 1: Buscar Sugestões (GET)
    // Exemplo URL: http://localhost:8080/api/cardapios/sugestoes?estoqueId=1&intolerancias=gluten
    // -------------------------------------------------------
    @GetMapping("/sugestoes")
    public ResponseEntity<?> listarSugestoes(
            @RequestParam Long estoqueId,
            @RequestParam(required = false) String intolerancias) {

        try {
            List<SugestaoCardapioDTO> sugestoes = cardapioService.sugerirReceitas(estoqueId, intolerancias);
            return ResponseEntity.ok(sugestoes);
        } catch (RuntimeException e) {
            // Retorna 400 Bad Request com a mensagem amigável (ex: "Estoque vazio")
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Retorna 500 se for um erro técnico grave
            return ResponseEntity.internalServerError().body("Erro interno ao buscar sugestões: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // PASSO 2: Escolher, Salvar e Baixar Estoque (POST)
    // Exemplo URL: http://localhost:8080/api/cardapios/escolher
    // Params no Postman: idReceitaApi, instituicaoId, tipo
    // -------------------------------------------------------
    @PostMapping("/escolher")
    public ResponseEntity<?> escolherCardapio(
            @RequestParam Long idReceitaApi,
            @RequestParam Long instituicaoId,
            @RequestParam TipoRefeicao tipo) {

        try {
            CardapioEntity cardapioSalvo = cardapioService.salvarCardapioEscolhido(idReceitaApi, instituicaoId, tipo);
            return ResponseEntity.ok(cardapioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o erro no console para você ver o detalhe
            return ResponseEntity.internalServerError().body("Erro ao salvar cardápio e baixar estoque: " + e.getMessage());
        }
    }
}