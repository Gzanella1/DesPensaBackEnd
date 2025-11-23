package com.app.desPensaBackEnd.model.entity;



import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_restricao_alimentar")
public class RestricaoAlimentarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restricao")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    // Nome único da restrição, ex: "GLUTEN", "LACTOSE", "AMENDOIM", "VEGAN"
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
