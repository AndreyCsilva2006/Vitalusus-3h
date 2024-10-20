package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.br.projeto.vitalusus.util.MensagemUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar(v);
            }
        });
    }

    public void salvar(View view) {
        if (!validar()) {
            return;
        }

        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String dataNasc = editDataNasc.getText().toString().trim();

        // Capturando o sexo do Aluno
        int sexo;
        if (rdggroupSexo.getCheckedRadioButtonId() == R.id.rbMasculino) {
            sexo = 0; // Masculino
        } else if (rdggroupSexo.getCheckedRadioButtonId() == R.id.rbFeminino) {
            sexo = 1; // Feminino
        } else {
            sexo = -1; // Indefinido ou não selecionado
        }

        if (calcularIdade(dataNasc) < 18) {
            MensagemUtil.exibir(this, "É necessário ter 18 anos ou mais para se cadastrar.");
            return;
        }

        // Instanciando o Retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Definindo valores padrão
        String nivelAcesso = "USER"; // Pode ser ADMIN ou USER
        String foto = null; // Foto não disponível no cadastro
        String statusUsuario = "ATIVO"; // Status inicial do usuário
        String tipoUsuario = "ALUNO"; // Tipo de usuário
        String nivelPrivacidade = "PUBLICO"; // Nível de privacidade
        int idade = calcularIdade(dataNasc); // Calculando a idade com base na data de nascimento
        Date dataCadastro = new Date(); // Data atual

        Date dataNascDate = null;
        try {
            // Convertendo a data de nascimento para Date
            dataNascDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dataNasc);
        } catch (ParseException e) {
            MensagemUtil.exibir(this, "Formato de data inválido. Use o formato YYYY-MM-DD.");
            return; // Sai do método se a data estiver em formato inválido
        }

        // Criando objeto Usuario com todos os campos necessários
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dataCadastroString = sdf.format(dataCadastro);

        Usuario usuario = new Usuario(nome, email, senha, nivelAcesso, foto, dataCadastroString, statusUsuario, tipoUsuario, nivelPrivacidade, idade, dataNascDate);

        // Chame a API para criar o usuário
        Call<Usuario> callUsuario = apiService.createUsuario(usuario);
        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int usuarioId = response.body().getId();

                    // Criando objeto Aluno
                    Aluno aluno = new Aluno();
                    aluno.setSexo(sexo);
                    aluno.setUsuario_id(usuarioId);
                    aluno.setAltura(0); // Defina a altura
                    aluno.setPeso(0);   // Defina o peso

                    // Chame a API para criar o aluno
                    Call<Aluno> callAluno = apiService.createAluno(aluno);
                    callAluno.enqueue(new Callback<Aluno>() {
                        @Override
                        public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                            if (response.isSuccessful()) {
                                MensagemUtil.exibir(FormCadastro.this, "Cadastro realizado com sucesso.");
                                startActivity(new Intent(FormCadastro.this, FormLogin.class));
                                finish();
                            } else {
                                Log.e("CadastroAluno", "Erro: " + response.code() + " - " + response.message());
                                MensagemUtil.exibir(FormCadastro.this, "Falha no cadastro do aluno. Verifique as informações.");
                            }
                        }

                        @Override
                        public void onFailure(Call<Aluno> call, Throwable t) {
                            Log.e("Erro", t.getMessage());
                            MensagemUtil.exibir(FormCadastro.this, "Erro na comunicação com o servidor.");
                        }
                    });
                } else {
                    Log.e("CadastroUsuario", "Erro: " + response.code() + " - " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("CadastroUsuario", "Erro detalhado: " + errorResponse);
                        } catch (IOException e) {
                            Log.e("CadastroUsuario", "Erro ao ler o corpo da resposta de erro", e);
                        }
                    }
                    MensagemUtil.exibir(FormCadastro.this, "Falha no cadastro do usuário. Verifique as informações.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Erro", t.getMessage());
                MensagemUtil.exibir(FormCadastro.this, "Erro na comunicação com o servidor.");
            }
        });
    }

    private boolean validar() {
        // Implementar validações necessárias para os campos
        return true; // Retornar true se a validação for bem-sucedida
    }

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

            return idade;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Retorna 0 caso haja erro no formato da data
        }
    }
}
