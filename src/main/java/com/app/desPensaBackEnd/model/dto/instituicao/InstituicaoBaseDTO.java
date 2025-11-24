package com.app.desPensaBackEnd.model.dto.instituicao;

import com.app.desPensaBackEnd.model.dto.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data; // Assumindo que você usa Lombok, se não, crie Getters/Setters


@Data
public abstract class InstituicaoBaseDTO {
    private Long idInstituicao; // Retornado no GET, ignorado no POST se for novo
    @NotNull(message = "O código é obrigatório")
    private Long codigo;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private String telefone;
    private EnderecoDTO endereco;


    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}