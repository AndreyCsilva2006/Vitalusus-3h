package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private View containerComponents;
    private TextView txtPerguntaSeguranca;
    private EditText editRespostaSeguranca;
    private AppCompatButton btnResgatarSenha, btnVerificarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        containerComponents = findViewById(R.id.containerComponents);
        txtPerguntaSeguranca = findViewById(R.id.txt_ESpSeguranca);
        editRespostaSeguranca = findViewById(R.id.edit_ESrSeguranca);
        btnResgatarSenha = findViewById(R.id.btnResgatarSenha);
        btnVerificarEmail = findViewById(R.id.btnVerificarEmail);

        txtPerguntaSeguranca.setVisibility(View.GONE);
        editRespostaSeguranca.setVisibility(View.GONE);
        btnResgatarSenha.setVisibility(View.GONE);

        ViewGroup.LayoutParams layoutParams = containerComponents.getLayoutParams();
        layoutParams.height = 460;

        btnVerificarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.height = 770;
                containerComponents.setLayoutParams(layoutParams);
                txtPerguntaSeguranca.setVisibility(View.VISIBLE);
                editRespostaSeguranca.setVisibility(View.VISIBLE);
                btnResgatarSenha.setVisibility(View.VISIBLE);

            }
        });
    }
}