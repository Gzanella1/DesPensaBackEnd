package com.app.desPensaBackEnd.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idEndereco;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "cep", nullable = false, length = 10)
    private String cep;

    @OneToOne(mappedBy = "endereco")
    private InstituicaoEntity instituicao;

}
