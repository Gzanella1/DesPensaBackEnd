package com.app.desPensaBackEnd.model.entity.tipoInstituicao;


import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_creche")
//@PrimaryKeyJoinColumn(name = "id_instituicao")
public class CrecheEntity extends InstituicaoEntity {

    @Column(name = "idadeMaxima")
    private int idadeMaximaAtendidaMeses;
    @Column(name = "capacidade")
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