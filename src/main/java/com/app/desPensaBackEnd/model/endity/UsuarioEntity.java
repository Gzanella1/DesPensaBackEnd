package com.app.desPensaBackEnd.model.endity;


import com.app.desPensaBackEnd.enums.PerfilAcessoUsuario;
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

    @Column(name = "codigo_usuario")
    private Long codigo;

    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "email_usuario")
    private String email;

    @Column(name = "senha_usuario")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso_usuario")
    private PerfilAcessoUsuario tipoAcesso;

    @Column(name = "data_criacao_usuario")
    private LocalDate dataCriacao;

    @Column(name = "ativo_usuario", nullable = false)
    private Boolean ativo;

    @Lob
    @Column(name = "foto_perfil_usuario")
    private String fotoPerfil;


    // Muitos usuários pertencem a uma instituição
    // @JoinColumn: define a coluna estrangeira no banco (instituicao_id) que referencia o id da tabela tb_instituicao.
    // referencedColumnName = "id" diz qual coluna na tabela instituicao é a chave primária.
    @ManyToOne
    @JoinColumn(name = "instituicao_id", referencedColumnName = "idInstituicao")
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


    /*   @Override
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

