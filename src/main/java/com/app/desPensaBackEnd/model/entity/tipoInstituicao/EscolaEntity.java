package com.app.desPensaBackEnd.model.entity.tipoInstituicao;


import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_escola")
//@PrimaryKeyJoinColumn(name = "id_instituicao")
public class EscolaEntity extends InstituicaoEntity {

    @Column(name = "num_matricula")
    private int numeroMatriculas;
    @Column(name = "turno")
    private String turno; // ex.: "manhã", "tarde", "integral"


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
