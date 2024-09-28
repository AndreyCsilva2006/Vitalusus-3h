package com.br.projeto.vitalusus.response;

import com.br.projeto.vitalusus.model.Usuario;

import java.util.List;

public class UsuarioResponse {
    private String status;
    private List<Usuario> usuarios;

    // Getters e setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}
