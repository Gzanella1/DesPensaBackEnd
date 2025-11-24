package com.app.desPensaBackEnd.model.dto.instituicao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EscolaDTO extends InstituicaoBaseDTO {
    private int numeroMatriculas;
    private String turno;

    public int getNumeroMatriculas() {
        return numeroMatriculas;
    }

    public void setNumeroMatriculas(int numeroMatriculas) {
        this.numeroMatriculas = numeroMatriculas;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}