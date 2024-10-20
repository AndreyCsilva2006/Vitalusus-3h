package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.util.MensagemUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormCadastro extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editDataNasc;
    private RadioGroup rdggroupSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        // Inicialização das views
        editNome = findViewById(R.id.editFormCadastroLoginNome);
        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
        editDataNasc = findViewById(R.id.editFormCadastroDataNascimento);
        rdggroupSexo = findViewById(R.id.rgSexo);

        // Configurando o botão de salvar
        Button btnSalvar = findViewById(R.id.btnCadastroSalvar);
        btnSalvar.setOnClickListener(this::salvar);
    }

    public void salvar(View view) {
        if (!validar()) {
            Log.e("FormCadastro", "Validação falhou.");
            return;
        }

        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String dataNasc = editDataNasc.getText().toString().trim();

        // Capturando o sexo do Aluno como String ("M" ou "F")
        String sexo;
        if (rdggroupSexo.getCheckedRadioButtonId() == R.id.rbMasculino) {
            sexo = "M"; // Masculino
        } else if (rdggroupSexo.getCheckedRadioButtonId() == R.id.rbFeminino) {
            sexo = "F"; // Feminino
        } else {
            sexo = null; // Não selecionado ou indefinido
        }

        if (sexo == null) {
            Log.e("FormCadastro", "Sexo não selecionado.");
            MensagemUtil.exibir(this, "Por favor, selecione o sexo.");
            return;
        }

        int idade = calcularIdade(dataNasc);
        if (idade < 18) {
            Log.e("FormCadastro", "Idade menor que 18 anos: " + idade);
            MensagemUtil.exibir(this, "É necessário ter 18 anos ou mais para se cadastrar.");
            return;
        }

        // Definindo valores padrão
        String nivelAcesso = "USER"; // Pode ser ADMIN ou USER
        String foto = null; // Foto não disponível no cadastro
        String statusUsuario = "ATIVO"; // Status inicial do usuário
        String tipoUsuario = "ALUNO"; // Tipo de usuário
        String nivelPrivacidade = "PUBLICO"; // Nível de privacidade
        Date dataCadastro = new Date(); // Data atual

        java.sql.Date dataNascDate = null; // Usando java.sql.Date
        try {
            // Convertendo a data de nascimento para java.sql.Date
            dataNascDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dataNasc).getTime());
            Log.d("FormCadastro", "Data de nascimento convertida com sucesso: " + dataNascDate);
        } catch (ParseException e) {
            Log.e("FormCadastro", "Erro ao converter a data de nascimento: " + dataNasc, e);
            MensagemUtil.exibir(this, "Formato de data inválido. Use o formato YYYY-MM-DD.");
            return; // Sai do método se a data estiver em formato inválido
        }

        // Criando objeto Usuario com todos os campos necessários
        Usuario usuario = new Usuario(nome, email, senha, nivelAcesso, foto, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dataCadastro),
                statusUsuario, tipoUsuario, nivelPrivacidade, idade, dataNascDate);

        // Instanciando o AlunoDAO
        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = new Aluno();
        aluno.setSexo(sexo); // Setando o sexo como string ("M" ou "F")

        // Chama o método para cadastrar o aluno
        try {
            alunoDAO.cadastrarAluno(aluno, usuario);
            MensagemUtil.exibir(this, "Cadastro realizado com sucesso.");
            startActivity(new Intent(FormCadastro.this, FormLogin.class));
            finish();
        } catch (Exception e) {
            Log.e("FormCadastro", "Erro ao cadastrar aluno.", e);
            MensagemUtil.exibir(this, "Falha no cadastro do aluno. Verifique as informações.");
        }
    }

    // Método validar
    private boolean validar() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String dataNasc = editDataNasc.getText().toString().trim();

        if (nome.isEmpty()) {
            Log.e("FormCadastro", "Nome não preenchido.");
            MensagemUtil.exibir(this, "O nome é obrigatório.");
            return false;
        }

        if (email.isEmpty()) {
            Log.e("FormCadastro", "Email não preenchido.");
            MensagemUtil.exibir(this, "O email é obrigatório.");
            return false;
        }

        if (senha.isEmpty()) {
            Log.e("FormCadastro", "Senha não preenchida.");
            MensagemUtil.exibir(this, "A senha é obrigatória.");
            return false;
        }

        if (dataNasc.isEmpty()) {
            Log.e("FormCadastro", "Data de nascimento não preenchida.");
            MensagemUtil.exibir(this, "A data de nascimento é obrigatória.");
            return false;
        }

        return true;
    }

    // Método calcularIdade
    private int calcularIdade(String dataNasc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dataNascimento = sdf.parse(dataNasc);
            Calendar dataNascCal = Calendar.getInstance();
            dataNascCal.setTime(dataNascimento);

            Calendar hoje = Calendar.getInstance();
            int idade = hoje.get(Calendar.YEAR) - dataNascCal.get(Calendar.YEAR);

            // Verifica se o aniversário já aconteceu este ano; se não, subtrai 1 da idade
            if (hoje.get(Calendar.DAY_OF_YEAR) < dataNascCal.get(Calendar.DAY_OF_YEAR)) {
                idade--;
            }

            Log.d("FormCadastro", "Idade calculada: " + idade);
            return idade;
        } catch (ParseException e) {
            Log.e("FormCadastro", "Erro ao calcular idade", e);
            MensagemUtil.exibir(this, "Formato de data inválido. Use o formato YYYY-MM-DD.");
            return 0; // Retorna 0 caso haja erro no formato da data
        }
    }
}
