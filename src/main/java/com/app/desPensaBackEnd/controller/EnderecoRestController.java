package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.EnderecoDTO;
import com.app.desPensaBackEnd.view.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/endereco")
@CrossOrigin(origins = "*")
public class EnderecoRestController {

    @Autowired
    private EnderecoService enderecoService;

    /**
     * GET - Lista todos os endereços
     */
    @GetMapping
    public ResponseEntity<Set<EnderecoDTO>> listarTodos() {
        return ResponseEntity.ok(enderecoService.buscarEnderecos());
    }

    /**
     * GET - Buscar endereço por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Long id) {
        EnderecoDTO dto = enderecoService.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    /**
     * POST - Cadastrar endereço vinculado a uma instituição
     */
    @PostMapping("/instituicao/{idInstituicao}")
    public ResponseEntity<EnderecoDTO> cadastrar(
            @PathVariable Long idInstituicao,
            @RequestBody EnderecoDTO dto
    ) {
        dto.setIdInstituicao(idInstituicao);
        EnderecoDTO resposta = enderecoService.cadastrar(dto);
        return ResponseEntity.ok(resposta);
    }

    /**
     * PUT - Atualizar um endereço
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody EnderecoDTO dto
    ) {
        EnderecoDTO atualizado = enderecoService.atualizar(id, dto);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    /**
     * DELETE - Excluir endereço
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        boolean removido = enderecoService.deletar(id);
        if (removido) {
            return ResponseEntity.ok("Endereço removido com sucesso!");
        }
        return ResponseEntity.notFound().build();
    }
}
