package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.TipoAcessoUsuario;
import com.app.desPensaBackEnd.model.endity.InstituicaoEntity;
import lombok.Data;

import java.time.LocalDate;

/**DTO dedicado para login na aplicação.* */
@Data
public class LoginDTO {
    private Long idUsuario;
    private Long codigo;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo;
    private LocalDate dataCriacao;
    private TipoAcessoUsuario tipoAcesso;
    private InstituicaoEntity instituicao;
    private String fotoPerfil;
    private String cpf;
}
