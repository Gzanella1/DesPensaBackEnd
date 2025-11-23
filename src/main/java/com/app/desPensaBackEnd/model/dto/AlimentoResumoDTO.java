package com.app.desPensaBackEnd.model.dto;

import java.time.LocalDate;

public class AlimentoResumoDTO {

    private Long idAlimento;
    private String nome;
    private Double valorCalorico;
    private int quantidade;
    private LocalDate dataValidade;

    public AlimentoResumoDTO(Long id, String nome, Double valorCalorico, int quantidade, LocalDate dataValidade) {
        this.idAlimento = id;
        this.nome = nome;
        this.valorCalorico = valorCalorico;
        this.quantidade = quantidade;
        this.dataValidade = dataValidade;
    }

    // getters
    public Long getIdAlimento() { return idAlimento; }
    public String getNome() { return nome; }
    public Double getValorCalorico() { return valorCalorico; }
    public int getQuantidade() { return quantidade; }
    public LocalDate getDataValidade() { return dataValidade; }
}

