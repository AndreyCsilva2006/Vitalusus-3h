package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;

public class Equipamento {

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("link")
    private String link;

    @SerializedName("patrocinador_id")
    private int patrocinador_id;

    @SerializedName("statusEquipamento")
    private String statusEquipamento;

    public Equipamento(int id, String nome, String link, int patrocinador_id, String statusEquipamento) {
        this.id = id;
        this.nome = nome;
        this.link = link;
        this.patrocinador_id = patrocinador_id;
        this.statusEquipamento = statusEquipamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPatrocinador_id() {
        return patrocinador_id;
    }

    public void setPatrocinador_id(int patrocinador_id) {
        this.patrocinador_id = patrocinador_id;
    }

    public String getStatusEquipamento() {
        return statusEquipamento;
    }

    public void setStatusEquipamento(String statusEquipamento) {
        this.statusEquipamento = statusEquipamento;
    }
}
