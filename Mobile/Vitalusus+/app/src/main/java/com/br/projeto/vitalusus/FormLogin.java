package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.br.projeto.vitalusus.dao.UsuarioDAO;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.util.MensagemUtil;
import com.br.projeto.vitalusus.network.RetrofitClient;

public class FormLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private EditText editEmail, editSenha;
    private AppCompatButton btnLogin, btnEsqueciSenha;
    private Button btnFormLoginOlharSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
        btnLogin = findViewById(R.id.btnFormLoginEntrar);
        btnEsqueciSenha = findViewById(R.id.btnFormLoginEsqueciSenha);
        btnFormLoginOlharSenha = findViewById(R.id.btnFormLoginOlharSenha);
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);

        // botão do olho de olhar a senha
        btnFormLoginOlharSenha.setOnClickListener(new View.OnClickListener() {
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

        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        btnEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, EsqueciSenhaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(View v) {
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Usuario> call = apiService.loginUser(email, senha);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    MensagemUtil.exibir(FormLogin.this, "Login com Sucesso!");

                    // Navegar para a próxima tela, passando o nome e email do usuário
                    Intent intent = new Intent(FormLogin.this, HomeActivity.class);
                    intent.putExtra("nome", usuario.getNome());
                    intent.putExtra("email", usuario.getEmail());
                    startActivity(intent);
                } else {
                    MensagemUtil.exibir(FormLogin.this, "Usuário não identificado, tente novamente.");
                    limpar();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                MensagemUtil.exibir(FormLogin.this, "Erro ao tentar realizar login: " + t.getMessage());
            }
        });
    }

    private void limpar() {
        // Fazendo com que o campo fique com a borda vermelha caso o campo esteja inválido.
        GradientDrawable redBorder = new GradientDrawable();
        redBorder.setColor(Color.WHITE); // Cor do fundo.
        redBorder.setCornerRadius(50); // Raio do arredondamento das bordas (em pixels fica 60px que é equivalente a 20dp).
        redBorder.setStroke(5, Color.RED); // Cor e espessura da borda (em pixels fica 6px que é equivalente a 2dp).

        editEmail.setBackground(redBorder);
        editSenha.setBackground(redBorder);
        // limpa campos
        editEmail.setText("");
        editSenha.setText("");

        editEmail.requestFocus();
    }
}