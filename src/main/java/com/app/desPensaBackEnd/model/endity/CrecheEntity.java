package com.app.desPensaBackEnd.model.endity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "creche")
public class CrecheEntity extends InstituicaoEntity {

    private int idadeMaximaAtendidaMeses;
    private int capacidade;
}