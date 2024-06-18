package com.br.projeto.vitalusus;

import java.io.Serializable;

public class Aluno implements Serializable {

    // Atributos
    private Integer id;
    private String nome;
    private String email;
    private String senha;

    // pergunta de segurança
    private String pSeguranca;
    // resposta da pergunta de segurança
    private String rSeguranca;

    // Getter's and Setter's
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getpSeguranca() {
        return pSeguranca;
    }

    public void setpSeguranca(String pSeguranca) {
        this.pSeguranca = pSeguranca;
    }

    public String getrSeguranca() {
        return rSeguranca;
    }

    public void setrSeguranca(String rSeguranca) {
        this.rSeguranca = rSeguranca;
    }

    // Sobrescrevendo o metodo toString() para uma vizualização melhor dos Alunos.
    @Override
    public String toString() {
        return nome;
    }
}
