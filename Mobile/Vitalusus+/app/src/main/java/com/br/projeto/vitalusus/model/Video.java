package com.br.projeto.vitalusus.model;

public class Video {
    private long id;
    private String titulo;
    private String dataPostagem;
    private Canal canal;
    private byte[] thumbnail; // Thumbnail do vídeo

    // Construtor com parâmetros
    public Video(long id, String titulo, String dataPostagem, Canal canal, byte[] thumbnail) {
        this.id = id;
        this.titulo = titulo;
        this.dataPostagem = dataPostagem;
        this.canal = canal;
        this.thumbnail = thumbnail;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(String dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
