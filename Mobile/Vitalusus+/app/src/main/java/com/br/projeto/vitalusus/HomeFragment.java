package com.br.projeto.vitalusus;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private GridLayout videoGrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout para esse fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Iniciar as views
        videoGrid = view.findViewById(R.id.video_grid);

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

        // Exemplo da lista de videos (isso normalmente viria de um banco de dados ou API)
        List<Video> videos = getMockVideos();

        // Filtrar videos baseados na categoria
        List<Video> filteredVideos = new ArrayList<>();
        for (Video video : videos) {
            if ("all".equals(category) || video.getCategory().equals(category)) {
                filteredVideos.add(video);
            }
        }

        // Popular a grid com os videos filtrados
        populateVideoGrid(filteredVideos);
    }

    private void populateVideoGrid(List<Video> videos) {
        for (Video video : videos) {
            ImageView videoThumbnail = new ImageView(getContext());
            videoThumbnail.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150)));
            videoThumbnail.setImageResource(video.getThumbnailResource()); // Use video-specific thumbnail
            videoThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(4, 4, 4, 4);
            videoThumbnail.setLayoutParams(params);

            videoGrid.addView(videoThumbnail);
        }
    }

    // Lista simulada de vídeos, devemos substituir isso pela nossa fonte de dados real
    private List<Video> getMockVideos() {
        List<Video> videos = new ArrayList<>();
        videos.add(new Video(R.drawable.logo, "alta"));
        videos.add(new Video(R.drawable.abababa, "treinos"));
        videos.add(new Video(R.drawable.logo, "recomendacoes"));
        videos.add(new Video(R.drawable.abababa, "dieta"));
        // Adicione quantos videos forem precisos
        return videos;
    }

    // Classe de video para demonstração
    private static class Video {
        private final int thumbnailRecurso;
        private final String categoria;

        public Video(int thumbnailResource, String categoria) {
            this.thumbnailRecurso = thumbnailResource;
            this.categoria = categoria;
        }

        public int getThumbnailResource() {
            return thumbnailRecurso;
        }

        public String getCategory() {
            return categoria;
        }
    }
}