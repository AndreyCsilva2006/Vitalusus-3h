package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;

public class Aluno implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("altura")
    private BigDecimal altura;

    @SerializedName("peso")
    private BigDecimal peso;

    @SerializedName("usuario_id")
    private int usuario_id;

    @SerializedName("sexo")
    private String sexo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
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
                ", sexo" + sexo +
                '}';
    }
}
