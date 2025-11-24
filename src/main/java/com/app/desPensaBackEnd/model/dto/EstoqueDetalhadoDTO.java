package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;

import java.util.List;
import java.util.stream.Collectors;

public record EstoqueDetalhadoDTO(
        Long idEstoque,
        String sessao,
        List<AlertaResponseDTO> alertas // Lista de alertas simplificada
) {
    public EstoqueDetalhadoDTO(EstoqueEntity estoque, List<AlertaEntity> alertas) {
        this(
                estoque.getIdEstoque(),
                estoque.getSessaoItem(), // ou getNome() dependendo da sua entidade
                alertas.stream().map(AlertaResponseDTO::new).collect(Collectors.toList())
        );
    }


}