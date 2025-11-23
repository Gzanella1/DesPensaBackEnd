package com.app.desPensaBackEnd.model.entity;


import com.app.desPensaBackEnd.enums.TipoRefeicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_cardapio")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cardapio")
    private Long idCardapio;

    @Column(name = "data_gerarcao_cardapio")
    private LocalDate dataGeracaoCardapio;

    @Column(name = "tipo_refeicao")
    private TipoRefeicao tipoRefeicao;

    @ManyToOne
    @JoinColumn(name = "fk_id_instituicao", nullable = false)
    @JsonBackReference
    private InstituicaoEntity instituicao;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemCardapio> itens = new ArrayList<>();

    @Column(name = "gerado_automatico")
    private boolean geradoAutomaticamente = false;

    // Novos campos: A receita escolhida fica aqui no cabeçalho
    @Column(name = "nome_receita_escolhida")
    private String nomeReceitaEscolhida;
    @Column(name = "id_receita")
    private Long idReceitaApi;


    // Agora essa lista representa os INGREDIENTES usados
    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL)
    private List<ItemCardapio> ingredientesUsados = new ArrayList<>();


    public String getNomeReceitaEscolhida() {
        return nomeReceitaEscolhida;
    }

    public void setNomeReceitaEscolhida(String nomeReceitaEscolhida) {
        this.nomeReceitaEscolhida = nomeReceitaEscolhida;
    }

    public Long getIdReceitaApi() {
        return idReceitaApi;
    }

    public void setIdReceitaApi(Long idReceitaApi) {
        this.idReceitaApi = idReceitaApi;
    }

    public List<ItemCardapio> getIngredientesUsados() {
        return ingredientesUsados;
    }

    public void setIngredientesUsados(List<ItemCardapio> ingredientesUsados) {
        this.ingredientesUsados = ingredientesUsados;
    }

    public LocalDate getDataGeracaoCardapio() {
        return dataGeracaoCardapio;
    }

    public void setDataGeracaoCardapio(LocalDate dataGeracaoCardapio) {
        this.dataGeracaoCardapio = dataGeracaoCardapio;
    }

    public TipoRefeicao getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(TipoRefeicao tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public InstituicaoEntity getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoEntity instituicao) {
        this.instituicao = instituicao;
    }

    public List<ItemCardapio> getItens() {
        return itens;
    }

    public void setItens(List<ItemCardapio> itens) {
        this.itens = itens;
    }

    public boolean isGeradoAutomaticamente() {
        return geradoAutomaticamente;
    }

    public void setGeradoAutomaticamente(boolean geradoAutomaticamente) {
        this.geradoAutomaticamente = geradoAutomaticamente;
    }

    public Long getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(Long idCardapio) {
        this.idCardapio = idCardapio;
    }
}
