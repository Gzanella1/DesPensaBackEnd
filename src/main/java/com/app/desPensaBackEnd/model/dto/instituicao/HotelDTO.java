package com.app.desPensaBackEnd.model.dto.instituicao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelDTO extends InstituicaoBaseDTO {
    private int numeroQuartos;
    private int classificacaoEstrelas;

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(int numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }

    public int getClassificacaoEstrelas() {
        return classificacaoEstrelas;
    }

    public void setClassificacaoEstrelas(int classificacaoEstrelas) {
        this.classificacaoEstrelas = classificacaoEstrelas;
    }
}