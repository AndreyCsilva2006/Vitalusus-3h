package com.br.projeto.vitalusus;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.br.projeto.vitalusus.Aluno;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static String dbTable = "aluno";
    private static final int version = 1;

    private static String id = "id";
    private static String nome = "nome";
    private static String email = "email";
    private static String senha = "senha";
    private static String pSeguranca = "pSeguranca";
    private static String rSeguranca = "rSeguranca";

    public Conexao(Context context){
        super(context, name, null, version);
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Comando SQL
        db.execSQL("create table aluno(id integer primary key autoincrement, " + "nome varchar(100), email varchar(100), senha varchar(100), pSeguranca varchar(100), rSeguranca varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addAluno(Aluno alunos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nome, alunos.getNome());
        values.put(email, alunos.getEmail());
        values.put(senha, alunos.getSenha());
        values.put(pSeguranca, alunos.getpSeguranca());
        values.put(rSeguranca, alunos.getrSeguranca());


        db.insert(dbTable, null, values);
    }
}
