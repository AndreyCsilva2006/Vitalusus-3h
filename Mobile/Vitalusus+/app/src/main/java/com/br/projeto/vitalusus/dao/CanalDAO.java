package com.br.projeto.vitalusus.dao;

import android.util.Log;

import com.br.projeto.vitalusus.conexao.Conexao;
import com.br.projeto.vitalusus.model.Canal;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CanalDAO {
    private Connection conn = null;

    private List<Canal> getCanal(String sql) throws SQLException {
        List<Canal> lista = new ArrayList<Canal>();

        Statement st = null;
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Canal canal = new Canal();

            canal.setId(rs.getInt(1));
            canal.setNome(rs.getString(2));
            canal.setVisualizacoes(rs.getInt(3));
            canal.setSeguidores(BigInteger.valueOf(rs.getInt(4)));

            lista.add(canal);
        }
        return lista;
    }

    // getAll
    public List<Canal> getAll() {
        List<Canal> lista = new ArrayList<Canal>();
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "select * from Canal";
                lista = getCanal(sql);

                conn.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Erro no CanalDAO.java no método getAll sem pesquisa", e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Erro no CanalDAO.java no método getAll sem pesquisa", throwables.getMessage());
        }
        return lista;
    }

    // getAll com pesquisa
    public List<Canal> getAll(String busca) {
        List<Canal> lista = new ArrayList<Canal>();
        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String sql = "select * from Canal where nome like '%" + busca + "%'";
                lista = getCanal(sql);

                conn.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Erro no CanalDAO.java no método getAll(String busca) (com pesquisa)", e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Erro no CanalDAO.java no método getAll(String busca) (com pesquisa)", throwables.getMessage());
        }
        return lista;
    }
}
