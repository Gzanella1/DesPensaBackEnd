package com.app.desPensaBackEnd.model.dto.cardapio;

import com.app.desPensaBackEnd.model.entity.AlimentoEntity;

public class AlimentoResumoDTO {

    private Long id;
    private String nome;
    private int quantidade;
    private String unidadeMedida;
    private Double valorCalorico; // Essencial para o cálculo do CardapioDTO

    public AlimentoResumoDTO() {
    }

    // Construtor que facilita a conversão da Entity para DTO
    public AlimentoResumoDTO(AlimentoEntity entity) {
        this.id = entity.getIdAlimento();
        this.nome = entity.getNome();
        this.quantidade = entity.getQuantidade();
        this.unidadeMedida = entity.getUnidadeMedida();
        this.valorCalorico = entity.getValorCalorico();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
}