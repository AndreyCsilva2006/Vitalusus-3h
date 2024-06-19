package com.br.projeto.vitalusus.dao;

import static android.provider.Settings.System.getString;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Aluno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// DAO - Data Access Object (Objeto de Acesso de Dados)
public class AlunoDAO {

    public Aluno selecionarAluno(String email, String senha){
        try {
            Connection conn = Conexao.conectar();
            if(conn != null){
                String sql = "select * from Aluno where email = '"+email+"' and senha = '"+senha+"'";
                Statement st = null;
                st = conn.createStatement();

                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    Aluno alu = new Aluno();
                    alu.setId(rs.getInt(1));
                    alu.setNome(rs.getString(2));
                    alu.setEmail(rs.getString(3));
                    alu.setSenha(rs.getString(4));
                    alu.setpSeguranca(rs.getString(5));
                    alu.setrSeguranca(rs.getString(6));

                    conn.close();
                    return alu;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    // versão antiga
//    private Conexao conexao;
//    private SQLiteDatabase banco;
//
//    // Metódo Construtor
//    public AlunoDAO(Context context){
//        conexao = new Conexao(context);
//        banco = conexao.getWritableDatabase();
//    }
//
//    public long inserir(Aluno aluno) {
//        // values é a váriavel que recebe o conteúdo para pôr no banco de dados
//        ContentValues values = new ContentValues();
//        values.put("nome", aluno.getNome());
//        values.put("email", aluno.getEmail());
//        values.put("senha", aluno.getSenha());
//        values.put("pSeguranca", aluno.getpSeguranca());
//        values.put("rSeguranca", aluno.getrSeguranca());
//
//        //           tabela   nullColumnHack(Colunas vazias ou não)
//        return banco.insert("aluno", null, values);
//    }
//
//    // metodo para obter todos a lista
//    public List<Aluno> obterTodos() {
//        List<Aluno> alunos = new ArrayList<Aluno>();
//        // cursor aponta para as linhas
//        // parametros query para consultar: Tabela(aluno) vetor String que retornará as colunas. (null para o selection, selectionArgs, groupBy, having, orderBy).
////        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "email", "senha", "pSeguranca", "rSeguranca"}, null, null, null, null, null, null);
//        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "email", "senha"}, null, null, null, null, null, null);
//        // fazer o cursor andar
//        while(cursor.moveToNext()) {
//            Aluno a = new Aluno();
//            // indice 0 para o id de acordo com os parametros da query e tbm o seu tipo (int).
//            a.setId(cursor.getInt(0));
//            a.setNome(cursor.getString(1));
//            a.setEmail(cursor.getString(2));
//            a.setSenha(cursor.getString(3));
////            a.setpSeguranca(cursor.getString(4));
////            a.setrSeguranca(cursor.getString(5));
//
//            // adiciona na lista
//            alunos.add(a);
//        }
//        return alunos;
//    }
//
//    // método para excluir registro
//    public void excluir(Aluno a) {
//        // informa a tabela, a clausala where e depois um vetor de string []
//        banco.delete("aluno", "id = ?", new String[]{a.getId().toString()});
//    }
//
//    // método atualizar
//    public void atualizar(Aluno aluno) {
//        ContentValues values = new ContentValues();
//        values.put("nome", aluno.getNome());
//        values.put("email", aluno.getEmail());
//        values.put("senha", aluno.getSenha());
//
//        // id = ? significa um id algum valor.
//        banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});
//    }
}
