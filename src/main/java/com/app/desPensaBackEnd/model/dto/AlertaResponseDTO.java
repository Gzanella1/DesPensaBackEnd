package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import com.app.desPensaBackEnd.model.entity.AlertaEntity;

import java.time.LocalDateTime;


public record AlertaResponseDTO(
        Long id,
        TipoAlerta tipoAlerta,
        String mensagem,
        Long estoqueId,
        String nomeSessao, // Ajustei o nome para ficar mais claro
        LocalDateTime data,
        Boolean visualizado,
        NivelAlerta nivel
) {
    public AlertaResponseDTO(AlertaEntity entity) {
        this(
                entity.getId(),
                entity.getTipoAlerta(),
                entity.getMensagem(),
                entity.getEstoque() != null ? entity.getEstoque().getIdEstoque() : null,
                // Verifica null pointer aqui para evitar crash se estoque for null
                entity.getEstoque() != null ? entity.getEstoque().getSessaoItem() : "Geral",
                entity.getData(),
                entity.getVisualizado(),
                entity.getNivel()
        );
    }
}