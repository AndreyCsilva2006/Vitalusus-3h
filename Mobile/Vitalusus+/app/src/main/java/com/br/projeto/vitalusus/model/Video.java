package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

public class Video implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("likes")
    private int likes;

    @SerializedName("deslikes")
    private int deslikes;

    @SerializedName("canal_id")
    private int canal_id;

    @SerializedName("visualizacoes")
    private BigInteger visualizacoes;

    @SerializedName("video")
    private String video;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("dataPubli")
    private String dataPubli;

    @SerializedName("categoria")
    private String categoria;

    @SerializedName("tags")
    private String tags;

    @SerializedName("equipamento_id")
    private Integer equipamento_id;

    @SerializedName("statusVideo")
    private String statusVideo;

    @SerializedName("privacidadeVideo")
    private String privacidadeVideo;

    // construtor

    public Video(int id, String descricao, String titulo, int likes, int deslikes, int canal_id, BigInteger visualizacoes, String video, String thumbnail, String dataPubli, String categoria, String tags, Integer equipamento_id, String statusVideo, String privacidadeVideo) {
        this.id = id;
        this.descricao = descricao;
        this.titulo = titulo;
        this.likes = likes;
        this.deslikes = deslikes;
        this.canal_id = canal_id;
        this.visualizacoes = visualizacoes;
        this.video = video;
        this.thumbnail = thumbnail;
        this.dataPubli = dataPubli;
        this.categoria = categoria;
        this.tags = tags;
        this.equipamento_id = equipamento_id;
        this.statusVideo = statusVideo;
        this.privacidadeVideo = privacidadeVideo;
    }

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

    public int getCanal_id() {
        return canal_id;
    }

    public void setCanal_id(int canal_id) {
        this.canal_id = canal_id;
    }

    public BigInteger getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(BigInteger visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getEquipamento_id() {
        return equipamento_id;
    }

    public void setEquipamento_id(Integer equipamento_id) {
        this.equipamento_id = equipamento_id;
    }

    public String getStatusVideo() {
        return statusVideo;
    }

    public void setStatusVideo(String statusVideo) {
        this.statusVideo = statusVideo;
    }

    public String getPrivacidadeVideo() {
        return privacidadeVideo;
    }

    public void setPrivacidadeVideo(String privacidadeVideo) {
        this.privacidadeVideo = privacidadeVideo;
    }
}
