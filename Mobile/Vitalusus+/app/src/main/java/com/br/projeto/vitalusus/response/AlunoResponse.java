package com.br.projeto.vitalusus.response;

public class AlunoResponse {
        private String nome;
        private String email;
        private String senha;
        private Double altura;
        private Double peso;
        private String sexo;
        private int usuarioId;

        // Getters e setters
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }

        public Double getAltura() { return altura; }
        public void setAltura(Double altura) { this.altura = altura; }

        public Double getPeso() { return peso; }
        public void setPeso(Double peso) { this.peso = peso; }

        public String getSexo() { return sexo; }
        public void setSexo(String sexo) { this.sexo = sexo; }

        public int getUsuarioId() { return usuarioId; }
        public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    }



