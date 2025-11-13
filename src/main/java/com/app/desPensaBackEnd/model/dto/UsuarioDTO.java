package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.TipoAcessoUsuario;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private Long codigo;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo;
    private LocalDate dataCriacao;
    private TipoAcessoUsuario tipoAcesso;
    private Long idInstituicao;
    private String fotoPerfil;
    private String cpf;
    private String login;


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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TipoAcessoUsuario getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(TipoAcessoUsuario tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Long getInstituicao() {
        return idInstituicao;
    }

    public void setInstituicao(Long instituicao) {
        this.idInstituicao = instituicao;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
