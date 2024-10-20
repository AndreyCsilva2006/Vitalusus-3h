package com.br.projeto.vitalusus.conexao;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() throws ClassNotFoundException, SQLException {
        Connection conn = null;

        StrictMode.ThreadPolicy politica;
        politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politica);

        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        String ip = "192.168.0.11:1433";
        String db = "bd_vitalusus2h";
        String user = "sa";
        String senha = "adurite123";

        String connString = "jdbc:jtds:sqlserver://" + ip + ";databaseName=" + db + ";user=" + user + ";password=" + senha + ";";
        conn = DriverManager.getConnection(connString);

        return conn;
    }
}
