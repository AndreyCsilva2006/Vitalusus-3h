package com.br.projeto.vitalusus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.br.projeto.vitalusus.R;
import com.br.projeto.vitalusus.adapter.NotificacoesAdapter;
import com.br.projeto.vitalusus.api.VideoApi;
import com.br.projeto.vitalusus.api.ApiClient;
import com.br.projeto.vitalusus.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacoesFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificacoesAdapter adapter;
    private List<Video> listaNotificacoes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacoes, container, false);

        // Inicializar a RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewNotificacoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getActivity().setTitle("Notificações");

        // Carregar notificações do banco de dados
        carregarNotificacoes();

        return view;
    }

    // Método que busca notificações do banco de dados via API
    private void carregarNotificacoes() {
        VideoApi videoApi = ApiClient.getClient().create(VideoApi.class);
        Call<List<Video>> call = videoApi.getVideosSeguidos(); // Método que busca vídeos de canais que o usuário segue

        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaNotificacoes = response.body(); // Recebe os vídeos do banco de dados
                    adapter = new NotificacoesAdapter(listaNotificacoes, getContext());
                    recyclerView.setAdapter(adapter); // Atualiza a RecyclerView com os dados recebidos
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                // Tratar falha (mostrar uma mensagem de erro, etc)
            }
        });
    }
}
