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

public class FormCadastro extends AppCompatActivity {

    private TextView text_tela_principal;
    private EditText nome, email, senha, pSeguranca, rSeguranca;
    private AlunoDAO dao;
    private AppCompatButton btnPrincipal;
    // Aluno do update
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

//        db = new DBHelper(this);

        btnPrincipal = (AppCompatButton)findViewById(R.id.btnPrincipal);

        nome = findViewById(R.id.edit_nome);
        email = findViewById(R.id.edit_email);
        senha = findViewById(R.id.edit_senha);
        pSeguranca = findViewById(R.id.edit_pSeguranca);
        rSeguranca = findViewById(R.id.edit_rSeguranca);
        // instanciando a variavél dao
        // dao = new AlunoDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("aluno")) {
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome());
            email.setText(aluno.getEmail());
            senha.setText(aluno.getSenha());
            pSeguranca.setText(aluno.getpSeguranca());
            rSeguranca.setText(aluno.getrSeguranca());
        }

        IniciarComponentes();
        text_tela_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastro.this,TelaPrincipal.class);
                startActivity(intent);
            }
        });

    }

    public void salvar (View view){
        // Aluno novo
        if (aluno == null) {
            if (nome.equals("")) {
                Toast.makeText(FormCadastro.this, "Nome não foi inserido! Tente novamente.", Toast.LENGTH_SHORT).show();
            }
            else if (email.equals("")) {
                Toast.makeText(FormCadastro.this, "E_mail não foi inserido! Tente novamente.", Toast.LENGTH_SHORT).show();

            }
            else if (senha.equals("")) {
                Toast.makeText(FormCadastro.this, "Senha não foi inserida! Tente novamente.", Toast.LENGTH_SHORT).show();

            }
            else {
                aluno = new Aluno();
                aluno.setNome(nome.getText().toString());
                aluno.setEmail(email.getText().toString());
                aluno.setSenha(senha.getText().toString());
                aluno.setpSeguranca(pSeguranca.getText().toString());
                aluno.setrSeguranca(rSeguranca.getText().toString());
                //long id = dao.inserir(aluno);
                //Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
                // tudo ok
//                    long res = db.CriarUsuario(nome, email, senha);
//                    if ( res > 0 ) {
//                        Toast.makeText(FormCadastro.this, "Registro OK.", Toast.LENGTH_SHORT).show();
//
//                    }
//                    else {
//                        Toast.makeText(FormCadastro.this, "Registro inválido.", Toast.LENGTH_SHORT).show();
//                    }
            }

        }
        // Aluno já existente (Update/Atualizar)
        else{
            aluno.setNome(nome.getText().toString());
            aluno.setEmail(email.getText().toString());
            aluno.setSenha(senha.getText().toString());
            aluno.setpSeguranca(pSeguranca.getText().toString());
            aluno.setrSeguranca(rSeguranca.getText().toString());
            //dao.atualizar(aluno);
            Toast.makeText(this, "Aluno foi atualizado!", Toast.LENGTH_SHORT).show();
        }
    }

    private void IniciarComponentes(){
        text_tela_principal = findViewById(R.id.text_tela_principal);
    }
}