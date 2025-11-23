package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.model.entity.AlimentoEntity;

import java.util.List;

public class CardapioDTO {

    private List<AlimentoResumoDTO> itens;
    private double caloriasTotais;

    public CardapioDTO(List<AlimentoResumoDTO> itens, double caloriasTotais) {
        this.itens = itens;
        this.caloriasTotais = caloriasTotais;
    }

    public List<AlimentoResumoDTO> getItens() { return itens; }
    public double getCaloriasTotais() { return caloriasTotais; }
}
