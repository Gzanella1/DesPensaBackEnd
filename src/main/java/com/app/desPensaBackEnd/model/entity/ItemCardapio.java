package com.app.desPensaBackEnd.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_ItemCardapio")
public class ItemCardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_cardapio")
    private Long idItemCardapio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alimento_id", nullable = false)
    private AlimentoEntity alimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardapio_id", nullable = false)
    private CardapioEntity cardapio;

    @Column(name = "quantididade_utilizada")
    private Double quantidadeUtilizada;

    @Column(name = "observacao")
    private String observacao;
}
