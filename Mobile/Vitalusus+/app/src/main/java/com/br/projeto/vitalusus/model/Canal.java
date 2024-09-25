package com.br.projeto.vitalusus.model;

import java.math.BigInteger;

public class Canal {
    private Integer id;
    private String nome;
    private BigInteger visualizacoes;
    private BigInteger seguidores;

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
}
