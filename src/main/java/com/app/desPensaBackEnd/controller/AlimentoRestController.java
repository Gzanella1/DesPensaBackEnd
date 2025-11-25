package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.view.services.AlimentoService;
import com.app.desPensaBackEnd.view.services.EstoqueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.desPensaBackEnd.model.dto.AlimentoInputDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/alimentos")
public class AlimentoRestController {

	@Autowired
	private final AlimentoService alimentoService;

	@Autowired
	private EstoqueService estoqueService;

	public AlimentoRestController(AlimentoService alimentoService) {
		this.alimentoService = alimentoService;
	}


	@PatchMapping("/{id}/consumir")
	public ResponseEntity<?> consumir(@PathVariable Long id, @RequestParam int quantidade) {
		try {
			AlimentoEntity atualizado = estoqueService.consumirAlimento(id, quantidade);
			return ResponseEntity.ok(atualizado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// POST: Adicionar Alimento
	// URL: http://localhost:8080/api/alimentos
	@PostMapping
	public ResponseEntity<AlimentoEntity> criar(@RequestBody AlimentoInputDTO dto) {
		AlimentoEntity novoAlimento = alimentoService.adicionar(dto);
		return ResponseEntity.ok(novoAlimento);
	}

	// GET: Listar todos
	@GetMapping
	public ResponseEntity<List<AlimentoEntity>> listar() {
		return ResponseEntity.ok(alimentoService.listarTodos());
	}

	// GET: Listar por Estoque específico
	// URL: http://localhost:8080/api/alimentos/estoque/1
	@GetMapping("/estoque/{idEstoque}")
	public ResponseEntity<List<AlimentoEntity>> listarPorEstoque(@PathVariable Long idEstoque) {
		return ResponseEntity.ok(alimentoService.listarPorEstoque(idEstoque));
	}




	// DELETE: Remover
	// URL: http://localhost:8080/api/alimentos/5 (onde 5 é o id do alimento)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		alimentoService.remover(id);
		return ResponseEntity.noContent().build();
	}

	// No AlimentoController.java

// AlimentoController.java

	@PutMapping("/{id}")
	public ResponseEntity<AlimentoEntity> atualizar(@PathVariable Long id, @RequestBody AlimentoEntity alimento) {
		AlimentoEntity atualizado = alimentoService.atualizar(id, alimento);

		if (atualizado != null) {
			return ResponseEntity.ok(atualizado);
		}

		return ResponseEntity.notFound().build();
	}


	@GetMapping("/{id}")
	public ResponseEntity<AlimentoEntity> buscarPorId(@PathVariable Long id) {
		// O Controller pede ao Service, e decide o HTTP (200 ou 404) baseado na resposta
		return alimentoService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

}