package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Canal implements Serializable {

    private static final long serialVersionUID = 3L;

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("visualizacoes")
    private long visualizacoes;

    @SerializedName("seguidores")
    private long seguidores;

    @SerializedName("bio")
    private String bio;

    // Chave estrangeira para associar o Canal ao Usuário (ou Treinador)
    @SerializedName("treinadorid")
    private int treinadorid;

    public Canal(int id, String nome, long visualizacoes, long seguidores, String bio, int treinadorid) {
        this.id = id;
        this.nome = nome;
        this.visualizacoes = visualizacoes;
        this.seguidores = seguidores;
        this.bio = bio;
        this.treinadorid = treinadorid;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(long visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public long getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(long seguidores) {
        this.seguidores = seguidores;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getTreinadorId() {
        return treinadorid;
    }

    public void setTreinadorId(int treinadorid) {
        this.treinadorid = treinadorid;
    }
}
