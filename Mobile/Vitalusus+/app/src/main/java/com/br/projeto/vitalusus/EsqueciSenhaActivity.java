package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.br.projeto.vitalusus.dao.UsuarioDAO;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.util.MensagemUtil;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private View containerComponents;
    private TextView txtPerguntaSeguranca;
    private EditText editRespostaSeguranca, editEsqueciSenhaEmail, editESNovaSenha;
    private AppCompatButton btnVerificarResposta, btnVerificarEmail, btnRedefinirSenha;
    private Button btnESOlharSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        containerComponents = findViewById(R.id.containerComponents);
        editEsqueciSenhaEmail = findViewById(R.id.editEsqueciSenhaEmail);
        btnVerificarEmail = findViewById(R.id.btnVerificarEmail);
        txtPerguntaSeguranca = findViewById(R.id.txtESpSeguranca);
        editRespostaSeguranca = findViewById(R.id.editESrSeguranca);
        btnVerificarResposta = findViewById(R.id.btnVerificarResposta);
        editESNovaSenha = findViewById(R.id.editESNovaSenha);
        btnESOlharSenha = findViewById(R.id.btnESOlharSenha);
        btnRedefinirSenha = findViewById(R.id.btnRedefinirSenha);

        txtPerguntaSeguranca.setVisibility(View.GONE);
        editRespostaSeguranca.setVisibility(View.GONE);
        btnVerificarResposta.setVisibility(View.GONE);
        editESNovaSenha.setVisibility(View.GONE);
        btnRedefinirSenha.setVisibility(View.GONE);
        btnESOlharSenha.setVisibility(View.GONE);

        // Borda vermelha (caso ocorra alguma validação errada no formulário)
        GradientDrawable redBorder = new GradientDrawable();
        redBorder.setColor(Color.WHITE);
        redBorder.setCornerRadius(50);
        redBorder.setStroke(5, Color.RED);

        // Pegando os paramêtros do layout do containerComponents (div branca) pra poder manipular
        ViewGroup.LayoutParams layoutParams = containerComponents.getLayoutParams();
        // trocando a containerComponents para o tamanho do passo 1 verificar email
        layoutParams.height = 450;

//        btnVerificarEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = editEsqueciSenhaEmail.getText().toString();
//
////                Usuario usu = new UsuarioDAO().consultaEmailUsuario(email);
//                if (usu != null) {
//                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "E-Mail Encontrado! Responda a Pergunta de Segurança.");
////                    txtPerguntaSeguranca.setText(usu.getpSeguranca());
//
//                    // mudando para o tamanho do passo 2 verificar pergunta secreta
//                    layoutParams.height = 570;
//
//                    editEsqueciSenhaEmail.setVisibility(View.GONE);
//                    btnVerificarEmail.setVisibility(View.GONE);
//
//                    txtPerguntaSeguranca.setVisibility(View.VISIBLE);
//                    editRespostaSeguranca.setVisibility(View.VISIBLE);
//                    btnVerificarResposta.setVisibility(View.VISIBLE);
//
//                    editRespostaSeguranca.setText("");
//                    editRespostaSeguranca.requestFocus();
//                } else {
//                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Usuario não identificado. Coloque um E-Mail Cadastrado.");
//
//                    editEsqueciSenhaEmail.setBackground(redBorder);
//                    editEsqueciSenhaEmail.setText("");
//                    editEsqueciSenhaEmail.requestFocus();
//                }
//            }
//        });

//        btnVerificarResposta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = editEsqueciSenhaEmail.getText().toString();
//                String rSeguranca = editRespostaSeguranca.getText().toString();
//
//                Usuario usu = new UsuarioDAO().validarRespostaPSeguranca(email, rSeguranca);
//                if (usu != null) {
//                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Resposta Correta!");
//
//                    // passo 3
//                    layoutParams.height = 450;
//
//                    txtPerguntaSeguranca.setVisibility(View.GONE);
//                    editRespostaSeguranca.setVisibility(View.GONE);
//                    btnVerificarResposta.setVisibility(View.GONE);
//
//                    editESNovaSenha.setVisibility(View.VISIBLE);
//                    btnESOlharSenha.setVisibility(View.VISIBLE);
//                    btnRedefinirSenha.setVisibility(View.VISIBLE);
//
//                    editESNovaSenha.requestFocus();
//                } else {
//                    editRespostaSeguranca.setBackground(redBorder);
//                    editRespostaSeguranca.setText("");
//
//                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Resposta Errada!");
//
//                    editRespostaSeguranca.requestFocus();
//                }
//            }
//        });

        btnESOlharSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tipoAtual = editESNovaSenha.getInputType();

                // Verifique se é um campo de senha (& ou | significa AND)
                boolean ehSenha = (tipoAtual & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD;

                // Alterne para o próximo tipo de entrada
                if (ehSenha) {
                    editESNovaSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editESNovaSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEsqueciSenhaEmail.getText().toString();
                String novaSenha = editESNovaSenha.getText().toString();

                if (novaSenha.trim().isEmpty()) {
                    editESNovaSenha.setBackground(redBorder);

                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Digite uma senha nova.");

                    editESNovaSenha.requestFocus();
                } else if (novaSenha.trim().length() < 8) {
                    editESNovaSenha.setBackground(redBorder);

                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Digite uma Senha nova que tenha pelo menos 8 caracteres.");

                    editESNovaSenha.requestFocus();
                } else if (novaSenha.trim().length() >= 8) {
//                    Usuario usu = new UsuarioDAO().redefinirSenha(email, novaSenha);

                    Intent formlogin = new Intent(EsqueciSenhaActivity.this, FormLogin.class);
                    startActivity(formlogin);

                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Senha Redefinida com Sucesso!");
                }
            }
        });
    }
}