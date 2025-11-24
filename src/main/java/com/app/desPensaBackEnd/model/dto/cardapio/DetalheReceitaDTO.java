package com.app.desPensaBackEnd.model.dto.cardapio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalheReceitaDTO {
    private Long id;
    private String title;

    // O @JsonAlias ajuda a mapear o nome feio da API para um nome bonito em Java
    @JsonAlias("extendedIngredients")
    private List<IngredienteDTO> ingredientesEstendidos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredienteDTO> getIngredientesEstendidos() {
        return ingredientesEstendidos;
    }

    public void setIngredientesEstendidos(List<IngredienteDTO> ingredientesEstendidos) {
        this.ingredientesEstendidos = ingredientesEstendidos;
    }
}