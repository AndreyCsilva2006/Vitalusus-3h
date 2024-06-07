package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormLogin extends AppCompatActivity {

    // Aviso: caso ao inciar o app e ir para esta tela, na pasta manifests foi alterado qual tela irá ser ligada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
    }
}