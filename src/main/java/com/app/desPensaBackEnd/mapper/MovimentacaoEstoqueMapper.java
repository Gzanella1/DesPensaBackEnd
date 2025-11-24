package com.app.desPensaBackEnd.mapper;


import com.app.desPensaBackEnd.model.dto.MovimentacaoEstoqueDTO;
import com.app.desPensaBackEnd.model.entity.AlimentoEntity;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;

public class MovimentacaoEstoqueMapper {

    public static MovimentacaoEstoqueDTO toDTO(MovimentacaoEstoqueEntity entity) {
        MovimentacaoEstoqueDTO dto = new MovimentacaoEstoqueDTO();

        dto.setIdMovimentacao(entity.getIdMovimentacao());
        dto.setQuantidade(entity.getQuantidade());
        dto.setOrigem(entity.getOrigem());
        dto.setDataMovimentacao(entity.getData());
        dto.setObservacao(entity.getObservacao());
        dto.setTipo(entity.getTipo());

        if (entity.getAlimento() != null)
            dto.setAlimentoId(entity.getAlimento().getIdAlimento());

        if (entity.getEstoque() != null)
            dto.setEstoqueId(entity.getEstoque().getIdEstoque());

        return dto;
    }

    public static MovimentacaoEstoqueEntity toEntity(
            MovimentacaoEstoqueDTO dto,
            AlimentoEntity alimento,
            EstoqueEntity estoque
    ) {
        MovimentacaoEstoqueEntity entity = new MovimentacaoEstoqueEntity();

        entity.setIdMovimentacao(dto.getIdMovimentacao());
        entity.setQuantidade(dto.getQuantidade());
        entity.setOrigem(dto.getOrigem());
        entity.setData(dto.getDataMovimentacao());
        entity.setObservacao(dto.getObservacao());
        entity.setTipo(dto.getTipo());

        entity.setAlimento(alimento);
        entity.setEstoque(estoque);

        return entity;
    }
}

