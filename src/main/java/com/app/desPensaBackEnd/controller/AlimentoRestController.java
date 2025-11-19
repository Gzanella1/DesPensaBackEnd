package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.AlimentoDTO;
import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.view.services.AlimentoService;
import com.app.desPensaBackEnd.view.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/alimento")
public class AlimentoRestController {
	

	@Autowired
	private AlimentoService alimentoService;

	// GET - Listar Todos
	
    @GetMapping("/buscaAlimento")
	public List<AlimentoDTO> listartodos( ) {
		return alimentoService.listarTodos ( );
	}
		
	// GET - Buscar por ID
	
	@GetMapping("/{id}")
	public AlimentoDTO buscarPorId(@PathVariable Long id) {
		return alimentoService.buscarPorId(id);
	}
	
	// POST - criar novo alimento
	@PostMapping
	public AlimentoDTO criar(@RequestBody AlimentoDTO alimento) {
	    return alimentoService.salvar(alimento);
	}
	
	// PUT - atualizar alimento
	@PutMapping("/{id}")
	public AlimentoDTO atualizar(   @PathVariable Long id, @RequestBody AlimentoDTO alimentoAtualizado) {
	    return alimentoService.atualizar(id, alimentoAtualizado);
	}
	
	// DELETE - remover alimento
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
	    alimentoService.deletar(id);
	}
}
