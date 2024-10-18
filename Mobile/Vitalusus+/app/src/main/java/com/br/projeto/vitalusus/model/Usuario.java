package com.br.projeto.vitalusus.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import net.sourceforge.jtds.jdbc.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String foto;

    @SerializedName("dataCadastro")
    private String dataCadastro;

    @SerializedName("statusUsuario")
    private String statusUsuario;

    @SerializedName("tipoUsuario")
    private String tipoUsuario;

    @SerializedName("chaveSegurancaId")
    private int chaveSegurancaId;

    @SerializedName("nivelPrivacidade")
    private String nivelPrivacidade;

    @SerializedName("dataNasc")
    private Date dataNasc;

    @SerializedName("idade")
    private int idade;

    @SerializedName("sexo")
    private String sexo;

    public Usuario(String nome, String email, String senha, String nivelAcesso, String foto, String dataCadastro, String statusUsuario, String tipoUsuario, String nivelPrivacidade, int idade, Date dataNasc, String sexo) {
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
        this.dataNasc = dataNasc;
        this.sexo = sexo;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getChaveSegurancaId() {
        return chaveSegurancaId;
    }

    public void setChaveSegurancaId(int chaveSegurancaId) {
        this.chaveSegurancaId = chaveSegurancaId;
    }

    public String getNivelPrivacidade() {
        return nivelPrivacidade;
    }

    public void setNivelPrivacidade(String nivelPrivacidade) {
        this.nivelPrivacidade = nivelPrivacidade;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email=" + email +
                ", senha=" + senha +
                ", nivelAcesso=" + nivelAcesso +
                ", foto=" + foto +
                ", dataCadastro=" + dataCadastro +
                ", statusUsuario=" + statusUsuario +
                ", tipoUsuario=" + tipoUsuario +
                ", chaveSegurancaId=" + chaveSegurancaId +
                ", nivelPrivacidade=" + nivelPrivacidade +
                ", dataNasc=" + dataNasc +
                ", idade=" + idade +
                ", sexo=" + sexo +
                '}';
    }
}
