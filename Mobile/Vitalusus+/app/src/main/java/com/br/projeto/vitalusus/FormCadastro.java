package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.util.MensagemUtil;

// código copiado e adaptador da ActivityAluno (com.br.projeto.vitalusus.view.ActivityAluno).
public class FormCadastro extends AppCompatActivity {

    EditText editNome, editEmail, editSenha, editPSeguranca, editRSeguranca;
    Button btnSalvar, btnFormCadastroOlharSenha;
    TextView text_tela_principal;

    Aluno alunoEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        btnFormCadastroOlharSenha = findViewById(R.id.btnFormCadastroOlharSenha);
        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
        text_tela_principal = findViewById(R.id.text_tela_principal);

        carregaFormulario();
        carregaBundle();

        text_tela_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastro.this, Home.class);
                startActivity(intent);
            }
        });

        btnFormCadastroOlharSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tipoAtual = editSenha.getInputType();

                // Verifique se é um campo de senha (& ou | significa AND)
                boolean ehSenha = (tipoAtual & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD;

                // Alterne para o próximo tipo de entrada
                if (ehSenha) {
                    editSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void carregaFormulario() {
        editNome = findViewById(R.id.editFormCadastroLoginNome);
        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
        editPSeguranca = findViewById(R.id.editFormCadastroPergSeguranca);
        editRSeguranca = findViewById(R.id.editFormCadastroRespSeguranca);

        btnSalvar = findViewById(R.id.btnCadastroAlunoSalvar);
        // btnExcluir = findViewById(R.id.btnActivityAlunoExcluir);

        // txtStatus = findViewById(R.id.txtActivityAlunoStatus);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    // carrega informações do Aluno
    private void carregaBundle() {
        AlunoDAO dao = new AlunoDAO();
        Bundle b = getIntent().getExtras();
        // verificação para editar Aluno
        if (b != null) {
            if (b.get("aluno") != null) {
                Integer idA = (Integer) b.get("aluno");
                alunoEditando = dao.findById(idA);
                if (alunoEditando != null) {
                    // mostra as informações do aluno que deseja editar
                    mostra(alunoEditando);
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

// Fazendo com que o campo fique com a borda vermelha caso o campo esteja inválido.
        GradientDrawable redBorder = new GradientDrawable();
        redBorder.setColor(Color.WHITE); // Cor do fundo.
        redBorder.setCornerRadius(50); // Raio do arredondamento das bordas (em pixels fica 60px que é equivalente a 20dp).
        redBorder.setStroke(5, Color.RED); // Cor e espessura da borda (em pixels fica 6px que é equivalente a 2dp).

        // Fazendo com que o campo fique com a borda normal caso campo esteja válido.
        GradientDrawable blackBorder = new GradientDrawable();
        blackBorder.setColor(Color.WHITE); // Cor do fundo.
        blackBorder.setCornerRadius(50); // Raio do arredondamento das bordas (em pixels fica 60px que é equivalente a 20dp).
        blackBorder.setStroke(5, Color.BLACK); // Cor e espessura da borda (em pixels fica 6px que é equivalente a 2dp).

        if (editNome.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite um Nome");

            // Aplicando o GradientDrawable ao EditText
            editNome.setBackground(redBorder);
            editEmail.setBackground(redBorder);
            editSenha.setBackground(redBorder);
            editPSeguranca.setBackground(redBorder);
            editRSeguranca.setBackground(redBorder);

            editNome.requestFocus();
            return false;
        }
        if (editNome.getText().toString().trim().length() < 3) {
            MensagemUtil.exibir(this, "Digite um Nome que tenha pelo menos 3 caracteres");
            editNome.requestFocus();
            return false;
        }

        editNome.setBackground(blackBorder);

        if (editEmail.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite um E-Mail");
            editEmail.requestFocus();
            return false;
        }
        // ! no início de uma expressão lógica é usado para negar o resultado dessa expressão. Ou seja, ele inverte o valor booleano.
        if (!editEmail.getText().toString().trim().contains("@")){
            MensagemUtil.exibir(this, "O Email precisa ter um @");
            editEmail.requestFocus();
            return false;
        }
        if (editEmail.getText().toString().trim().length() < 9) {
            MensagemUtil.exibir(this, "Digite um E-Mail válido");
            editEmail.requestFocus();
            return false;
        }
        if (editSenha.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite uma Senha");
            editSenha.requestFocus();
            return false;
        }
        if (editSenha.getText().toString().trim().length() < 6) {
            MensagemUtil.exibir(this, "Digite uma Senha que tenha pelo menos 6 caracteres");
            editSenha.requestFocus();
            return false;
        }
        if (editPSeguranca.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite uma Pergunta de Segurança caso perca sua Senha para poder recuperar.");
            editPSeguranca.requestFocus();
            return false;
        }
        if (editPSeguranca.getText().toString().trim().length() < 4) {
            MensagemUtil.exibir(this, "Digite uma Pergunta que pelo menos 4 caracteres");
            editPSeguranca.requestFocus();
            return false;
        }
        if (editRSeguranca.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite uma Resposta para a Pergunta de Segurança.");
            editRSeguranca.requestFocus();
            return false;
        }
        if (editRSeguranca.getText().toString().trim().length() < 4) {
            MensagemUtil.exibir(this, "Digite uma Resposta para a Pergunta de Segurança que tenha pelo menos 4 caracteres");
            editRSeguranca.requestFocus();
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
        a.setpSeguranca(editPSeguranca.getText().toString());
        a.setrSeguranca(editRSeguranca.getText().toString());

        AlunoDAO dao = new AlunoDAO();
        if (alunoEditando != null) {
            dao.alterar(a);
        } else {
            dao.cadastrar(a);
        }

        Intent intent = new Intent(FormCadastro.this, FormLogin.class);
        startActivity(intent);
    }
}