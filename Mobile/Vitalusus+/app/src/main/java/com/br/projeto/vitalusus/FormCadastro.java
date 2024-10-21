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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        // Inicialização das views
        editNome = findViewById(R.id.editFormCadastroLoginNome);
        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
//        editDataNasc = findViewById(R.id.editFormCadastroDataNascimento);
        btnFormCadastroOlharSenha = findViewById(R.id.btnFormCadastroOlharSenha);
//        rdggroupSexo = findViewById(R.id.rgSexo);
        // Configurando o botão de salvar
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
    }

    public void salvar(View view) {
        if (!validar()) {
            Log.e("FormCadastro", "Validação falhou.");
            return;
        }

        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        // Capturando o sexo do Aluno como String ("M" ou "F")
//        String sexo = obterSexoSelecionado();

//        if (sexo == null) {
//            Log.e("FormCadastro", "Sexo não selecionado.");
//            MensagemUtil.exibir(this, "Por favor, selecione o sexo.");
//            return;
//        }

        // Capturando a data de nascimento diretamente como Date
//        java.sql.Date dataNasc = obterDataNasc();
//        if (dataNasc == null) {
//            Log.e("FormCadastro", "Data de nascimento inválida.");
//            return;
//        }

//        int idade = calcularIdade(dataNasc);
//        if (idade < 18) {
//            Log.e("FormCadastro", "Idade menor que 18 anos: " + idade);
//            MensagemUtil.exibir(this, "É necessário ter 18 anos ou mais para se cadastrar.");
//            return;
//        }

        // Definindo valores padrão
        String nivelAcesso = "USER"; // Pode ser ADMIN ou USER
        String foto = ""; // Foto não disponível no cadastro
        String statusUsuario = "ATIVO"; // Status inicial do usuário
        String tipoUsuario = "ALUNO"; // Tipo de usuário
        String nivelPrivacidade = "PUBLICO"; // Nível de privacidade
        Date dataCadastro = new Date(System.currentTimeMillis()); // Data atual

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
//            aluno.setSexo(sexo); // Setando o sexo como string ("M" ou "F")

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

//    private String obterSexoSelecionado() {
//        int id = rdggroupSexo.getCheckedRadioButtonId();
//        if (id == R.id.rbMasculino) {
//            return "Masculino"; // Masculino
//        } else if (id == R.id.rbFeminino) {
//            return "Feminino"; // Feminino
//        }
//        return null; // Não selecionado ou indefinido
//    }

    // Método validar
    private boolean validar() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
//        String dataNasc = editDataNasc.getText().toString().trim();

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

//        if (TextUtils.isEmpty(dataNasc)) {
//            Log.e("FormCadastro", "Data de nascimento não preenchida.");
//            MensagemUtil.exibir(this, "A data de nascimento é obrigatória.");
//            return false;
//        }

        return true;
    }

    // Método calcularIdade
    private int calcularIdade(java.sql.Date dataNasc) {
        Calendar dataNascCal = Calendar.getInstance();
        dataNascCal.setTime(dataNasc);

        Calendar hoje = Calendar.getInstance();
        int idade = hoje.get(Calendar.YEAR) - dataNascCal.get(Calendar.YEAR);

        // Verifica se o aniversário já aconteceu este ano; se não, subtrai 1 da idade
        if (hoje.get(Calendar.DAY_OF_YEAR) < dataNascCal.get(Calendar.DAY_OF_YEAR)) {
            idade--;
        }

        Log.d("FormCadastro", "Idade calculada: " + idade);
        return idade;
    }

    // Método para obter a data de nascimento
//    private java.sql.Date obterDataNasc() {
//        String dataNascTexto = editDataNasc.getText().toString().trim();
//        try {
//            return java.sql.Date.valueOf(dataNascTexto); // Usa java.sql.Date diretamente
//        } catch (IllegalArgumentException e) {
//            Log.e("FormCadastro", "Erro ao converter a data de nascimento: " + dataNascTexto, e);
//            MensagemUtil.exibir(this, "Formato de data inválido. Use o formato YYYY-MM-DD.");
//            return null; // Retorna null em caso de erro
//        }
//    }
}