package com.app.desPensaBackEnd.controller;


import com.app.desPensaBackEnd.model.dto.EstoqueDetalhadoDTO;
import com.app.desPensaBackEnd.model.dto.MovimentacaoEstoqueDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.repository.MovimentacaoEstoqueRepository;
import com.app.desPensaBackEnd.view.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    // Injete o repositório de movimentações se ainda não tiver
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;


    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    // Endpoint para consumir um alimento
    // Exemplo de URL: POST http://localhost:8080/api/estoque/consumir/1?quantidade=2
    /**
     * Rota para CONSUMIR um alimento.
     * Diminui a quantidade e gera alerta se o estoque ficar baixo.
     * Exemplo de uso: POST /api/estoque/consumir/26?quantidade=2
     */
    @PostMapping("/consumir/{id}")
    public ResponseEntity<AlimentoEntity> consumirAlimento(
            @PathVariable Long id,
            @RequestParam int quantidade) {

        AlimentoEntity alimentoAtualizado = estoqueService.consumirAlimento(id, quantidade);
        return ResponseEntity.ok(alimentoAtualizado);
    }

    // NOVO Endpoint para visualizar o Estoque ncom seus Alertas
    // URL: http://localhost:8080/api/estoque/1
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDetalhadoDTO> buscarPorId(@PathVariable Long id) {
        EstoqueDetalhadoDTO dto = estoqueService.buscarEstoquePorId(id);
        return ResponseEntity.ok(dto);
    }


    /**
     * Rota para REABASTECER um alimento.
     * Aumenta a quantidade e remove alertas de "Estoque Baixo" se a quantidade ficar segura.
     * Exemplo de uso: POST /api/estoque/reabastecer/26?quantidade=10
     */
    @PostMapping("/reabastecer/{id}")
    public ResponseEntity<AlimentoEntity> reabastecerAlimento(
            @PathVariable Long id,
            @RequestParam int quantidade) {
        AlimentoEntity alimento = estoqueService.reabastecerAlimento(id, quantidade);
        return ResponseEntity.ok(alimento);
    }





}