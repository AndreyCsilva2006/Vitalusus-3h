package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Treinador implements Serializable {

    private static final long serialVersionUID = 2L;

    @SerializedName("id")
    private Integer id;

    @SerializedName("cref")
    private String cref;

    @SerializedName("dataNasc")
    private String dataNasc;

    @SerializedName("usuarioId")
    private Integer usuarioId;

    public Treinador(Integer id, String cref, String dataNasc, Integer usuario_id) {
        this.id = id;
        this.cref = cref;
        this.dataNasc = dataNasc;
        this.usuarioId = usuarioId;
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

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuario_id) {
        this.usuarioId = usuarioId;
    }


}
