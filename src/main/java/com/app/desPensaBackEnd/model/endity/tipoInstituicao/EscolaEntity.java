package com.app.desPensaBackEnd.model.endity.tipoInstituicao;


import com.app.desPensaBackEnd.model.endity.InstituicaoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class EscolaEntity extends InstituicaoEntity {

    @Column(name = "num_matricula")
    private int numeroMatriculas;
    @Column(name = "turno")
    private String turno; // ex.: "manhã", "tarde", "integral"

}
