package com.br.projeto.vitalusus.model;

public class Treinador {
    private String nome;
    private String descricao;
    private Canal canal;  // Associação com a classe Canal

    public Treinador(String nome, String descricao, Canal canal) {
        this.nome = nome;
        this.descricao = descricao;
        this.canal = canal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }
}
