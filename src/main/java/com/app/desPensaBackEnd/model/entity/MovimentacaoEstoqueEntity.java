package com.app.desPensaBackEnd.model.entity;

import com.app.desPensaBackEnd.enums.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_movimentacao_estoque")
public class MovimentacaoEstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;

    @Column(name = "quantidade")
    private int quantidade;
    @Column(name = "origem")
    private String origem;
    @Column(name = "data")
    private LocalDateTime dataMovimentacao;
    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private AlimentoEntity alimento;

    @JsonIgnore // Isso impede o loop infinito
    @ManyToOne
    @JoinColumn(name = "estoque_id")  // nome da coluna FK na tabela movimentacao_estoque
    private EstoqueEntity estoque;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo; // ENTRADA, SAIDA, AJUSTE

    public Long getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(Long idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public LocalDateTime getData() {
        return dataMovimentacao;
    }

    public void setData(LocalDateTime data) {
        this.dataMovimentacao = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public AlimentoEntity getAlimento() {
        return alimento;
    }

    public void setAlimento(AlimentoEntity alimento) {
        this.alimento = alimento;
    }

    public EstoqueEntity getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueEntity estoque) {
        this.estoque = estoque;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }
}
