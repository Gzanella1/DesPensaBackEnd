package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.TipoMovimentacao;
import com.app.desPensaBackEnd.model.entity.MovimentacaoEstoqueEntity;

import java.time.LocalDateTime;

public class MovimentacaoEstoqueDTO {

    private Long idMovimentacao;
    private int quantidade;
    private String origem;
    private LocalDateTime dataMovimentacao;
    private String observacao;
    private Long alimentoId;
    private Long estoqueId;
    private TipoMovimentacao tipo;


    // Construtor que recebe a entidade
    public MovimentacaoEstoqueDTO(MovimentacaoEstoqueEntity entity) {
        this.idMovimentacao = entity.getIdMovimentacao();
        this.quantidade = entity.getQuantidade();
        this.origem = entity.getOrigem();
        this.dataMovimentacao = entity.getData();
        this.observacao = entity.getObservacao();
        this.alimentoId = entity.getAlimento().getIdAlimento();
        this.estoqueId = entity.getEstoque().getIdEstoque();
        this.tipo = entity.getTipo();
    }

    // Construtor vazio para o Spring
    public MovimentacaoEstoqueDTO() {}

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

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getAlimentoId() {
        return alimentoId;
    }

    public void setAlimentoId(Long alimentoId) {
        this.alimentoId = alimentoId;
    }

    public Long getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(Long estoqueId) {
        this.estoqueId = estoqueId;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }
}
