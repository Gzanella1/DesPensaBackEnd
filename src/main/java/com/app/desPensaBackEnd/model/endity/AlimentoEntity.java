package com.app.desPensaBackEnd.model.endity;

import com.app.desPensaBackEnd.enums.CategoriaAlimento;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "alimento")
public class AlimentoEntity     {

    @Id
    @Column(name = "id_alimento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlimento;

    @Column(name = "nome_alimento")
    private String nome;

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



}
