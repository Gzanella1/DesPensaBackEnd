package com.app.desPensaBackEnd.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_item_cardapio")
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    // Vínculo com o Alimento do seu estoque
    @ManyToOne
    @JoinColumn(name = "fk_alimento")
    private AlimentoEntity alimento;

    private double quantidadeUsada;
    private String unidade;

    @ManyToOne
    @JoinColumn(name = "fk_cardapio")
    @JsonIgnore
    private CardapioEntity cardapio;


    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public AlimentoEntity getAlimento() {
        return alimento;
    }

    public void setAlimento(AlimentoEntity alimento) {
        this.alimento = alimento;
    }

    public double getQuantidadeUsada() {
        return quantidadeUsada;
    }

    public void setQuantidadeUsada(double quantidadeUsada) {
        this.quantidadeUsada = quantidadeUsada;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public CardapioEntity getCardapio() {
        return cardapio;
    }

    public void setCardapio(CardapioEntity cardapio) {
        this.cardapio = cardapio;
    }
}