package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class Canal {
    @SerializedName("id")
    private Integer id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("visualizacoes")
    private BigInteger visualizacoes;

    @SerializedName("seguidores")
    private BigInteger seguidores;

    @SerializedName("bio")
    private String bio;

    // Construtor que aceita apenas o nome
    public Canal(String nome) {
        this.nome = nome;
    }

    // Construtor padrão
    public Canal() { }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigInteger getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(BigInteger seguidores) {
        this.seguidores = seguidores;
    }

    public BigInteger getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(BigInteger visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
