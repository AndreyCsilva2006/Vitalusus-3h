package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailFragment extends Fragment {

    private static final String ARG_CANAL_ID = "canal_id";
    private int canalId;

    public static DetailFragment newInstance(int canalId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CANAL_ID, canalId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            canalId = getArguments().getInt(ARG_CANAL_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        getActivity().setTitle("Detalhes do Canal");

        TextView nomeTextView = view.findViewById(R.id.txtNomeCanalDetail);
        TextView seguidoresTextView = view.findViewById(R.id.tv_SeguidoresCanalDetail);
        TextView visualizacoesTextView = view.findViewById(R.id.tv_VisualizacoesCanalDetail);
        TextView biografiaTextView = view.findViewById(R.id.tv_BiografiaCanalDetail);

        // Inicie o fetch de detalhes do canal
        fetchCanalDetails(canalId, nomeTextView, seguidoresTextView, visualizacoesTextView, biografiaTextView);

        return view;
    }

    private void fetchCanalDetails(int canalId, TextView nomeTextView, TextView seguidoresTextView, TextView visualizacoesTextView, TextView biografiaTextView) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Canal> callCanal = apiService.getCanalById(canalId);
        callCanal.enqueue(new Callback<Canal>() {
            @Override
            public void onResponse(Call<Canal> call, Response<Canal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Canal canal = response.body();

                    // Atualiza a UI com as informações do canal
                    nomeTextView.setText(canal.getNome());
                    seguidoresTextView.setText(String.valueOf(canal.getSeguidores()));
                    visualizacoesTextView.setText(String.valueOf(canal.getVisualizacoes()));
                    biografiaTextView.setText(canal.getBio());

                    // Verifica se o treinador_id não é nulo
                    Integer treinadorId = canal.getTreinadorId();
                    if (treinadorId != null) {
                        fetchTreinadorDetails(treinadorId); // Chamada para buscar as informações do treinador
                    } else {
                        Log.e("Erro", "TreinadorId é nulo.");
                        Toast.makeText(getContext(), "Treinador não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Erro", "Erro ao carregar canal: " + response.code());
                    Toast.makeText(getContext(), "Erro ao carregar canal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Canal> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do canal.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchTreinadorDetails(int treinadorId) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Treinador> callTreinador = apiService.getTreinadorById(treinadorId);
        callTreinador.enqueue(new Callback<Treinador>() {
            @Override
            public void onResponse(Call<Treinador> call, Response<Treinador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Treinador treinador = response.body();

                    Log.d("RespostaTreinador", "Treinador: " + treinador.toString());
                    Log.d("RespostaJSON", response.raw().toString());  // Loga a resposta bruta da API

                    // Verifica se o usuario_id não é nulo
                    Integer usuarioId = treinador.getUsuarioId();
                    if (usuarioId != null) {
                        fetchUsuarioDetails(usuarioId);
                    } else {
                        Log.e("Erro", "Treinador ou UsuarioId é nulo.");
                        Toast.makeText(getContext(), "Usuário do treinador não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Erro", "Erro ao carregar treinador: " + response.code());
                    Toast.makeText(getContext(), "Erro ao carregar treinador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Treinador> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do treinador.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUsuarioDetails(int usuarioId) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Busca as informações do usuário (treinador) pelo ID
        Call<Usuario> callUsuario = apiService.getUsuarioById(usuarioId);
        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();

                    // Atualizar a UI com as informações do treinador/usuário
                    updateUIDetails(usuario);
                } else {
                    Log.e("Erro", "Erro ao carregar usuário: " + response.code());
                    Toast.makeText(getContext(), "Erro ao carregar usuário.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do usuário.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUIDetails(Usuario usuario) {
        // Atualiza os TextViews, ImageViews, etc. com os dados do usuário
        TextView nomeTreinadorTextView = getView().findViewById(R.id.txtNomeTreinadorCanalDetail);
        nomeTreinadorTextView.setText(usuario.getNome());

        // Continue atualizando a UI com os detalhes do usuário e do canal
    }
}
