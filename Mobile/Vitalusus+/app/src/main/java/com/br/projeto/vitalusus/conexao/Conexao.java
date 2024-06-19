package com.br.projeto.vitalusus.conexao;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() throws  ClassNotFoundException, SQLException {
        Connection conn = null;

        StrictMode.ThreadPolicy politica;
        politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politica);

        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        String ip = "192.168.1.110:1433";
        String db = "db_teste";
        String user = "sa";
        String senha = "admin123";

        String connString = "jdbc:jtds:sqlserver://"+ip+";databaseName="+db+";user="+user+";password="+senha+";";
        conn = DriverManager.getConnection(connString);

        return conn;
    }





    // precisa do extends SQLiteHelper para funcionar o código abaixo
//    private static final String name = "banco.db";
//    private static String dbTable = "aluno";
//    private static final int version = 1;
//
//    private static String id = "id";
//    private static String nome = "nome";
//    private static String email = "email";
//    private static String senha = "senha";
//    private static String pSeguranca = "pSeguranca";
//    private static String rSeguranca = "rSeguranca";
//
//    public Conexao(Context context){
//        super(context, name, null, version);
//}
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // Comando SQL
//        db.execSQL("create table aluno(id integer primary key autoincrement, " + "nome varchar(100), email varchar(100), senha varchar(100), pSeguranca varchar(100), rSeguranca varchar(100))");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    public void addAluno(Aluno alunos){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(nome, alunos.getNome());
//        values.put(email, alunos.getEmail());
//        values.put(senha, alunos.getSenha());
//        values.put(pSeguranca, alunos.getpSeguranca());
//        values.put(rSeguranca, alunos.getrSeguranca());
//
//
//        db.insert(dbTable, null, values);
//    }
}
