package com.br.projeto.vitalusus.dao;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

    // Método para cadastrar Aluno associando ao Usuario
    public void cadastrarAluno(Aluno aluno, Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmtAluno = null;
        PreparedStatement stmtUsuario = null;
        ResultSet rsUsuario = null;

        try {
            // Conecta ao banco de dados
            conn = Conexao.conectar();

            // Desabilita o auto-commit para transação
            conn.setAutoCommit(false);

            // Inserir o usuário na tabela Usuario
            String sqlUsuario = "INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc, idade) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtUsuario = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, usuario.getNome());
            stmtUsuario.setString(2, usuario.getEmail());
            stmtUsuario.setString(3, usuario.getSenha());
            stmtUsuario.setString(4, usuario.getNivelAcesso());
            stmtUsuario.setBytes(5, null); // Foto como null
            stmtUsuario.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis())); // Data de cadastro
            stmtUsuario.setString(7, usuario.getStatusUsuario());
            stmtUsuario.setString(8, usuario.getTipoUsuario());
            stmtUsuario.setString(9, usuario.getNivelPrivacidade());
            stmtUsuario.setDate(10, new java.sql.Date(usuario.getDataNasc().getTime())); // Data de nascimento
            stmtUsuario.setInt(11, usuario.getIdade());

            // Executa a inserção de Usuario
            stmtUsuario.executeUpdate();

            // Recupera o ID do usuário recém-inserido
            rsUsuario = stmtUsuario.getGeneratedKeys();
            if (rsUsuario.next()) {
                int usuarioId = rsUsuario.getInt(1); // ID gerado

                // Insere o Aluno na tabela Aluno, associando ao Usuario
                String sqlAluno = "INSERT INTO Aluno (sexo, usuario_id) VALUES (?, ?)";
                stmtAluno = conn.prepareStatement(sqlAluno);
                stmtAluno.setString(1, aluno.getSexo());
                stmtAluno.setInt(2, usuarioId);

                // Executa a inserção de Aluno
                stmtAluno.executeUpdate();
            }

            // Comita a transação
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback em caso de falha
            }
            throw e;
        } finally {
            if (rsUsuario != null) rsUsuario.close();
            if (stmtAluno != null) stmtAluno.close();
            if (stmtUsuario != null) stmtUsuario.close();
            if (conn != null) conn.close();
        }
    }
}
