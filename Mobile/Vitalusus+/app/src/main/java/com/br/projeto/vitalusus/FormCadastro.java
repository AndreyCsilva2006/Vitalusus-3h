package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.dao.UsuarioDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.util.MensagemUtil;

import java.sql.Date;
import java.util.Calendar;

public class FormCadastro extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editDataNasc;
    private Button btnFormCadastroOlharSenha;
    private RadioGroup rdggroupSexo;
    private TextView pularCadastro, politica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        // Inicialização das views
        editNome = findViewById(R.id.editFormCadastroLoginNome);
        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
        btnFormCadastroOlharSenha = findViewById(R.id.btnFormCadastroOlharSenha);
        pularCadastro = findViewById(R.id.text_tela_principal);
        politica = findViewById(R.id.text_tela_politica);
        Button btnSalvar = findViewById(R.id.btnCadastroSalvar);
        btnSalvar.setOnClickListener(this::salvar);

        btnFormCadastroOlharSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tipoAtual = editSenha.getInputType();
                boolean ehSenha = (tipoAtual & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD;

                if (ehSenha) {
                    editSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // Movendo o cursor para o final do texto
                editSenha.setSelection(editSenha.getText().length());
            }
        });

        pularCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormCadastro.this, FormLogin.class);
                startActivity(i);
            }
        });

        politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormCadastro.this, PoliticaAcitivity.class);
                startActivity(i);
            }
        });
    }

    public void salvar(View view) {
        if (!validar()) {
            Log.e("FormCadastro", "Validação falhou.");
            return;
        }

        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        // Definindo valores padrão
        String nivelAcesso = "USER";
        String foto = "";
        String statusUsuario = "ATIVO";
        String tipoUsuario = "ALUNO";
        String nivelPrivacidade = "PUBLICO";
        Date dataCadastro = new Date(System.currentTimeMillis());

        // Criando objeto Usuario com todos os campos necessários
        Usuario usuario = new Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro.toString(),
                statusUsuario, tipoUsuario, nivelPrivacidade);

        // Instanciando o UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int usuarioId = usuarioDAO.cadastrarUsuario(usuario); // Insere o usuário e retorna o ID

        if (usuarioId != -1) { // Verifica se o usuário foi inserido com sucesso
            // Instanciando o AlunoDAO e criando objeto Aluno
            AlunoDAO alunoDAO = new AlunoDAO();
            Aluno aluno = new Aluno();

            // Chama o método para cadastrar o aluno, passando o ID do usuário
            try {
                alunoDAO.cadastrarAluno(usuarioId, aluno); // Cadastra o aluno com o ID do usuário
                MensagemUtil.exibir(this, "Cadastro realizado com sucesso.");
                startActivity(new Intent(FormCadastro.this, FormLogin.class));
                finish();
            } catch (Exception e) {
                Log.e("FormCadastro", "Erro ao cadastrar aluno.", e);
                MensagemUtil.exibir(this, "Falha no cadastro do aluno. Verifique as informações.");
            }
        } else {
            Log.e("FormCadastro", "Erro ao cadastrar o usuário.");
            MensagemUtil.exibir(this, "Falha no cadastro do usuário.");
        }
    }

    private boolean validar() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            Log.e("FormCadastro", "Nome não preenchido.");
            MensagemUtil.exibir(this, "O nome é obrigatório.");
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Log.e("FormCadastro", "Email não preenchido.");
            MensagemUtil.exibir(this, "O email é obrigatório.");
            return false;
        }

        if (TextUtils.isEmpty(senha)) {
            Log.e("FormCadastro", "Senha não preenchida.");
            MensagemUtil.exibir(this, "A senha é obrigatória.");
            return false;
        }
        return true;
    }
}