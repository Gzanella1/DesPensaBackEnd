package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ValidadeService {

    private final AlimentoRepository alimentoRepository;
    private final AlertaService alertaService;

    public ValidadeService(AlimentoRepository alimentoRepository, AlertaService alertaService) {
        this.alimentoRepository = alimentoRepository;
        this.alertaService = alertaService;
    }


}