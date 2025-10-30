package com.app.desPensaBackEnd.model.endity;


import com.app.desPensaBackEnd.enums.TipoRefeicao;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_cardapio")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cardapio")
    private Long idCardapio;

    private LocalDate dataGeracaoCardapio;
    private TipoRefeicao tipoRefeicao;

    @ManyToOne
    @JoinColumn(name = "fk_id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;

}
