package com.app.desPensaBackEnd.model.endity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_pessoa")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Long idPessoa;

    @ManyToOne
    @JoinColumn(name = "id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;

}
