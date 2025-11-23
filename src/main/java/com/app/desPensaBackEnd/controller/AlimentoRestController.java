package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.AlimentoDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.view.services.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimentos") // endpoint base
public class AlimentoRestController {
/*
	@Autowired
	private AlimentoService alimentoService;

	// GET - Listar Todos
	@GetMapping
	public List<AlimentoDTO> listarTodos() {
		return alimentoService.listarTodos();
	}

	// GET - Buscar por ID
	@GetMapping("/{id}")
	public AlimentoDTO buscarPorId(@PathVariable Long id) {
		return alimentoService.buscarPorId(id);
	}

	// POST - Criar novo alimento
	@PostMapping
	public AlimentoDTO criar(@RequestBody AlimentoDTO alimento) {
		return alimentoService.salvar(alimento);
	}

	// PUT - Atualizar alimento
	@PutMapping("/{id}")
	public AlimentoDTO atualizar(
			@PathVariable Long id,
			@RequestBody AlimentoDTO alimentoAtualizado
	) {
		return alimentoService.atualizar(id, alimentoAtualizado);
	}

	// DELETE - Remover alimento
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		alimentoService.deletar(id);
	}
	*/

}
