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


    @ElementCollection(targetClass = RestricaoAlimentar.class)
    @CollectionTable(name = "tb_pessoa_restricoes", joinColumns = @JoinColumn(name = "id_pessoa"))
    @Enumerated(EnumType.STRING)
    @Column(name = "restricao_alimentar")
    private List<RestricaoAlimentar> restricaoAlimentar;

    @ManyToOne
    @JoinColumn(name = "id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;


    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<RestricaoAlimentar> getRestricaoAlimentar() {
        return restricaoAlimentar;
    }

    public void setRestricaoAlimentar(List<RestricaoAlimentar> restricaoAlimentar) {
        this.restricaoAlimentar = restricaoAlimentar;
    }

    public InstituicaoEntity getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoEntity instituicao) {
        this.instituicao = instituicao;
    }
}
