package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.view.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    // --- READ ALL ---
    @GetMapping(value = "/buscarAll")
    public ResponseEntity<Set<UsuarioDTO>> retornarUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarUsuarios());
    }

    // --- READ ONE (Por ID) ---
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // --- CREATE ---
    @PostMapping(value = "/cadastro", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastrar(@RequestBody UsuarioDTO cadastro) {
        try {
            usuarioService.cadastrar(cadastro);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar: " + e.getMessage());
        }
    }

    // --- UPDATE ---
    @PutMapping(value = "/atualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO cadastro) {
        UsuarioDTO atualizado = usuarioService.atualizar(id, cadastro);
        return ResponseEntity.ok(atualizado);
    }

    // --- DELETE ---

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build(); // Retorna 204 (Sucesso sem conteúdo)
        } catch (RuntimeException e) {
            // Se não encontrar, retorna 404 e a mensagem do erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}