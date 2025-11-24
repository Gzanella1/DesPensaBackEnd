package com.app.desPensaBackEnd.model.entity;




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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EstoqueEntity getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueEntity estoque) {
        this.estoque = estoque;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Boolean getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(Boolean visualizado) {
        this.visualizado = visualizado;
    }

    public NivelAlerta getNivel() {
        return nivel;
    }

    public void setNivel(NivelAlerta nivel) {
        this.nivel = nivel;
    }
}
