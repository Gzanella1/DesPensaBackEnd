package com.app.desPensaBackEnd.controller;

import com.app.desPensaBackEnd.model.dto.AlertaResponseDTO;
import com.app.desPensaBackEnd.view.services.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
@CrossOrigin(origins = "*") // Configure conforme sua necessidade de segurança
public class AlertaController {

    @Autowired
    private AlertaService service;


}