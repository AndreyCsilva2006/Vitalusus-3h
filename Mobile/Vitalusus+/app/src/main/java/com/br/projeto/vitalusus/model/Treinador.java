package com.br.projeto.vitalusus.model;

public class Treinador {
    private Integer id;
    private String cref;
    private String dataNasc;
    private Integer usuario_id;

    public Treinador(Integer id, String cref, String dataNasc, Integer usuario_id) {
        this.id = id;
        this.cref = cref;
        this.dataNasc = dataNasc;
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

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }


}
