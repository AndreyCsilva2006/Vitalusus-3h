package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Aluno implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("altura")
    private double altura;

    @SerializedName("peso")
    private double peso;

    @SerializedName("usuario_id")
    private int usuario_id;

    @SerializedName("sexo")
    private int sexo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
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

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", altura=" + altura +
                ", peso=" + peso +
                ", usuario_id=" + usuario_id +
                '}';
    }
}
