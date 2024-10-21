package com.br.projeto.vitalusus.dao;

import android.util.Log;
import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlunoDAO {
    private Connection conn = null;

    // Método para cadastrar um aluno com base no ID do usuário
    public void cadastrarAluno(int usuarioId, Aluno aluno) {
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                // Insere o aluno usando o ID do usuário já cadastrado
                String sqlAluno = "INSERT INTO Aluno (altura, peso, usuario_id, sexo) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno);

                // Insere NULL para altura e peso, e o ID do usuário e sexo fornecido
                stmtAluno.setNull(1, java.sql.Types.DECIMAL); // Altura como NULL
                stmtAluno.setNull(2, java.sql.Types.DECIMAL); // Peso como NULL
                stmtAluno.setInt(3, usuarioId);               // ID do usuário
                stmtAluno.setString(4, aluno.getSexo());      // Sexo do aluno

                // Executa a inserção
                stmtAluno.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO", e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Garantir que a conexão seja fechada
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
