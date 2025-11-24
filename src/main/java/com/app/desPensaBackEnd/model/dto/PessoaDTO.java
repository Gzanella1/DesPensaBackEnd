package com.app.desPensaBackEnd.model.dto;


import com.app.desPensaBackEnd.enums.RestricaoAlimentar;
import com.app.desPensaBackEnd.model.entity.PessoaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PessoaDTO {

    private Long idPessoa;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotNull(message = "A idade é obrigatória")
    private Integer idade;
    private List<RestricaoAlimentar> restricaoAlimentar;
    @NotNull(message = "O ID da instituição é obrigatório")
    private Long idInstituicao; // Fundamental para o vínculo
    // --- CONSTRUTOR VAZIO (OBRIGATÓRIO PARA O JSON) ---
    public PessoaDTO() {
    }

    // Construtor para converter Entity -> DTO
    public PessoaDTO(PessoaEntity entity) {
        this.idPessoa = entity.getIdPessoa();
        this.nome = entity.getNome();
        this.idade = entity.getIdade();
        this.restricaoAlimentar = entity.getRestricaoAlimentar();

        if (entity.getInstituicao() != null) {
            this.idInstituicao = entity.getInstituicao().getIdInstituicao();
        }
    }


    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public List<RestricaoAlimentar> getRestricaoAlimentar() {
        return restricaoAlimentar;
    }

    public void setRestricaoAlimentar(List<RestricaoAlimentar> restricaoAlimentar) {
        this.restricaoAlimentar = restricaoAlimentar;
    }

    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }
}
