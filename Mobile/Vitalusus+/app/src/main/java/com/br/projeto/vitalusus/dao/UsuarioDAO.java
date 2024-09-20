//package com.br.projeto.vitalusus.dao;
//
//import com.br.projeto.vitalusus.conexao.Conexao;
//import com.br.projeto.vitalusus.model.Usuario;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class UsuarioDAO {
//    // preparando conexão
//    private Connection conn = null;
//
//    // Método para executar comandos SQL, com abertura e fechamento do banco.
//    private void executeSql(String sql) throws SQLException, ClassNotFoundException {
//        conn = Conexao.conectar();
//        if (conn != null) {
//            Statement st = conn.createStatement();
//            st.executeQuery(sql);
//            conn.close();
//        }
//    }
//
//    public void cadastrarUsuario(Usuario u) {
//        try {
//            executeSql("insert into Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, pSeguranca, rSeguranca) values ('" + u.getNome() + "', '" + u.getEmail() + "', '" + u.getSenha() + "', '" + "USER" + "', '" + u.getFoto() + "', '" + u.getpSeguranca() + "', '" + "2024-08-20 10:00:00" + "', '" + "ATIVO" + "', '" + "ALUNO" + "',  '" + u.getrSeguranca() + "')");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Usuario selecionarUsuario(String email, String senha) {
//        try {
//            conn = Conexao.conectar();
//            if (conn != null) {
//                String sql = "select * from Usuario where email = '" + email + "' and senha = '" + senha + "'";
//                Statement st = null;
//                st = conn.createStatement();
//
//                ResultSet rs = st.executeQuery(sql);
//                while (rs.next()) {
//                    Usuario usu = new Usuario();
//                    usu.setId(rs.getInt(1));
//                    usu.setNome(rs.getString(2));
//                    usu.setEmail(rs.getString(3));
//                    usu.setSenha(rs.getString(4));
//
//                    conn.close();
//                    return usu;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }
//
//    // Esqueci Senha
//    public Usuario consultaEmailUsuario(String email) {
//        try {
//            conn = Conexao.conectar();
//            if (conn != null) {
//                String sql = "select * from Usuario where email ='" + email + "'";
//                Statement st = null;
//                st = conn.createStatement();
//
//                ResultSet rs = st.executeQuery(sql);
//                while (rs.next()) {
//                    Usuario usu = new Usuario();
//
//                    usu.setpSeguranca(rs.getString(5));
//
//                    conn.close();
//                    return usu;
//                }
//
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }
//
//    public Usuario validarRespostaPSeguranca(String email, String rSeguranca) {
//        try {
//            conn = Conexao.conectar();
//            if (conn != null) {
//                String sql = "select * from Usuario where rSeguranca ='" + rSeguranca + "' and email ='" + email + "'";
//                Statement st = null;
//                st = conn.createStatement();
//
//                ResultSet rs = st.executeQuery(sql);
//                while (rs.next()) {
//                    Usuario usu = new Usuario();
//
//                    usu.setpSeguranca(rs.getString(5));
//                    usu.setrSeguranca(rs.getString(6));
//
//                    conn.close();
//                    return usu;
//                }
//
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }
//
//    public Usuario redefinirSenha(String email, String novaSenha) {
//        try {
//            conn = Conexao.conectar();
//            if (conn != null) {
//                String sql = "update Usuario set senha = '" + novaSenha + "' where email = '" + email + "'";
//                Statement st = null;
//                st = conn.createStatement();
//
//                ResultSet rs = st.executeQuery(sql);
//                while (rs.next()) {
//                    Usuario usu = new Usuario();
//
//                    usu.setEmail(rs.getString(3));
//                    usu.setSenha(rs.getString(4));
//
//                    conn.close();
//                    return usu;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }
//}
