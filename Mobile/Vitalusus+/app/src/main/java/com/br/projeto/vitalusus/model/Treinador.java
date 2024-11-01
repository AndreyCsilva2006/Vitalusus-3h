package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Treinador implements Serializable {

    private static final long serialVersionUID = 2L;

    @SerializedName("id")
    private Integer id;

    @SerializedName("cref")
    private String cref;

    @SerializedName("usuario_id")
    private Integer usuario_id;

    public Treinador(Integer id, String cref, Integer usuario_id) {
        this.id = id;
        this.cref = cref;
        this.usuario_id = usuario_id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public Integer getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    @Override
    public String toString() {
        return "Treinador{" +
                "id=" + id +
                ", cref='" + cref + '\'' +
                ", usuario_id=" + usuario_id +
                '}';
    }


}
