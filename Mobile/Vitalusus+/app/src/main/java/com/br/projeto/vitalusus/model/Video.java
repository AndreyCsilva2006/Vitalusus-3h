package com.br.projeto.vitalusus.model;

import com.br.projeto.vitalusus.model.Canal;

public class Video {
    private int id;
    private String descricao;
    private String titulo;
    private int likes;
    private int deslikes;
    private long visualizacoes;
    private String dataPubli; // Formato de data
    private String categoria;
    private String tipoVideoaula;
    private byte[] thumbnail; // Imagem em bytes

    private Canal canal; // Para vincular ao canal

    // Getters e setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDeslikes() {
        return deslikes;
    }

    public void setDeslikes(int deslikes) {
        this.deslikes = deslikes;
    }

    public long getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(long visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public String getDataPubli() {
        return dataPubli;
    }

    public void setDataPubli(String dataPubli) {
        this.dataPubli = dataPubli;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoVideoaula() {
        return tipoVideoaula;
    }

    public void setTipoVideoaula(String tipoVideoaula) {
        this.tipoVideoaula = tipoVideoaula;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }
}
