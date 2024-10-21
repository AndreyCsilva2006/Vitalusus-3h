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

import com.br.projeto.vitalusus.dao.UsuarioDAO;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.response.AlunoResponse;
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

        // Botão para mostrar/ocultar a senha
        btnFormLoginOlharSenha.setOnClickListener(new View.OnClickListener() {
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

        // Clique para ir para a tela de cadastro
        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        // Clique para ir para a tela de recuperação de senha
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

        Usuario usu = new UsuarioDAO().selecionarUsuario(email, senha);
        if (usu != null){
            Intent intent = new Intent(FormLogin.this, HomeActivity.class);
            intent.putExtra("usuarioNome", usu.getNome());
            intent.putExtra("usuarioEmail", usu.getEmail());
            startActivity(intent);

            finish();
        } else {
            limpar();
        }
    }

    private void limpar() {
        GradientDrawable redBorder = new GradientDrawable();
        redBorder.setColor(Color.WHITE);
        redBorder.setCornerRadius(50);
        redBorder.setStroke(5, Color.RED);

        editEmail.setBackground(redBorder);
        editSenha.setBackground(redBorder);
        editEmail.setText("");
        editSenha.setText("");
        editEmail.requestFocus();
    }
}