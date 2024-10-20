package com.br.projeto.vitalusus.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    @SerializedName("nivel_acesso")
    private String nivelAcesso;

    @SerializedName("foto")
    private String foto;

    @SerializedName("data_cadastro")
    private String dataCadastro;

    @SerializedName("status_usuario")
    private String statusUsuario;

    @SerializedName("tipo_usuario")
    private String tipoUsuario;

    @SerializedName("nivel_privacidade")
    private String nivelPrivacidade;

    @SerializedName("data_nasc")
    private Date dataNasc; // Mudei para String

    @SerializedName("idade")
    private int idade;

    public Usuario(String nome, String email, String senha, String nivelAcesso, String foto, String dataCadastro, String statusUsuario, String tipoUsuario, String nivelPrivacidade, int idade, Date dataNasc) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.foto = foto;
        this.dataCadastro = dataCadastro;
        this.statusUsuario = statusUsuario;
        this.tipoUsuario = tipoUsuario;
        this.nivelPrivacidade = nivelPrivacidade;
        this.idade = idade;
        this.dataNasc = dataNasc; // Mudei para String
    }

    // Getters e Setters
    // ... [mesmo código dos getters e setters] ...

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

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(String statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNivelPrivacidade() {
        return nivelPrivacidade;
    }

    public void setNivelPrivacidade(String nivelPrivacidade) {
        this.nivelPrivacidade = nivelPrivacidade;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nivelAcesso='" + nivelAcesso + '\'' +
                ", foto='" + foto + '\'' +
                ", dataCadastro='" + dataCadastro + '\'' +
                ", statusUsuario='" + statusUsuario + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", nivelPrivacidade='" + nivelPrivacidade + '\'' +
                ", dataNasc='" + dataNasc + '\'' +
                ", idade=" + idade +
                '}';
    }
}
