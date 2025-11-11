package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.app.desPensaBackEnd.model.dto.AlimentoDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;


    // buscar todos alimentos
    //private List<AlimentoDTO> buscaTodosAlimentos(){

    //}

}
