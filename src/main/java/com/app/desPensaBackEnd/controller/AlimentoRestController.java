package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.view.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

public class AlimentoRestController {
	}

	@Autowired
	private AlimentoService alimentoService;

// GET - Listar Todos

@GetMapping
public List<Alimento> listartodos( ) {
	return alimentoService.listarTodos ( );{
}
	
// GET - Buscar por ID

@GetMapping("/{id}")
public Alimento buscarPorId(@PathVariable Long id) {
	return alimentoService.buscarPorId(id);
}

// POST - criar novo alimento
@PostMapping
public Alimento criar(@RequestBody Alimento alimento) {
    return alimentoService.salvar(alimento);
}

// PUT - atualizar alimento
@PutMapping("/{id}")
public Alimento atualizar(
        @PathVariable Long id,
        @RequestBody Alimento alimentoAtualizado
) {
    return alimentoService.atualizar(id, alimentoAtualizado);
}

// DELETE - remover alimento
@DeleteMapping("/{id}")
public void deletar(@PathVariable Long id) {
    alimentoService.deletar(id);
}
}