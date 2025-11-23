package com.app.desPensaBackEnd.model.entity;


import com.app.desPensaBackEnd.enums.TipoAcessoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity /*implements UserDetails*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @Column(name = "cpf")
    private String cpf;

    @NotNull
    @Column(name = "codigo_usuario", nullable = false, unique = true)
    private Long codigo;

    @NotNull
    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "email_usuario")
    private String email;

    @NotNull
    @Column(name = "senha_usuario", nullable = false)
    private String senha;

    @Column(name = "data_criacao_usuario")
    private LocalDate dataCriacao;

    @Column(name = "ativo_usuario", nullable = false)
    private Boolean ativo;

    @Lob
    @Column(name = "foto_perfil_usuario")
    private String fotoPerfil;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso", nullable = false, length = 20)
    private TipoAcessoUsuario tipoAcesso;

    @ManyToOne
    @JoinColumn(name = "fk_id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;

    @Column(name = "login")
    private String login;

}

