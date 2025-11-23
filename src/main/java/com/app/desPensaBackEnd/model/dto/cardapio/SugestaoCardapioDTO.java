package com.app.desPensaBackEnd.model.dto.cardapio;

public class SugestaoCardapioDTO {
    private Long idReceitaApi;
    private String nomeReceita;
    private String urlImagem;
// Pode adicionar resumo de ingredientes aqui se quiser


    public Long getIdReceitaApi() {
        return idReceitaApi;
    }

    public void setIdReceitaApi(Long idReceitaApi) {
        this.idReceitaApi = idReceitaApi;
    }

    public String getNomeReceita() {
        return nomeReceita;
    }

    public void setNomeReceita(String nomeReceita) {
        this.nomeReceita = nomeReceita;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}