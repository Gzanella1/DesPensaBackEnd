package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.TipoMovimentacao;
import lombok.Data;

@Data
public class MovimentacaoDTO {
    private Long idEstoque;
    private Long idAlimento; // O item que está sendo movido
    private int quantidade;
    private TipoMovimentacao tipo; // ENTRADA ou SAIDA
    private String origem; // Ex: "Doação", "Compra", "Consumo"
    private String observacao;


    public Long getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Long idEstoque) {
        this.idEstoque = idEstoque;
    }

    public Long getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Long idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}