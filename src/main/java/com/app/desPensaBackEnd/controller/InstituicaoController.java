package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.mapper.InstituicaoMapper;
import com.app.desPensaBackEnd.model.dto.*;
import com.app.desPensaBackEnd.model.dto.instituicao.CrecheDTO;
import com.app.desPensaBackEnd.model.dto.instituicao.EscolaDTO;
import com.app.desPensaBackEnd.model.dto.instituicao.HotelDTO;
import com.app.desPensaBackEnd.model.dto.instituicao.InstituicaoBaseDTO;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.view.services.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instituicoes")
@CrossOrigin(origins = "*") // Permite conexão com o Frontend
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private InstituicaoMapper mapper;

    // 1. LISTAR TODAS (Retorna DTOs)
    @GetMapping
    public ResponseEntity<List<InstituicaoBaseDTO>> listarTodas() {
        // Busca as entidades no serviço
        List<InstituicaoEntity> entidades = instituicaoService.listarTodas();

        // Converte para DTOs para enviar ao frontend
        List<InstituicaoBaseDTO> dtos = entidades.stream()
                .map(entity -> mapper.toDTO(entity))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // 2. BUSCAR POR ID (Retorna DTO)
    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoBaseDTO> buscarPorId(@PathVariable Long id) {
        try {
            InstituicaoEntity entity = instituicaoService.buscarPorId(id);
            return ResponseEntity.ok(mapper.toDTO(entity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. ADICIONAR CRECHE (Recebe DTO -> Salva Entity -> Retorna DTO)
    @PostMapping("/creche")
    public ResponseEntity<CrecheDTO> criarCreche(@RequestBody @Valid CrecheDTO dto) {
        var entity = mapper.toEntity(dto);
        var salvo = instituicaoService.salvarCreche(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body((CrecheDTO) mapper.toDTO(salvo));
    }

    // 4. ADICIONAR ESCOLA
    @PostMapping("/escola")
    public ResponseEntity<EscolaDTO> criarEscola(@RequestBody @Valid EscolaDTO dto) {
        var entity = mapper.toEntity(dto);
        var salvo = instituicaoService.salvarEscola(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body((EscolaDTO) mapper.toDTO(salvo));
    }

    // 5. ADICIONAR HOTEL
    @PostMapping("/hotel")
    public ResponseEntity<HotelDTO> criarHotel(@RequestBody @Valid HotelDTO dto) {
        var entity = mapper.toEntity(dto);
        var salvo = instituicaoService.salvarHotel(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body((HotelDTO) mapper.toDTO(salvo));
    }

    // 6. REMOVER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            instituicaoService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. ATUALIZAR (Adicione isso ao seu Controller)
    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoDTO> atualizar(@PathVariable Long id, @RequestBody InstituicaoDTO dto) {
        // Chama o serviço para atualizar
        InstituicaoEntity salvo = instituicaoService.atualizar(id, dto);

        // Converte de volta para DTO e retorna
        return ResponseEntity.ok(new InstituicaoDTO(salvo));
    }
}