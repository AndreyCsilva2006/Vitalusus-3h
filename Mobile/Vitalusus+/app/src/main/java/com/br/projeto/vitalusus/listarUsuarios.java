package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.br.projeto.vitalusus.adapter.UsuarioAdapter;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.br.projeto.vitalusus.response.UsuarioResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class listarUsuarios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usuarioAdapter = new UsuarioAdapter(usuarioList);
        recyclerView.setAdapter(usuarioAdapter);

        fetchUsuarios();
    }

    private void fetchUsuarios() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Usuario>> call = apiService.findAll();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioList.clear();
                    usuarioList.addAll(response.body());

                    Toast.makeText(listarUsuarios.this, "entrou dentro do carlos onResponse", Toast.LENGTH_SHORT).show();

                    Log.d("Retrofit Success", "Número de usuários: " + usuarioList.size());
                    usuarioAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(listarUsuarios.this, "Resposta vazia ou erro na resposta", Toast.LENGTH_SHORT).show();
                    Log.d("Retrofit Response", "Status Code: " + response.code());
                    Log.d("Retrofit Body", "Body: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // Tratar o erro
                Log.e("Retrofit Error", "Erro: " + t.getMessage());
                Toast.makeText(listarUsuarios.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}