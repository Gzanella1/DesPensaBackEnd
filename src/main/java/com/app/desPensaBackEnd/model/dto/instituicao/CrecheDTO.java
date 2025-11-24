package com.app.desPensaBackEnd.model.dto.instituicao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CrecheDTO extends InstituicaoBaseDTO {
    private int idadeMaximaAtendidaMeses;
    private int capacidade;


    public int getIdadeMaximaAtendidaMeses() {
        return idadeMaximaAtendidaMeses;
    }

    public void setIdadeMaximaAtendidaMeses(int idadeMaximaAtendidaMeses) {
        this.idadeMaximaAtendidaMeses = idadeMaximaAtendidaMeses;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}