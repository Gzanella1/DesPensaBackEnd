package com.app.desPensaBackEnd.view.services;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.app.desPensaBackEnd.model.dto.AlimentoDTO;
import com.app.desPensaBackEnd.model.dto.UsuarioDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.view.repository.AlimentoRepository;
import jakarta.persistence.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;




    public List<AlimentoDTO> listarTodos() {
        return alimentoRepository.findAll().stream().map(aE -> {
        	
        	AlimentoDTO dto = new AlimentoDTO();
            BeanUtils.copyProperties(aE, dto);

               return dto;
        }).collect(Collectors.toList());
    }


}
