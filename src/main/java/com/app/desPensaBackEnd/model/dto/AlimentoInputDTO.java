package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AlimentoInputDTO {
    private String nome;
    private Long codigo; // Codigo de barras ou similar
    private CategoriaAlimento categoria; // Deve vir como STRING no JSON (ex: "FRUTA")
    private String unidadeMedida;
    private Double valorCalorico;
    private LocalDate dataValidade;
    private int quantidade;

    // O MAIS IMPORTANTE: Recebemos apenas o ID do estoque
    @JsonProperty("idEstoque") // Mapeia o JSON "idEstoque" para esta variável
    private Long idEstoque;




    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public CategoriaAlimento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAlimento categoria) {
        this.categoria = categoria;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getValorCalorico() {
        return valorCalorico;
    }

    public void setValorCalorico(Double valorCalorico) {
        this.valorCalorico = valorCalorico;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getEstoqueId() {
        return idEstoque;
    }

    public void setEstoqueId(Long estoqueId) {
        this.idEstoque = estoqueId;
    }
}
