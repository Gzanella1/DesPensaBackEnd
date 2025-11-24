package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;

import java.time.LocalDateTime;

public record AlertaResponseDTO(
        Long id,
        TipoAlerta tipoAlerta,
        String mensagem,
        Long estoqueId,      // Retornamos apenas o ID para facilitar
        String nomeProduto,  // Opcional: útil para o front exibir o nome sem nova requisição
        LocalDateTime data,
        Boolean visualizado,
        NivelAlerta nivel
) {
    // Construtor auxiliar para converter Entity -> DTO
    public AlertaResponseDTO(AlertaEntity entity) {
        this(
                entity.getId(),
                entity.getTipoAlerta(),
                entity.getMensagem(),
                entity.getEstoque() != null ? entity.getEstoque().getIdEstoque(): null,
                entity.getEstoque() != null ? entity.getEstoque().getSessaoItem() : "N/A", // Assumindo que Estoque tem .getNome()
                entity.getData(),
                entity.getVisualizado(),
                entity.getNivel()
        );
    }
}