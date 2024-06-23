package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.view.ActivityAluno;
import com.br.projeto.vitalusus.view.ListarAlunos;

public class TelaPrincipal extends AppCompatActivity {

    TextView txtPerfilNome, txtPerfilEmail;
    Button btnPerfilDeslogar;

    Aluno alunoInfos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        txtPerfilNome = findViewById(R.id.txtPerfilNomeAluno);
        txtPerfilEmail = findViewById(R.id.txtPerfilEmailAluno);
        btnPerfilDeslogar = findViewById(R.id.btnPerfilDeslogar);

        btnPerfilDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, MainActivity.class);
                startActivity(intent);
            }
        });
        carregaBundle(getIntent().getExtras());
    }

    // carrega informações do Aluno
    private void carregaBundle(Bundle bundle) {
        AlunoDAO dao = new AlunoDAO();
        Bundle b = getIntent().getExtras();
        // verificação para editar Aluno
        if (b != null) {
            if (b.get("aluno") != null) {
                Integer idAluno = bundle.getInt("aluno");
                alunoInfos = dao.findById(idAluno);
                if (alunoInfos != null) {
                    // mostra as informações do aluno que deseja editar
                    txtPerfilNome.setText(alunoInfos.getNome());
                    txtPerfilEmail.setText(alunoInfos.getEmail());
                }
            }
        }
    }
}