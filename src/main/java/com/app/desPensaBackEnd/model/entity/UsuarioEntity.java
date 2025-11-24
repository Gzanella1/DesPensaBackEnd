package com.app.desPensaBackEnd.model.entity;


import com.app.desPensaBackEnd.enums.TipoAcessoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity implements UserDetails {

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


    // Implementações de UserDetails
    @Override
    public String getPassword() { return senha; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return Boolean.TRUE.equals(ativo); }

/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("USER"));
    }*/


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipoAcesso == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipoAcesso.name()));
}



// GUETTER E SETTERS


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public TipoAcessoUsuario getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(TipoAcessoUsuario tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public InstituicaoEntity getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoEntity instituicao) {
        this.instituicao = instituicao;
    }
}

