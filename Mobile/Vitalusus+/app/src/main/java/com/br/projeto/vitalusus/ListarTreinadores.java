package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.br.projeto.vitalusus.adapter.TreinadorAdapter;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListarTreinadores extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TreinadorAdapter treinadorAdapter;
    private List<Treinador> treinadorList = new ArrayList<>();
    private List<Usuario> usuarioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_treinadores);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        treinadorAdapter = new TreinadorAdapter(usuarioList, treinadorList);
        recyclerView.setAdapter(treinadorAdapter);

        fetchTreinadores();
    }

    private void fetchTreinadores() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Chamada para buscar os usuários com tipoUsuario = "TREINADOR"
        Call<List<Usuario>> callUsuarios = apiService.findUsuariosTreinadores("TREINADOR");
        callUsuarios.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioList.clear();
                    usuarioList.addAll(response.body());
                    Log.d("Sucesso", "Número de Usuários Treinadores: " + usuarioList.size());

                    // Depois de obter os usuários, busca os treinadores
                    Call<List<Treinador>> callTreina = apiService.findAllTreinadores();
                    callTreina.enqueue(new Callback<List<Treinador>>() {
                        @Override
                        public void onResponse(Call<List<Treinador>> call, Response<List<Treinador>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                treinadorList.clear();
                                treinadorList.addAll(response.body());
                                treinadorAdapter.notifyDataSetChanged();

                                Toast.makeText(ListarTreinadores.this, "Dados carregados com sucesso", Toast.LENGTH_SHORT).show();
                                Log.d("Sucesso", "Número de Treinadores: " + treinadorList.size());
                                Log.d("URL Completa", retrofit.baseUrl().toString() + "treinadores?tipoUsuario=TREINADOR");

                            } else {
                                Toast.makeText(ListarTreinadores.this, "Erro ao carregar treinadores", Toast.LENGTH_SHORT).show();
                                Log.d("Erro", "Falha ao carregar treinadores: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Treinador>> call, Throwable t) {
                            Log.e("Retrofit Error", "Erro: " + t.getMessage());
                            Toast.makeText(ListarTreinadores.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(ListarTreinadores.this, "Resposta vazia ou erro na resposta", Toast.LENGTH_SHORT).show();
                    Log.d("Retrofit Response", "Status Code: " + response.code());
                    Log.d("Retrofit Body", "Body: " + response.body());
                    Log.d("URL Completa", retrofit.baseUrl().toString() + "treinadores?tipoUsuario=TREINADOR");
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("Retrofit Error", "Erro: " + t.getMessage());
                Toast.makeText(ListarTreinadores.this, "Erro ao carregar dados do 1º if", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
