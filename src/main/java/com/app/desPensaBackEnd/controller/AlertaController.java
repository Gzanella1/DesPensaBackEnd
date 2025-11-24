package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.AlertaResponseDTO;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;
import com.app.desPensaBackEnd.view.repository.AlertaRepository;
import com.app.desPensaBackEnd.view.services.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaRepository alertaRepository;

    public AlertaController(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @GetMapping
    public List<AlertaEntity> listarTodos() {
        return alertaRepository.findAll();
    }

    // Endpoint para ver apenas os não lidos (o sininho)
    @GetMapping("/nao-lidos")
    public List<AlertaEntity> listarNaoLidos() {
        return alertaRepository.findByVisualizadoFalseOrderByDataDesc();
    }
}