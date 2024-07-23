package com.br.projeto.vitalusus.model;

import android.graphics.drawable.BitmapDrawable;

import java.math.BigInteger;

public class Canal {

    private Integer id;
    private String nome;
    private Integer visualizacoes;
    private BigInteger seguidores;
    private BitmapDrawable foto;

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

    public Integer getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(Integer visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public BigInteger getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(BigInteger seguidores) {
        this.seguidores = seguidores;
    }

    public BitmapDrawable getFoto() {
        return foto;
    }

    public void setFoto(BitmapDrawable foto) {
        this.foto = foto;
    }
}