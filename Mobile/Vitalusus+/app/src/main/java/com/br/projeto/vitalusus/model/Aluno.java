package com.br.projeto.vitalusus.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Aluno implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("dataNasc")
    private String dataNasc;

    @SerializedName("altura")
    private double altura;

    @SerializedName("peso")
    private double peso;

    @SerializedName("usuario_id")
    private int usuario_id;

    public Aluno(String dataNasc, double altura, double peso, int usuario_id) {
        this.dataNasc = dataNasc;
        this.altura = altura;
        this.peso = peso;
        this.usuario_id = usuario_id;
    }

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

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
