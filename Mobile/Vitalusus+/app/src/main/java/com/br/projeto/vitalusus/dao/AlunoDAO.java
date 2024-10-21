package com.br.projeto.vitalusus.dao;

import android.util.Log;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlunoDAO {
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

    // Método para cadastrar um aluno
    public int cadastrarAluno(Aluno a, Usuario u) {
        int usuarioId = -1; // Inicializa como -1 para indicar erro
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                // Primeiro insere o usuário
                String sqlUsuario = "INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc, idade) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
                stmtUsuario.setString(1, u.getNome());
                stmtUsuario.setString(2, u.getEmail());
                stmtUsuario.setString(3, u.getSenha());
                stmtUsuario.setString(4, "USER"); // nível de acesso padrão
                stmtUsuario.setBytes(5, null); // Envia null para foto
                stmtUsuario.setString(6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // Data com hora
                stmtUsuario.setString(7, "ATIVO"); // status do usuário
                stmtUsuario.setString(8, "ALUNO"); // tipo do usuário
                stmtUsuario.setString(9, u.getNivelPrivacidade());
                stmtUsuario.setDate(10, new java.sql.Date(u.getDataNasc().getTime())); // Data de nascimento
                stmtUsuario.setInt(11, calcularIdade(u.getDataNasc())); // Idade calculada

                // Executa a inserção e obtém a chave gerada
                stmtUsuario.executeUpdate();
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuarioId = generatedKeys.getInt(1); // Obtém a chave gerada
                }

                // Agora insere o aluno
                if (usuarioId != -1) { // Verifica se o ID do usuário foi gerado com sucesso
                    String sqlAluno = "INSERT INTO Aluno (usuario_id, sexo) VALUES (?, ?)";
                    PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno);
                    stmtAluno.setInt(1, usuarioId);
                    stmtAluno.setString(2, a.getSexo());
                    stmtAluno.executeUpdate(); // Executa a inserção do aluno
                }
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
        return usuarioId; // Retorna o ID do usuário cadastrado ou -1 em caso de erro
    }

    // Método para alterar informações de um aluno
    public void alterar(Aluno a) {
        try {
            conn = Conexao.conectar();
            String sql = "UPDATE Aluno SET sexo = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, a.getSexo());
            stmt.setInt(2, a.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    // Método para excluir um aluno
    public void excluir(Aluno a) {
        try {
            conn = Conexao.conectar();
            String sql = "DELETE FROM Aluno WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    // Método para encontrar um aluno pelo ID
    public Aluno findById(Integer id) {
        Aluno alu = null;
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Aluno WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    alu = new Aluno();
                    alu.setId(rs.getInt("id"));
                    alu.setSexo(rs.getString("sexo"));
                    // Adicionar outros campos necessários conforme o modelo Aluno
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO.java no método findById(Integer id)", e.getMessage());
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
        return alu; // Retorna o aluno encontrado ou null
    }

    // Método para obter uma lista de alunos
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

    // Método para obter todos os alunos
    public List<Aluno> getAll() {
        List<Aluno> lista = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Aluno";
                lista = getAluno(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO.java no método getAll", e.getMessage());
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
        return lista;
    }

    // Método para buscar alunos com base em uma pesquisa
    public List<Aluno> getAll(String busca) {
        List<Aluno> lista = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Aluno WHERE usuario_id IN (SELECT id FROM Usuario WHERE nome LIKE ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + busca + "%");
                lista = getAluno(stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Erro no AlunoDAO.java no método getAll(String busca)", e.getMessage());
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
        return lista;
    }

    // Método auxiliar para obter alunos a partir de um PreparedStatement
    private List<Aluno> getAluno(PreparedStatement stmt) throws SQLException {
        List<Aluno> lista = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Aluno alu = new Aluno();
            alu.setId(rs.getInt("id"));
            alu.setSexo(rs.getString("sexo"));
            // Adicionar outros campos necessários conforme o modelo Aluno

            lista.add(alu);
        }
        return lista;
    }
}
