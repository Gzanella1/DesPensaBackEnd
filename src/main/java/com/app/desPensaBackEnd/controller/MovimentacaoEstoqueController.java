package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.MovimentacaoEstoqueDTO;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;
import com.app.desPensaBackEnd.view.services.MovimentacaoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin("*")
public class MovimentacaoEstoqueController {

    @Autowired
    private MovimentacaoEstoqueService movimentacaoService;

    @PostMapping
    public MovimentacaoEstoqueDTO criar(@RequestBody MovimentacaoEstoqueDTO dto) {
        return movimentacaoService.criar(dto);
    }

    @PutMapping("/{id}")
    public MovimentacaoEstoqueDTO atualizar(@PathVariable Long id, @RequestBody MovimentacaoEstoqueDTO dto) {
        return movimentacaoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        movimentacaoService.deletar(id);
    }

    @GetMapping("/{id}")
    public MovimentacaoEstoqueDTO buscar(@PathVariable Long id) {
        return movimentacaoService.buscarPorId(id);
    }

    @GetMapping
    public List<MovimentacaoEstoqueDTO> listarTodos() {
        return movimentacaoService.listarTodos();
    }

    @GetMapping("/estoque/{estoqueId}")
    public List<MovimentacaoEstoqueDTO> listarPorEstoque(@PathVariable Long estoqueId) {
        return movimentacaoService.listarPorEstoque(estoqueId);
    }









}
