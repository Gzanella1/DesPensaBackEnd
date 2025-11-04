package com.app.desPensaBackEnd.model.endity;


import com.app.desPensaBackEnd.enums.TipoInstituicao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_instituicao")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class InstituicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instituicao")
    private Long idInstituicao;

    @NotNull
    @Column(name = "codigo_instituicao")
    private Long codigo;

    @NotNull
    @Column(name = "nome_instituicao")
    private String nome;

    @Column(name = "telefone_instituicao")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_unidade", nullable = false, length = 20)
    private TipoInstituicao tipoUnidade;

    // Uma instituição pode ter vários usuários
        /* @OneToMany(mappedBy = "instituicao"): indica que essa relação é mapeada pelo atributo instituicao
        da classe UsuarioEntity. Ou seja, o lado “forte” da relação está em UsuarioEntity.
        cascade = CascadeType.ALL: (opcional) propaga operações — se apagar uma instituição, apaga os
        usuários ligados a ela, por exemplo.*/

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<UsuarioEntity> responsaveis = new ArrayList<>();

    // Uma instituição tem muitas pessoas
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaEntity> pessoas = new ArrayList<>();

    // indica que o atributo instituição na classe cardapio é quem manda
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardapioEntity> cardapios = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkid_endereco") // nome da FK na tabela tb_instituicao
    private EnderecoEntity endereco;
}
