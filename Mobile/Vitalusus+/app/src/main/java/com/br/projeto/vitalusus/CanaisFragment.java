package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.projeto.vitalusus.adapter.TreinadorAdapter;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CanaisFragment extends Fragment {

    private RecyclerView recyclerView;
    private TreinadorAdapter treinadorAdapter;
    private List<Treinador> treinadorList = new ArrayList<>();
    private List<Usuario> usuarioList = new ArrayList<>();
    private List<Canal> canalList = new ArrayList<>();
    private Map<Integer, Usuario> treinadorUsuarioMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canais, container, false);

        getActivity().setTitle("Canais");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        treinadorAdapter = new TreinadorAdapter(usuarioList, treinadorList, canalList, (usuario, treinador, canal) -> {
            openDetailFragment(canal.getId());
        });
        recyclerView.setAdapter(treinadorAdapter);

        fetchTreinadores();
        return view;
    }

    private void openDetailFragment(int canalId) {
        DetailFragment detailFragment = DetailFragment.newInstance(canalId);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fetchTreinadores() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Busca usuários com tipoUsuario "TREINADOR"
        apiService.findUsuariosTreinadores("TREINADOR").enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioList.clear();
                    usuarioList.addAll(response.body());
                    Log.d("Sucesso", "Usuários treinadores carregados: " + usuarioList.size());

                    // Log para cada usuário e sua foto
                    for (Usuario usuario : usuarioList) {
                        Log.d("UsuarioFoto", "ID: " + usuario.getId() + ", Nome: " + usuario.getNome() + ", Foto: " + usuario.getFoto());
                    }

                    // Após carregar os usuários, busca treinadores
                    fetchTreinadoresData(apiService);
                } else {
                    mostrarErro("Falha ao carregar usuários treinadores", response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                mostrarErro("Erro ao carregar usuários treinadores: " + t.getMessage());
            }
        });
    }

    private void fetchTreinadoresData(ApiService apiService) {
        apiService.findAllTreinadores().enqueue(new Callback<List<Treinador>>() {
            @Override
            public void onResponse(Call<List<Treinador>> call, Response<List<Treinador>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    treinadorList.clear();
                    treinadorList.addAll(response.body());
                    Log.d("Sucesso", "Treinadores carregados: " + treinadorList.size());

                    // Mapeia os treinadores ao respectivo usuário via `usuario_id`
                    for (Treinador treinador : treinadorList) {
                        Usuario usuario = treinadorUsuarioMap.get(treinador.getUsuarioId());
                        if (usuario != null) {
                            Log.d("TreinadorUsuario", "Treinador: " + treinador.getId() + " está associado ao Usuario: " + usuario.getNome());
                        }
                    }

                    // Após carregar os treinadores, busca canais
                    fetchCanaisData(apiService);
                } else {
                    mostrarErro("Falha ao carregar treinadores", response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Treinador>> call, Throwable t) {
                mostrarErro("Erro ao carregar treinadores: " + t.getMessage());
            }
        });
    }

    private void fetchCanaisData(ApiService apiService) {
        apiService.findAllCanal().enqueue(new Callback<List<Canal>>() {
            @Override
            public void onResponse(Call<List<Canal>> call, Response<List<Canal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    canalList.clear();
                    canalList.addAll(response.body());
                    treinadorAdapter.notifyDataSetChanged();
                    Log.d("Sucesso", "Canais carregados: " + canalList.size());
                } else {
                    mostrarErro("Falha ao carregar canais", response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Canal>> call, Throwable t) {
                mostrarErro("Erro ao carregar canais: " + t.getMessage());
            }
        });
    }

    private void mostrarErro(String mensagem, int codigo) {
        Toast.makeText(requireContext(), mensagem + " (Código: " + codigo + ")", Toast.LENGTH_SHORT).show();
        Log.e("Erro", mensagem + " - Código: " + codigo);
    }

    private void mostrarErro(String mensagem) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show();
        Log.e("Erro", mensagem);
    }
}
