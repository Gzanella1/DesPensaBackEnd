package com.app.desPensaBackEnd.model.endity;


import com.app.desPensaBackEnd.enums.TipoRefeicao;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_cardapio")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cardapio")
    private Long idCardapio;

    @Column(name = "data_gerarcao_cardapio")
    private LocalDate dataGeracaoCardapio;

    @Column(name = "tipo_refeicao")
    private TipoRefeicao tipoRefeicao;

    @ManyToOne
    @JoinColumn(name = "fk_id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemCardapio> itens = new ArrayList<>();

    @Column(name = "gerado_automatico")
    private boolean geradoAutomaticamente = false;

}
