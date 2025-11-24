package com.app.desPensaBackEnd.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "tb_estoque")
public class EstoqueEntity {

    @Id
    @Column(name = "id_estoque")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstoque;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
    private List<AlimentoEntity> alimentos=new ArrayList<>();

    @OneToOne
    private InstituicaoEntity instituicao;

    @Column(name = "sessao_item")
    private String sessaoItem;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentacaoEstoqueEntity> movimentacoes;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlertaEntity> alertas = new ArrayList<>();


    public Long getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Long idEstoque) {
        this.idEstoque = idEstoque;
    }

    public List<AlimentoEntity> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(List<AlimentoEntity> alimentos) {
        this.alimentos = alimentos;
    }

    public InstituicaoEntity getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoEntity instituicao) {
        this.instituicao = instituicao;
    }

    public String getSessaoItem() {
        return sessaoItem;
    }

    public void setSessaoItem(String sessaoItem) {
        this.sessaoItem = sessaoItem;
    }

    public List<MovimentacaoEstoqueEntity> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoEstoqueEntity> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}
