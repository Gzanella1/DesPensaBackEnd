package com.app.desPensaBackEnd.model.endity.tipoInstituicao;


import com.app.desPensaBackEnd.model.endity.InstituicaoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class HotelEntity extends InstituicaoEntity {

    @Column(name = "num_quartos")
    private int numeroQuartos;
    @Column(name = "classificacao_estrelas")
    private int classificacaoEstrelas;
}