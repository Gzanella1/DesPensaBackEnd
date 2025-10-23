package com.app.desPensaBackEnd.model.endity;


import com.app.desPensaBackEnd.enums.TipoUnidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_instituicao")
public class InstituicaoEntity {

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

    @Column(name = "tipo_unidade")
    private TipoUnidade tipoUnidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco") // nome da FK na tabela tb_instituicao
    private EnderecoEntity endereco;

    @Column(name = "telefone_instituicao")
    private String telefone;

    @Column(name = "pessoas_daInstituicao")

    // Uma instituição tem muitas pessoas
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaEntity> pessoas = new ArrayList<>();


    // "instituicao" é o nome do atributo na classe Usuario:
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<CardapioEntity> cardapioEntities = new ArrayList<>();


    // Uma instituição pode ter vários usuários
        /* @OneToMany(mappedBy = "instituicao"): indica que essa relação é mapeada pelo atributo instituicao
        da classe UsuarioEntity. Ou seja, o lado “forte” da relação está em UsuarioEntity.
        cascade = CascadeType.ALL: (opcional) propaga operações — se apagar uma instituição, apaga os
        usuários ligados a ela, por exemplo.*/
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<UsuarioEntity> responsaveis =new ArrayList<>();


}
