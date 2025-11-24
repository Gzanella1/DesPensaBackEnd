package com.app.desPensaBackEnd.model.dto;

import com.app.desPensaBackEnd.enums.TipoInstituicao;
import com.app.desPensaBackEnd.model.entity.InstituicaoEntity;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.CrecheEntity;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.EscolaEntity;
import com.app.desPensaBackEnd.model.entity.tipoInstituicao.HotelEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstituicaoDTO {

    // --- Campos Comuns ---
    private Long id;
    private Long codigo;
    private String nome;
    private String telefone;
    private TipoInstituicao tipo;
    private EnderecoDTO enderecoDTO;

    public InstituicaoDTO() {
    }

    // --- Campos de ESCOLA ---
    private Integer numeroMatriculas;
    private String turno;

    // --- Campos de CRECHE ---
    private Integer idadeMaximaAtendidaMeses;
    private Integer capacidade;

    // --- Campos de HOTEL ---
    private Integer numeroQuartos;
    private Integer classificacaoEstrelas;


    // Construtor Inteligente: Identifica o tipo da entidade e preenche os campos certos
    public InstituicaoDTO(InstituicaoEntity entity) {
        this.id = entity.getIdInstituicao();
        this.codigo = entity.getCodigo();
        this.nome = entity.getNome();
        this.telefone = entity.getTelefone();
        this.tipo = entity.getTipoInstituição();

        if (entity instanceof EscolaEntity) {
            EscolaEntity escola = (EscolaEntity) entity;
            this.numeroMatriculas = escola.getNumeroMatriculas();
            this.turno = escola.getTurno();
        }
        else if (entity instanceof CrecheEntity) {
            CrecheEntity creche = (CrecheEntity) entity;
            this.idadeMaximaAtendidaMeses = creche.getIdadeMaximaAtendidaMeses();
            this.capacidade = creche.getCapacidade();
        }
        else if (entity instanceof HotelEntity) {
            HotelEntity hotel = (HotelEntity) entity;
            this.numeroQuartos = hotel.getNumeroQuartos();
            this.classificacaoEstrelas = hotel.getClassificacaoEstrelas();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TipoInstituicao getTipo() {
        return tipo;
    }

    public void setTipo(TipoInstituicao tipo) {
        this.tipo = tipo;
    }

    public Integer getNumeroMatriculas() {
        return numeroMatriculas;
    }

    public void setNumeroMatriculas(Integer numeroMatriculas) {
        this.numeroMatriculas = numeroMatriculas;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getIdadeMaximaAtendidaMeses() {
        return idadeMaximaAtendidaMeses;
    }

    public void setIdadeMaximaAtendidaMeses(Integer idadeMaximaAtendidaMeses) {
        this.idadeMaximaAtendidaMeses = idadeMaximaAtendidaMeses;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(Integer numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }

    public Integer getClassificacaoEstrelas() {
        return classificacaoEstrelas;
    }

    public void setClassificacaoEstrelas(Integer classificacaoEstrelas) {
        this.classificacaoEstrelas = classificacaoEstrelas;
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }
}