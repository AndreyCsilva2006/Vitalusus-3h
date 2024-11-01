package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.br.projeto.vitalusus.dao.UsuarioDAO;
import com.br.projeto.vitalusus.model.EmailMessage;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.br.projeto.vitalusus.util.MensagemUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private View containerComponents;
    private EditText editEsqueciSenhaEmail;
    private AppCompatButton btnVerificarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        containerComponents = findViewById(R.id.containerComponents);
        editEsqueciSenhaEmail = findViewById(R.id.editEsqueciSenhaEmail);
        btnVerificarEmail = findViewById(R.id.btnVerificarEmail);

        // Borda vermelha (caso ocorra alguma validação errada no formulário)
        GradientDrawable redBorder = new GradientDrawable();
        redBorder.setColor(Color.WHITE);
        redBorder.setCornerRadius(50);
        redBorder.setStroke(5, Color.RED);

        btnVerificarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pega o email que o usuário digitou na EditText
                String email = editEsqueciSenhaEmail.getText().toString().trim();

                // Criando o objeto EmailMessage para enviar
                EmailMessage emailMessage = new EmailMessage(email);

                // Fazendo a requisição usando Retrofit
                apiService.verificarEmail(emailMessage).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // A requisição foi bem-sucedida, faça algo como mostrar uma mensagem
                            Toast.makeText(getApplicationContext(), "Email verificado com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            // A requisição falhou, tratar o erro
                            Toast.makeText(getApplicationContext(), "Falha na verificação do email", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Aconteceu um erro de rede ou algo inesperado
                        Toast.makeText(getApplicationContext(), "Erro ao conectar-se ao servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
    private void sendEmail(String recipientEmail) {
        // remetente
        String senderEmail = "vitalususplusoficial@gmail.com";

        // Intent de email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // apenas aplicativos de email devem responder
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Assunto do email");
        intent.putExtra(Intent.EXTRA_TEXT, "Corpo do email");

        // Verifique se há algum aplicativo de email instalado
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "caiu no else do sendEmail", Toast.LENGTH_SHORT).show();
        }
    }
}