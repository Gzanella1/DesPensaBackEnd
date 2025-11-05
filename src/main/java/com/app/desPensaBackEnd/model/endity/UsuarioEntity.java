package com.app.desPensaBackEnd.model.endity;


import com.app.desPensaBackEnd.enums.TipoAcessoUsuario;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "codigo_usuario")
    private Long codigo;

    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "email_usuario")
    private String email;

    @Column(name = "senha_usuario")
    private String senha;

    @Column(name = "data_criacao_usuario")
    private LocalDate dataCriacao;

    @Column(name = "ativo_usuario", nullable = false)
    private Boolean ativo;

    @Lob
    @Column(name = "foto_perfil_usuario")
    private String fotoPerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso", nullable = false, length = 20)
    private TipoAcessoUsuario tipoAcesso;

    @ManyToOne
    @JoinColumn(name = "fk_id_instituicao", nullable = false)
    private InstituicaoEntity instituicao;


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


}

