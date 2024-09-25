package com.br.projeto.vitalusus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private GridLayout videoGrid;
    private ApiService apiService;

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
        filterVideos("all");

        return view;
    }

    private void filterVideos(String category) {
        // Limpa o video grid atual
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
                Toast.makeText(getContext(), "Erro ao buscar vídeos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateVideoGrid(List<Video> videos) {
        for (Video video : videos) {
            // Verifica se o contexto está disponível
            if (getContext() == null) return;

            View videoItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, videoGrid, false);

            // Configura a miniatura do vídeo
            ImageView videoThumbnail = videoItemView.findViewById(R.id.video_thumbnail);
            if (video.getThumbnail() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(video.getThumbnail(), 0, video.getThumbnail().length);
                videoThumbnail.setImageBitmap(bitmap);
            }

            // Configura o título do vídeo
            TextView videoTitle = videoItemView.findViewById(R.id.video_title);
            videoTitle.setText(video.getTitulo() != null ? video.getTitulo() : "Título não disponível");

            // Configura o nome do canal e a data de postagem
            TextView channelName = videoItemView.findViewById(R.id.channel_name);
            channelName.setText(video.getCanal() != null && video.getCanal().getNome() != null ? video.getCanal().getNome() : "Canal não disponível");

            TextView videoDate = videoItemView.findViewById(R.id.video_date);
            videoDate.setText(video.getDataPostagem() != null ? video.getDataPostagem() : "Data não disponível");

            // Adiciona a view do item ao GridLayout
            videoGrid.addView(videoItemView);
        }
    }

    private byte[] getMockThumbnail() {
        // Simule uma thumbnail para testes usando uma imagem existente
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
