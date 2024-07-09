package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.br.projeto.vitalusus.dao.AlunoDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.util.MensagemUtil;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private View containerComponents;
    private TextView txtPerguntaSeguranca;
    private EditText editRespostaSeguranca, editEsqueciSenhaEmail;
    private AppCompatButton btnRedefinirSenha, btnVerificarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        editEsqueciSenhaEmail = findViewById(R.id.editEsqueciSenhaEmail);
        containerComponents = findViewById(R.id.containerComponents);
        txtPerguntaSeguranca = findViewById(R.id.txt_ESpSeguranca);
        editRespostaSeguranca = findViewById(R.id.edit_ESrSeguranca);
        btnRedefinirSenha = findViewById(R.id.btnRedefinirSenha);
        btnVerificarEmail = findViewById(R.id.btnVerificarEmail);

        txtPerguntaSeguranca.setVisibility(View.GONE);
        editRespostaSeguranca.setVisibility(View.GONE);
        btnRedefinirSenha.setVisibility(View.GONE);

        // Pegando os paramêtros do layout do containerComponents (div branca) pra poder manipular
        ViewGroup.LayoutParams layoutParams = containerComponents.getLayoutParams();
        // trocando a containerComponents para o tamanho do passo 1 verificar email
        layoutParams.height = 450;

        btnVerificarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEsqueciSenhaEmail.getText().toString();

                Aluno alu = new AlunoDAO().consultaEmailAluno(email);
                if (alu != null) {
                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "E-Mail Encontrado! Responda a Pergunta de Segurança.");
                    txtPerguntaSeguranca.setText(alu.getpSeguranca());

                    GradientDrawable blackBorder = new GradientDrawable();
                    blackBorder.setColor(Color.WHITE);
                    blackBorder.setCornerRadius(50);
                    blackBorder.setStroke(5, Color.BLACK);

                    editEsqueciSenhaEmail.setBackground(blackBorder);

                    // mudando para o tamanho do passo 2 verificar pergunta secreta
                    layoutParams.height = 570;
                    containerComponents.setLayoutParams(layoutParams);

                    editEsqueciSenhaEmail.setVisibility(View.GONE);
                    btnVerificarEmail.setVisibility(View.GONE);

                    txtPerguntaSeguranca.setVisibility(View.VISIBLE);
                    editRespostaSeguranca.setVisibility(View.VISIBLE);
                    btnRedefinirSenha.setVisibility(View.VISIBLE);

                    editRespostaSeguranca.setText("");
                    editRespostaSeguranca.requestFocus();
                } else {
                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Aluno não identificado. Coloque um E-Mail Cadastrado.");

                    GradientDrawable redBorder = new GradientDrawable();
                    redBorder.setColor(Color.WHITE);
                    redBorder.setCornerRadius(50);
                    redBorder.setStroke(5, Color.RED);

                    editEsqueciSenhaEmail.setBackground(redBorder);
                    editEsqueciSenhaEmail.setText("");
                    editEsqueciSenhaEmail.requestFocus();
                }
            }
        });

        btnRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEsqueciSenhaEmail.getText().toString();
                String rSeguranca = editRespostaSeguranca.getText().toString();

                Aluno alu = new AlunoDAO().validarRespostaPSeguranca(email, rSeguranca);
                if (alu != null) {
                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Resposta Correta!");

                    GradientDrawable blackBorder = new GradientDrawable();
                    blackBorder.setColor(Color.WHITE);
                    blackBorder.setCornerRadius(50);
                    blackBorder.setStroke(5, Color.BLACK);

                    editRespostaSeguranca.setBackground(blackBorder);
                } else {
                    GradientDrawable redBorder = new GradientDrawable();
                    redBorder.setColor(Color.WHITE);
                    redBorder.setCornerRadius(50);
                    redBorder.setStroke(5, Color.RED);

                    editRespostaSeguranca.setBackground(redBorder);
                    editRespostaSeguranca.setText("");

                    MensagemUtil.exibir(EsqueciSenhaActivity.this, "Resposta Errada!");

                    editRespostaSeguranca.requestFocus();
                }
            }
        });
    }
}