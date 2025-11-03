package com.app.desPensaBackEnd.model.endity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "estoque")
public class EstoqueEntity {

    @Id
    @Column(name = "id_estoque")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEstoque;
    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
    private List<AlimentoEntity> alimentos=new ArrayList<>();
    @OneToOne
    private InstituicaoEntity instituicao;
    @Column(name = "sessao_item")
    private String sessaoItem;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentacaoEstoqueEntity> movimentacoes;

}
