package com.br.projeto.vitalusus.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import net.sourceforge.jtds.jdbc.DateTime;

public class Usuario {

    @SerializedName("id")
    private Integer id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    @SerializedName("nivelAcesso")
    private String nivelAcesso;

    @SerializedName("foto")
    private Bitmap foto;

    @SerializedName("dataCadastro")
    private String dataCadastro;

    @SerializedName("statusUsuario")
    private String statusUsuario;

    @SerializedName("tipoUsuario")
    private String tipoUsuario;

    @SerializedName("chaveSegurancaId")
    private String chaveSegurancaId;

    @SerializedName("nivelPrivacidade")
    private String nivelPrivacidade;

    public Usuario(Integer id, String nome, String email, String senha, String nivelAcesso, Bitmap foto, String dataCadastro, String statusUsuario, String tipoUsuario, String chaveSegurancaId, String nivelPrivacidade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.foto = foto;
        this.dataCadastro = dataCadastro;
        this.statusUsuario = statusUsuario;
        this.tipoUsuario = tipoUsuario;
        this.chaveSegurancaId = chaveSegurancaId;
        this.nivelPrivacidade = nivelPrivacidade;
    }

    //    Getter e Setter

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

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
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

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getChaveSegurancaId() {
        return chaveSegurancaId;
    }

    public void setChaveSegurancaId(String chaveSegurancaId) {
        this.chaveSegurancaId = chaveSegurancaId;
    }

    public String getNivelPrivacidade() {
        return nivelPrivacidade;
    }

    public void setNivelPrivacidade(String nivelPrivacidade) {
        this.nivelPrivacidade = nivelPrivacidade;
    }
}
