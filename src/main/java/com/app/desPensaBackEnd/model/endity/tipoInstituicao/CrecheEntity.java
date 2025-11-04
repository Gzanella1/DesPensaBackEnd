package com.app.desPensaBackEnd.model.endity.tipoInstituicao;


import com.app.desPensaBackEnd.model.endity.InstituicaoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class CrecheEntity extends InstituicaoEntity {

    @Column(name = "idadeMaxima")
    private int idadeMaximaAtendidaMeses;
    @Column(name = "capacidade")
    private int capacidade;

}