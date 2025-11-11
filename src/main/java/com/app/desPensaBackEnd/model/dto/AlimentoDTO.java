package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import com.app.desPensaBackEnd.model.entity.EstoqueEntity;

import java.time.LocalDate;

public class AlimentoDTO {
    private String nome;
    private Long codigo;
    private CategoriaAlimento categoria;
    private String unidadeMedida; // ex: "kg", "g", "un"
    private Double valorCalorico;
    private LocalDate dataValidade;
    private int quantidade;
    private EstoqueEntity estoque;


    // GETTER E SETTER
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public EstoqueEntity getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueEntity estoque) {
        this.estoque = estoque;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
