package com.app.desPensaBackEnd.model.endity;

import com.app.desPensaBackEnd.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_movimentacao_estoque")
public class MovimentacaoEstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;

    @Column(name = "quantidade")
    private int quantidade;
    @Column(name = "origem")
    private String origem;
    @Column(name = "data")
    private LocalDateTime data;
    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "estoque_id")  // nome da coluna FK na tabela movimentacao_estoque
    private EstoqueEntity estoque;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo; // ENTRADA, SAIDA, AJUSTE


}
