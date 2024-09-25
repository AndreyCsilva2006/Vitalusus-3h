//package com.br.projeto.vitalusus.dao;
//
//import android.util.Log;
//
//import com.br.projeto.vitalusus.EsqueciSenhaActivity;
//import com.br.projeto.vitalusus.conexao.Conexao;
//import com.br.projeto.vitalusus.model.Aluno;
//import com.br.projeto.vitalusus.model.Usuario;
//import com.br.projeto.vitalusus.util.MensagemUtil;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//// DAO - Data Access Object (Objeto de Acesso de Dados)
//public class AlunoDAO {
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
//    public void cadastrarAluno(Aluno a, Usuario u) {
//        try {
//            executeSql("insert into Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, pSeguranca, rSeguranca) values ('" + u.getNome() + "', '" + u.getEmail() + "', '" + u.getSenha() + "', '" + "USER" + "', '" + "null" + "', '" + u.getpSeguranca() + "', '" + "2024-08-20 10:00:00" + "', '" + "ATIVO" + "', '" + "ALUNO" + "',  '" + u.getrSeguranca() + "')");
////            executeSql("insert into Aluno (dataNasc, altura, peso, usuario_id) " + "values ('" + "1990-02-12" + "', '" + a.getAltura() + "', '" + a.getPeso() + "', '" + "3" + "')");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // alterar aluno, no UsuarioDAO, alterar o Usuario
////    public void alterar(Aluno a) {
////        try {
////            executeSql("update Aluno set Nome = '" + a.getNome() + "', Email = '" + a.getEmail() + "', Senha = '" + a.getSenha() + "' where id = " + a.getId());
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
////    }
//
//    // excluir aluno
////    public void excluir(Aluno a) {
////        try {
////            executeSql("delete from Aluno where id = " + a.getId());
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
////    }
//
////    public Aluno findById(Integer id) {
////        List<Aluno> lista = new ArrayList<Aluno>();
////        try {
////            conn = Conexao.conectar();
////            if (conn != null) {
////                String sql = "select * from Aluno where id = " + id;
////
////                Statement st = conn.createStatement();
////                ResultSet rs = st.executeQuery(sql);
////                while (rs.next()) {
////                    Aluno alu = new Aluno();
////                    alu.setId(rs.getInt(1));
////                    alu.setNome(rs.getString(2));
////                    alu.setEmail(rs.getString(3));
////                    alu.setSenha(rs.getString(4));
//////                    alu.setpSeguranca(rs.getString(5));
//////                    alu.setrSeguranca(rs.getString(6));
////
////                    conn.close();
////                    return alu;
////                }
////
////            }
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////            Log.e("Erro no AlunoDAO.java no método findById(Integer id)", e.getMessage());
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////            Log.e("Erro no AlunoDAO.java no método findById(Integer id)", throwables.getMessage());
////        }
////
////        return null;
////    }
////    private List<Aluno> getAluno(String sql) throws SQLException {
////        List<Aluno> lista = new ArrayList<Aluno>();
////
////        Statement st = null;
////        st = conn.createStatement();
////        ResultSet rs = st.executeQuery(sql);
////        while (rs.next()) {
////            Aluno alu = new Aluno();
////            alu.setId(rs.getInt(1));
////            alu.setNome(rs.getString(2));
////            alu.setEmail(rs.getString(3));
////            alu.setSenha(rs.getString(4));
//////                    alu.setpSeguranca(rs.getString(5));
//////                    alu.setrSeguranca(rs.getString(6));
////
////            lista.add(alu);
////        }
////        return lista;
////    }
////
////    // getAll
////    public List<Aluno> getAll() {
////        List<Aluno> lista = new ArrayList<Aluno>();
////        try {
////            conn = Conexao.conectar();
////            if (conn != null) {
////                String sql = "select * from Aluno";
////                lista = getAluno(sql);
////
////                conn.close();
////            }
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////            Log.e("Erro no AlunoDAO.java no método getAll", e.getMessage());
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////            Log.e("Erro no AlunoDAO.java no método getAll", throwables.getMessage());
////        }
////        return lista;
////    }
////
////    // getAll com pesquisa
////    public List<Aluno> getAll(String busca) {
////        List<Aluno> lista = new ArrayList<Aluno>();
////        try {
////            conn = Conexao.conectar();
////            if (conn != null) {
////                String sql = "select * from Aluno where nome like '%" + busca + "%'";
////                lista = getAluno(sql);
////
////                conn.close();
////            }
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////            Log.e("Erro no AlunoDAO.java no método getAll(String busca)", e.getMessage());
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////            Log.e("Erro no AlunoDAO.java no método getAll(String busca)", throwables.getMessage());
////        }
////        return lista;
////    }
//}
