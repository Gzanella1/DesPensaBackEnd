package com.app.desPensaBackEnd.model.endity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_hotel")
public class HotelEntity extends InstituicaoEntity {

    private int numeroQuartos;
    private int classificacaoEstrelas;
}