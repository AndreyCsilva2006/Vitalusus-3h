package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormLogin extends AppCompatActivity {

    // Aviso: é possivel acessar o login através do second fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
    }
}