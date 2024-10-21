package com.br.projeto.vitalusus.dao;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UsuarioDAO {
    private Connection conn = null;

    // Método para calcular a idade
    private int calcularIdade(Date dataNasc) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(dataNasc);

        Calendar hoje = Calendar.getInstance();
        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH) ||
                (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) &&
                        hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH))) {
            idade--;
        }

        return idade;
    }

    // Método para cadastrar um novo usuário
    public void cadastrarUsuario(Usuario u) {
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                // SQL de inserção usando PreparedStatement
                String sql = "INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, dataNasc, idade, nivelPrivacidade) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(sql);

                // Preenche os parâmetros do SQL
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getEmail());
                stmt.setString(3, u.getSenha());
                stmt.setString(4, u.getNivelAcesso());
                stmt.setBytes(5, null); // Envia null para foto

                // Usa Timestamp para a data de cadastro, incluindo data e hora
                Timestamp dataCadastroAtual = new Timestamp(System.currentTimeMillis());
                stmt.setTimestamp(6, dataCadastroAtual); // Envia o Timestamp correto

                stmt.setString(7, u.getStatusUsuario());
                stmt.setString(8, u.getTipoUsuario());

                // Para a data de nascimento, use java.sql.Date
                stmt.setDate(9, new java.sql.Date(u.getDataNasc().getTime())); // Passa a data de nascimento corretamente

                // Calcula a idade
                int idadeCalculada = calcularIdade(u.getDataNasc());
                stmt.setInt(10, idadeCalculada);

                stmt.setString(11, u.getNivelPrivacidade());

                // Executa a inserção
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Seleciona um usuário pelo email e senha
    public Usuario selecionarUsuario(String email, String senha) {
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, senha);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Usuario usu = new Usuario();
                    usu.setId(rs.getInt("id"));
                    usu.setNome(rs.getString("nome"));
                    usu.setEmail(rs.getString("email"));
                    usu.setSenha(rs.getString("senha"));
                    usu.setDataNasc(rs.getDate("dataNasc"));

                    // Calcula a idade
                    int idade = calcularIdade(usu.getDataNasc());
                    usu.setIdade(idade);

                    conn.close();
                    return usu;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Redefinir a senha de um usuário
    public void redefinirSenha(String email, String novaSenha) {
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "UPDATE Usuario SET senha = ? WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, novaSenha);
                stmt.setString(2, email);
                stmt.executeUpdate();
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
