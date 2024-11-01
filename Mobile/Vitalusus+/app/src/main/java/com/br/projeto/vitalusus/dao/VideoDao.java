package com.br.projeto.vitalusus.dao;

import com.br.projeto.vitalusus.conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import retrofit2.http.Query;

public class VideoDao {

    // Método para incrementar visualizações
    public void incrementaVisu(int videoId) {
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE Videoaula SET visualizacoes = visualizacoes + 1 WHERE id = ?"; // Ajuste o nome da coluna
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, videoId);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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

