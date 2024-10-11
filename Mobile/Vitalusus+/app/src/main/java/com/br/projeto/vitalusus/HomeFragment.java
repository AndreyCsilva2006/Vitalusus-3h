package com.br.projeto.vitalusus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private GridLayout videoGrid;
    private ApiService apiService;
    private List<Video> videoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout para esse fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicia as views
        videoGrid = view.findViewById(R.id.video_grid);

        // Inicializa Retrofit
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        getActivity().setTitle("Home");

        // Inicia os botões de categoria
        Button categoryHigh = view.findViewById(R.id.categoria_videosalta);
        Button categoryWorkouts = view.findViewById(R.id.categoria_treinos);
        Button categoryRecommendations = view.findViewById(R.id.categoria_recomendacoes);
        Button categoryDiet = view.findViewById(R.id.categoria_dieta);

        // Seta os listeners para os botões
        categoryHigh.setOnClickListener(v -> filterVideos("alta"));
        categoryWorkouts.setOnClickListener(v -> filterVideos("treinos"));
        categoryRecommendations.setOnClickListener(v -> filterVideos("recomendacoes"));
        categoryDiet.setOnClickListener(v -> filterVideos("dieta"));

        // Inicia o video grid com todas as categorias
//        filterVideos("all");
        fetchVideos();

        return view;
    }

    private void fetchVideos() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.findAllVideo().enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList.clear();
                    videoList.addAll(response.body());
                    Log.d("Sucesso", "Videos carregados: " + videoList.size());
                } else {
                    Log.d("NovoUsuario", "Usuário: " + videoList.toString()); // log para verificar os valores
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });
    }

    // Método para filtrar vídeos com base na categoria
    private void filterVideos(String category) {
        // Limpa o grid de vídeos atual
        videoGrid.removeAllViews();

        // Chama a API para buscar vídeos com Retrofit
        apiService.searchVideos(category).enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Video> videos = response.body();
                    populateVideoGrid(videos);
                } else {
                    Toast.makeText(getContext(), "Nenhum vídeo encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                // Verifica se o Fragment ainda está associado a um Context antes de exibir o Toast
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Falha ao carregar os vídeos", Toast.LENGTH_SHORT).show();
                }
                // Outras ações de falha
            }


    // Método para preencher o grid de vídeos
    private void populateVideoGrid(List<Video> videos) {
        for (Video video : videos) {
            // Verifica se o contexto está disponível
            if (getContext() == null) return;

            View videoItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, videoGrid, false);

            // Configura a miniatura do vídeo
            ImageView videoThumbnail = videoItemView.findViewById(R.id.videoThumbnail);
            if (video.getThumbnail() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(video.getThumbnail(), 0, video.getThumbnail().length);
                videoThumbnail.setImageBitmap(bitmap);
            }

            // Configura o título do vídeo
            TextView videoTitle = videoItemView.findViewById(R.id.tituloVideo);
            videoTitle.setText(video.getTitulo() != null ? video.getTitulo() : "Título não disponível");

            // Configura o nome do canal
            TextView channelName = videoItemView.findViewById(R.id.nomeCanal);
//            channelName.setText(canal.getNome() != null && video.getCanal().getNome() != null ? video.getCanal().getNome() : "Canal não disponível");

            // Configura a data de postagem do vídeo
            TextView videoDate = videoItemView.findViewById(R.id.DataPubliVideo);
            videoDate.setText(video.getDataPubli() != null ? video.getDataPubli() : "Data não disponível");

            // Adiciona a view do item ao GridLayout
            videoGrid.addView(videoItemView);
        }
    }

    // Método auxiliar para gerar uma thumbnail mock
    private byte[] getMockThumbnail() {
        // Simule uma thumbnail para testes usando uma imagem existente
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
});
    }}
