package com.app.desPensaBackEnd.model.endity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_cardapio")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cardapio")
    private Long idCardapio;


    @ManyToOne
    @JoinColumn(name = "fk_id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;

}
