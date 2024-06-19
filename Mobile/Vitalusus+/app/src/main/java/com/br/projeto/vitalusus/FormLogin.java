package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;

import java.time.Instant;

public class FormLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private EditText edit_email, edit_senha;
    private AppCompatButton btnLogin, btnEsqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        btnLogin = findViewById(R.id.btnLogin);

        IniciarComponentes();
        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this,FormCadastro.class);
                startActivity(intent);
            }
        });
//        Não funcionando por algum motivo estranho :/
//        btnEsqueciSenha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FormLogin.this,EsqueciSenhaActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void IniciarComponentes(){
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
    }

    public void login(View v){
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        // DAO - Data Access Object (Objeto de Acesso de Dados)
        Aluno alu = new AlunoDAO().selecionarAluno(email, senha);
        if(alu != null){
            Toast.makeText(this, "Login com sucesoo!.",Toast.LENGTH_LONG);
            Intent intent = new Intent(FormLogin.this, TelaPrincipal.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Aluno não identificado, tente novamente.",Toast.LENGTH_LONG);
            limpar();
        }
    }

    private void limpar(){
        edit_email.setText("");
        edit_senha.setText("");
        edit_email.requestFocus();
    }
}