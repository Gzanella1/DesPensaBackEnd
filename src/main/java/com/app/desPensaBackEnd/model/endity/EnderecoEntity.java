package com.app.desPensaBackEnd.model.endity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_endereco")
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idEndereco;

    @OneToOne(mappedBy = "endereco")
    private InstituicaoEntity instituicao;

}
