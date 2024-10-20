package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

public class Comentario {

    private static final long serialVersionUID = 4L;

    @SerializedName("id")
    private int id;

    @SerializedName("texto")
    private String texto;

    @SerializedName("aluno_id")
    private int aluno_id;

    @SerializedName("videoaula_id")
    private int videoaula_id;

    @SerializedName("dataPubli")
    private String dataPubli;

    public Comentario(int id, String texto, int aluno_id, int videoaula_id, String dataPubli) {
        this.id = id;
        this.texto = texto;
        this.aluno_id = aluno_id;
        this.videoaula_id = videoaula_id;
        this.dataPubli = dataPubli;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(int aluno_id) {
        this.aluno_id = aluno_id;
    }

    public int getVideoaula_id() {
        return videoaula_id;
    }

    public void setVideoaula_id(int videoaula_id) {
        this.videoaula_id = videoaula_id;
    }

    public String getDataPubli() {
        return dataPubli;
    }

    public void setDataPubli(String dataPubli) {
        this.dataPubli = dataPubli;
    }
}
