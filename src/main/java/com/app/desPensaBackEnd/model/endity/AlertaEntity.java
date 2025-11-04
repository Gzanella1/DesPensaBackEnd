package com.app.desPensaBackEnd.model.endity;

import com.app.desPensaBackEnd.enums.NivelAlerta;
import com.app.desPensaBackEnd.enums.TipoAlerta;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_alerta")
public class AlertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alerta", nullable = false)
    private TipoAlerta tipoAlerta;
    @Column(nullable = false, length = 1000)
    private String mensagem;
    /**
     * Relação com Estoque (supondo que você tenha uma entidade Estoque).
     * ManyToOne porque um Estoque pode gerar vários Alertas.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estoque_id")
    private EstoqueEntity estoque;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime data;
    @Column(name = "visualizado", nullable = false)
    private Boolean visualizado = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_alerta")
    private NivelAlerta nivel;


}
