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

//    public Aluno cadastrarAluno(String nome, String email, String senha){
//
//    }

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
//                    alu.setpSeguranca(rs.getString(5));
//                    alu.setrSeguranca(rs.getString(6));

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

    public List<Aluno> getAll(){
        List<Aluno> lista = new ArrayList<Aluno>();
        try {
            Connection conn = Conexao.conectar();
            if(conn != null){
                String sql = "select * from Aluno";
                Statement st = null;
                st = conn.createStatement();

                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    Aluno alu = new Aluno();
                    alu.setId(rs.getInt(1));
                    alu.setNome(rs.getString(2));
                    alu.setEmail(rs.getString(3));
                    alu.setSenha(rs.getString(4));
//                    alu.setpSeguranca(rs.getString(5));
//                    alu.setrSeguranca(rs.getString(6));

                    lista.add(alu);
                }
                conn.close();

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }

}
