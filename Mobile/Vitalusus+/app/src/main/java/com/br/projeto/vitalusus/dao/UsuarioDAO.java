package com.br.projeto.vitalusus.dao;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

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

    // Método para criptografar a senha
    private String criptografarSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Método para verificar se a senha fornecida corresponde ao hash armazenado
    private boolean verificarSenha(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }

    // Método para cadastrar um novo usuário
    public int cadastrarUsuario(Usuario u) {
        int usuarioId = -1; // ID padrão para o caso de falha
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                // SQL de inserção usando PreparedStatement
                String sql = "INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, chaveSeguranca, nivelPrivacidade, dataNasc, idade) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NEWID(), ?, ?, ?)";

                // Usamos RETURN_GENERATED_KEYS para obter o ID gerado
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                // Preenche os parâmetros do SQL na ordem especificada
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getEmail());

                // Criptografa a senha antes de armazená-la
                String senhaCriptografada = criptografarSenha(u.getSenha());
                stmt.setString(3, senhaCriptografada);

                stmt.setString(4, u.getNivelAcesso()); // Pode ser null, então verificações podem ser feitas no model
                stmt.setBytes(5, null); // Envia null para foto, pois não há como enviar a imagem pelo FormCadastro

                // Usa Timestamp para a data de cadastro, incluindo data e hora
                Timestamp dataCadastroAtual = new Timestamp(System.currentTimeMillis());
                stmt.setTimestamp(6, dataCadastroAtual); // Envia o Timestamp correto

                stmt.setString(7, u.getStatusUsuario());
                stmt.setString(8, u.getTipoUsuario());

                stmt.setString(9, u.getNivelPrivacidade());

                // Para a data de nascimento, use java.sql.Date
                stmt.setDate(10, new java.sql.Date(u.getDataNasc().getTime())); // Passa a data de nascimento corretamente

                // Calcula a idade
                int idadeCalculada = calcularIdade(u.getDataNasc());
                stmt.setInt(11, idadeCalculada);

                // Executa a inserção
                stmt.executeUpdate();

                // Obtém as chaves geradas (neste caso, o ID do usuário)
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuarioId = generatedKeys.getInt(1); // Captura o ID gerado
                }

                conn.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarioId; // Retorna o ID do usuário inserido
    }
    // Seleciona um usuário pelo email e senha
    public Usuario selecionarUsuario(String email, String senha) {
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Usuario WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String senhaArmazenada = rs.getString("senha");

                    // Verifica a senha
                    if (verificarSenha(senha, senhaArmazenada)) {
                        Usuario usu = new Usuario();
                        usu.setId(rs.getInt("id"));
                        usu.setNome(rs.getString("nome"));
                        usu.setEmail(rs.getString("email"));
                        usu.setSenha(rs.getString("senha"));
                        usu.setDataNasc(rs.getDate("dataNasc"));

                        int idade = calcularIdade(usu.getDataNasc());
                        usu.setIdade(idade);

                        conn.close();
                        return usu;
                    } else {
                        System.out.println("Senha incorreta.");
                    }
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
