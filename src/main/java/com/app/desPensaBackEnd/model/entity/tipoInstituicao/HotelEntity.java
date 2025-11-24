package com.app.desPensaBackEnd.model.entity.tipoInstituicao;


import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_hotel")
//@PrimaryKeyJoinColumn(name = "id_instituicao")
public class HotelEntity extends InstituicaoEntity {

    @Column(name = "num_quartos")
    private int numeroQuartos;
    @Column(name = "classificacao_estrelas")
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