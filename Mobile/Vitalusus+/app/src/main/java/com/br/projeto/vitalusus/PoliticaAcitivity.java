package com.br.projeto.vitalusus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PoliticaAcitivity extends AppCompatActivity {

    private ImageView voltarPolitica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica);

        voltarPolitica = findViewById(R.id.voltarPolitica);

        voltarPolitica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PoliticaAcitivity.this, FormCadastro.class);
                startActivity(i);
            }
        });

    }
}
