package com.app.desPensaBackEnd.model.dto.cardapio;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredienteDTO {
    private String name;   // Nome do ingrediente (ex: "chicken")
    private double amount; // Quantidade (ex: 1.5)
    private String unit;   // Unidade (ex: "kg")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}