package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChaveSeguranca implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("chave")
    private String chave;

    public ChaveSeguranca(String chave) {
        this.chave = chave;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
