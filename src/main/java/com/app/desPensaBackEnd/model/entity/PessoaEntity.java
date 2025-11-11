package com.app.desPensaBackEnd.model.entity;

import com.app.desPensaBackEnd.enums.RestricaoAlimentar;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tb_pessoa")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Long idPessoa;

    @Column(name = "nome_pessoa")
    private String nome;
    @Column(name = "idade_pessoa")
    private int idade;

    @Enumerated(EnumType.STRING)
    @Column(name = "restricao_alimentar")
    private List<RestricaoAlimentar> restricaoAlimentar;

    @ManyToOne
    @JoinColumn(name = "id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;

}
