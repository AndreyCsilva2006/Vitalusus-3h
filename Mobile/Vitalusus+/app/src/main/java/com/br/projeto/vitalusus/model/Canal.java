package com.br.projeto.vitalusus.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.br.projeto.vitalusus.model.Treinador;

import java.math.BigInteger;

public class Canal {
    private Integer id;
    private String nome;
    private BigInteger seguidores;
    private byte[] foto;

    public Canal() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigInteger getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(BigInteger seguidores) {
        this.seguidores = seguidores;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}


