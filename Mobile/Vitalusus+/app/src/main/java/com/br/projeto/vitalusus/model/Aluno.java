package com.br.projeto.vitalusus.model;


import com.google.gson.annotations.SerializedName;

public class Aluno {

    @SerializedName("id")
    private Integer id;

    @SerializedName("dataNasc")
    private String dataNasc;

    @SerializedName("altura")
    private Float altura;

    @SerializedName("peso")
    private Float peso;

    private int usuario_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
