package com.br.projeto.vitalusus.dao;

import android.util.Log;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    // preparando conexão
    private Connection conn = null;

    // Método para executar comandos SQL, com abertura e fechamento do banco.
    private void executeSql(String sql) throws SQLException, ClassNotFoundException {
        conn = Conexao.conectar();
        if (conn != null) {
            Statement st = conn.createStatement();
            st.executeUpdate(sql); // Corrigido para executeUpdate para comandos INSERT, UPDATE, DELETE
            conn.close();
        }
    }

    public void cadastrarAluno(Aluno a, Usuario u) {
        try {
            // Primeiro insere o usuário e depois o aluno
            String sqlUsuario = "INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc, idade) " +
                    "VALUES ('" + u.getNome() + "', '" + u.getEmail() + "', '" + u.getSenha() + "', 'USER', NULL, '" +
                    u.getDataCadastro() + "', 'ATIVO', 'ALUNO', '" + u.getNivelPrivacidade() + "', '" +
                    new SimpleDateFormat("yyyy-MM-dd").format(u.getDataNasc()) + "', " + u.getIdade() + ")";
            executeSql(sqlUsuario);

            // Depois, pega o último ID inserido do usuário
            String sqlIdUsuario = "SELECT SCOPE_IDENTITY() AS usuario_id";
            conn = Conexao.conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlIdUsuario);
            int usuarioId = 0;
            if (rs.next()) {
                usuarioId = rs.getInt("usuario_id");
            }

            // Agora insere o aluno
            String sqlAluno = "INSERT INTO Aluno (usuario_id, sexo) VALUES (" + usuarioId + ", '" + a.getSexo() + "')";
            executeSql(sqlAluno);

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void alterar(Aluno a) {
        try {
            executeSql("UPDATE Aluno SET sexo = '" + a.getSexo() + "' WHERE id = " + a.getId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void excluir(Aluno a) {
        try {
            executeSql("DELETE FROM Aluno WHERE id = " + a.getId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Aluno findById(Integer id) {
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Aluno WHERE id = " + id;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    Aluno alu = new Aluno();
                    alu.setId(rs.getInt("id"));
                    alu.setSexo(rs.getString("sexo"));
                    // Adicionar outros campos necessários conforme o modelo Aluno

                    conn.close();
                    return alu;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO.java no método findById(Integer id)", e.getMessage());
        }
        return null;
    }

    private List<Aluno> getAluno(String sql) throws SQLException {
        List<Aluno> lista = new ArrayList<>();

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Aluno alu = new Aluno();
            alu.setId(rs.getInt("id"));
            alu.setSexo(rs.getString("sexo"));
            // Adicionar outros campos necessários conforme o modelo Aluno

            lista.add(alu);
        }
        return lista;
    }

    public List<Aluno> getAll() {
        List<Aluno> lista = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Aluno";
                lista = getAluno(sql);
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO.java no método getAll", e.getMessage());
        }
        return lista;
    }

    public List<Aluno> getAll(String busca) {
        List<Aluno> lista = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Aluno WHERE nome LIKE '%" + busca + "%'";
                lista = getAluno(sql);
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO.java no método getAll(String busca)", e.getMessage());
        }
        return lista;
    }
}
