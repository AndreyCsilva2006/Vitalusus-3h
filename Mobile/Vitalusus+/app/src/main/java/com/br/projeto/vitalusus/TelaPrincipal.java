package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.view.ListarAlunos;

import org.parceler.Parcels;

public class TelaPrincipal extends AppCompatActivity {

    TextView txtPerfilNome, txtPerfilEmail;
    Button btnPerfilDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        String nome = getIntent().getStringExtra("nome");
        String email = getIntent().getStringExtra("email");

        txtPerfilNome = findViewById(R.id.txtPerfilNomeAluno);
        txtPerfilEmail = findViewById(R.id.txtPerfilEmailAluno);
        btnPerfilDeslogar = findViewById(R.id.btnPerfilDeslogar);

        txtPerfilNome.setText(nome);
        txtPerfilEmail.setText(email);

        btnPerfilDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}