package com.app.desPensaBackEnd.model.endity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_escola")
public class EscolaEntity extends InstituicaoEntity{

    private int numeroMatriculas;
    private String turno; // ex.: "manhã", "tarde", "integral"

}
