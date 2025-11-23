package com.app.desPensaBackEnd.model.entity;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "tb_alimento")
public class AlimentoEntity     {

    @Id
    @Column(name = "id_alimento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlimento;

    @Column(name = "nome_alimento")
    private String nome;

    @Column(name = "codigo_usuario")
    private Long codigo;

    @Column(name = "categoria_alimento")
    @Enumerated(EnumType.STRING)
    private CategoriaAlimento categoria;

    @Column(name = "uni_medidada")
    private String unidadeMedida; // ex: "kg", "g", "un"

    @Column(name = "val_calorico")
    private Double valorCalorico;

    @Temporal(TemporalType.DATE)
    private LocalDate dataValidade;

    @Column(name = "quantidade_alimento")
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "fk_estoque")
    private EstoqueEntity estoque;


    // relacionamento many-to-many com restrições
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_alimento_restricao",
            joinColumns = @JoinColumn(name = "id_alimento"),
            inverseJoinColumns = @JoinColumn(name = "id_restricao")
    )
    private Set<RestricaoAlimentarEntity> restricoes;


    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Long getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Long idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public CategoriaAlimento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAlimento categoria) {
        this.categoria = categoria;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getValorCalorico() {
        return valorCalorico;
    }

    public void setValorCalorico(Double valorCalorico) {
        this.valorCalorico = valorCalorico;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public EstoqueEntity getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueEntity estoque) {
        this.estoque = estoque;
    }

    public Set<RestricaoAlimentarEntity> getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(Set<RestricaoAlimentarEntity> restricoes) {
        this.restricoes = restricoes;
    }
}
