package com.br.projeto.vitalusus.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.projeto.vitalusus.FormLogin;
import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.TelaPrincipal;
import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.util.MensagemUtil;

public class ActivityAluno extends AppCompatActivity {

    EditText editNome, editEmail, editSenha;
    TextView txtStatus;
    Button btnSalvar, btnExcluir;

    Aluno alunoEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        carregaFormulario();
        carregaBundle();
    }

    private void carregaFormulario() {
        editNome = findViewById(R.id.editActivityAlunoNome);
        editEmail = findViewById(R.id.editActivityAlunoEmail);
        editSenha = findViewById(R.id.editActivityAlunoSenha);

        btnSalvar = findViewById(R.id.btnActivityAlunoSalvar);
        btnExcluir = findViewById(R.id.btnActivityAlunoExcluir);

        txtStatus = findViewById(R.id.txtActivityAlunoStatus);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir();
            }
        });
    }

    // carrega informações do Aluno
    private void carregaBundle() {
        AlunoDAO dao = new AlunoDAO();
        Bundle b = getIntent().getExtras();
        // caso novo cadastro, não aparecer o botão excluir.
        btnExcluir.setVisibility(View.INVISIBLE);
        // verificação para editar Aluno
        if (b != null) {
            if (b.get("aluno") != null) {
                Integer idA = (Integer) b.get("aluno");
                alunoEditando = dao.findById(idA);
                if (alunoEditando != null) {
                    // mostra as informações do aluno que deseja editar
                    mostra(alunoEditando);
                    txtStatus.setText("Alterando Aluno");
                    btnExcluir.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    private void mostra(Aluno a) {
        editNome.setText(a.getNome());
        editEmail.setText(a.getEmail());
        editSenha.setText(a.getSenha());

    }

    // validações cadastro
    private Boolean validar() {

        if (editNome.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite um Nome");
            return false;
        } else if (editEmail.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite um E-Mail");
            return false;
        } else if (editSenha.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite uma Senha");
            return false;
        }
        // caso o nome tiver menos que 3 caracteres, não será aceito.
        else if (editNome.getText().toString().trim().length() < 3) {
            MensagemUtil.exibir(this, "Digite um Nome Válido, com mais de 3 caracteres");
            return false;
        } else if (editEmail.getText().toString().trim().length() < 9) {
            MensagemUtil.exibir(this, "Digite um E-Mail válido");
            return false;
        } else if (editSenha.getText().toString().trim().length() <= 6) {
            MensagemUtil.exibir(this, "Digite uma senha que tenha mais de 6 caracteres");
            return false;
        }


        return true;
    }

    private void salvar() {
        // se ele o método validar() não for verdadeiro...
        if (!validar()) {
            // retorne tudo novamente.
            return;
        }
        Aluno a = new Aluno();

        if (alunoEditando != null) {
            a = alunoEditando;
        }
        a.setNome(editNome.getText().toString());
        a.setEmail(editEmail.getText().toString());
        a.setSenha(editSenha.getText().toString());

        AlunoDAO dao = new AlunoDAO();
        if (alunoEditando != null) {
            dao.alterar(a);
        } else {
            dao.cadastrar(a);
        }

        Intent intent = new Intent(ActivityAluno.this, ListarAlunos.class);
        startActivity(intent);
    }

    private void excluir() {
        if (alunoEditando != null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Remover");
            alert.setMessage("Deseja Realmente remover esse Aluno?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlunoDAO dao = new AlunoDAO();
                    // como o aluno editando é o selecionado, devemos excluir o que está sendo editado, pois ele foi selecionado na lista
                    dao.excluir(alunoEditando);
                    dialogInterface.dismiss();

                    Intent intent = new Intent(ActivityAluno.this, ListarAlunos.class);
                    startActivity(intent);
                }
            });
            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alert.show();
        }
    }
}