package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoResponse implements Serializable {

    @SerializedName("video")
    private Video video;

    @SerializedName("canal")
    private Canal canal;

    @SerializedName("treinador")
    private Treinador treinador;

    @SerializedName("usuario")
    private Usuario usuario;

    public VideoResponse(Video video, Canal canal, Treinador treinador, Usuario usuario) {
        this.video = video;
        this.canal = canal;
        this.treinador = treinador;
        this.usuario = usuario;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
