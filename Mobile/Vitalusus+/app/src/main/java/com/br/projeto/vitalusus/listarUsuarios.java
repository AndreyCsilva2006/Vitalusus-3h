package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.br.projeto.vitalusus.adapter.UsuarioAdapter;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

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
        setContentView(R.layout.activity_main);

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
                if (response.isSuccessful()) {
                    usuarioList.clear();
                    usuarioList.addAll(response.body());
                    usuarioAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // Trate o erro
                Toast.makeText(listarUsuarios.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}