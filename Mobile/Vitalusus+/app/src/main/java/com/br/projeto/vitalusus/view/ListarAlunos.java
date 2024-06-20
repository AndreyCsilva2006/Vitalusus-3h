package com.br.projeto.vitalusus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;

import java.util.List;

public class ListarAlunos extends AppCompatActivity {

    TextView txtConsultaAlunoNome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        txtConsultaAlunoNome = findViewById(R.id.txtConsultaAlunoNome);

        preenche();
    }

    private void preenche() {
        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.getAll();
        for (Aluno aluno : lista){
            txtConsultaAlunoNome.setText(aluno.getNome());
        }
    }
}