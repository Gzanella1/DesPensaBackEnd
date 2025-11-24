package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.PessoaDTO;
import com.app.desPensaBackEnd.model.entity.PessoaEntity;
import com.app.desPensaBackEnd.view.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*") // Permite requisições do Frontend (React/HTML)
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    // 1. LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listar() {
        List<PessoaEntity> lista = pessoaService.listarTodas();

        // Converte a lista de Entidades para DTOs
        // Usamos lambda explicitamente para evitar ambiguidade de construtores
        List<PessoaDTO> dtos = lista.stream()
                .map(entity -> new PessoaDTO(entity))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // 2. BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        try {
            PessoaEntity entity = pessoaService.buscarPorId(id);
            return ResponseEntity.ok(new PessoaDTO(entity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. CRIAR
    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@RequestBody @Valid PessoaDTO dto) {
        PessoaEntity salvo = pessoaService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PessoaDTO(salvo));
    }

    // 4. ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PessoaDTO dto) {
        try {
            PessoaEntity salvo = pessoaService.atualizar(id, dto);
            return ResponseEntity.ok(new PessoaDTO(salvo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            pessoaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}