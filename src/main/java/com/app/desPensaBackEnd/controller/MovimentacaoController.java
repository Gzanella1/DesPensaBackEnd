package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.MovimentacaoDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin(origins = "*") // Permite acesso do front
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    // POST: Realizar Entrada ou Saída
    @PostMapping
    public ResponseEntity<?> criarMovimentacao(@RequestBody MovimentacaoDTO dto) {
        try {
            MovimentacaoEstoqueEntity novaMov = service.registrarMovimentacao(dto);
            return ResponseEntity.ok(novaMov);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET: Histórico de um Estoque
    @GetMapping("/estoque/{idEstoque}")
    public ResponseEntity<List<MovimentacaoEstoqueEntity>> listarHistorico(@PathVariable Long idEstoque) {
        return ResponseEntity.ok(service.listarPorEstoque(idEstoque));
    }


}