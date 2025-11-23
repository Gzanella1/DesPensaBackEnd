package com.app.desPensaBackEnd.model.entity;


import com.app.desPensaBackEnd.enums.TipoInstituicao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class InstituicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private TipoInstituicao tipoInstituição;

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
    @JsonManagedReference // "Eu gerencio a lista"
    //@JsonIgnore
    private List<CardapioEntity> cardapios = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkid_endereco") // nome da FK na tabela tb_instituicao
    private EnderecoEntity endereco;


    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoInstituicao getTipoInstituição() {
        return tipoInstituição;
    }

    public void setTipoInstituição(TipoInstituicao tipoInstituição) {
        this.tipoInstituição = tipoInstituição;
    }

    public List<UsuarioEntity> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<UsuarioEntity> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public List<PessoaEntity> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<PessoaEntity> pessoas) {
        this.pessoas = pessoas;
    }

    public List<CardapioEntity> getCardapios() {
        return cardapios;
    }

    public void setCardapios(List<CardapioEntity> cardapios) {
        this.cardapios = cardapios;
    }

    public EnderecoEntity getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoEntity endereco) {
        this.endereco = endereco;
    }
}
