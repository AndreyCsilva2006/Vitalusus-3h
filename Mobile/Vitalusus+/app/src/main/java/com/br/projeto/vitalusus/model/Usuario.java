package com.br.projeto.vitalusus.model;

import android.graphics.Bitmap;

import net.sourceforge.jtds.jdbc.DateTime;

public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String nivelAcesso;
    private Bitmap foto;
    private DateTime dataCadastro;
    private String statusUsuario;
    private String tipoUsuario;
    private String pSeguranca;
    private String rSeguranca;

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

    public DateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(DateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(String statusUsuario) {
        this.statusUsuario = statusUsuario;
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
}
