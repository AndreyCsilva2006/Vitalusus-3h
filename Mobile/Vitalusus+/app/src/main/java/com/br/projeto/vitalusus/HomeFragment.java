package com.br.projeto.vitalusus;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.model.VideoResponse;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
//        categoryHigh.setOnClickListener(v -> filterVideos("alta"));
//        categoryWorkouts.setOnClickListener(v -> filterVideos("treinos"));
//        categoryRecommendations.setOnClickListener(v -> filterVideos("recomendacoes"));
//        categoryDiet.setOnClickListener(v -> filterVideos("dieta"));

        // Inicia o video grid com todas as categorias
//        filterVideos("all");
        fetchVideos();

        return view;
    }

    private void fetchVideos() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.findAllVideosComDetalhes().enqueue(new Callback<List<VideoResponse>>() {
            @Override
            public void onResponse(Call<List<VideoResponse>> call, Response<List<VideoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList.clear();
                    videoGrid.removeAllViews();

                    List<VideoResponse> videoResponses = response.body();
                    for (VideoResponse videoResponse : videoResponses) {
                        Video video = videoResponse.getVideo();
                        Canal canal = videoResponse.getCanal();
                        Usuario usuario = videoResponse.getUsuario();

                        // Só adiciona ao grid se os três objetos forem não-nulos
                        if (video != null && canal != null && usuario != null) {
                            addVideoToGrid(video, canal, usuario);
                            Log.d("Sucesso", "Vídeos carregados com sucesso");
                        } else {
                            Log.e("Erro", "Algum dado está nulo: video, canal ou usuario.");
                        }
                        Log.d("Debug", "Video: " + video);
                        Log.d("Debug", "Canal: " + canal);
                        Log.d("Debug", "Usuario: " + usuario);
                    }

                } else {
                    Log.d("Erro", "Erro ao carregar vídeos");
                    mostrarErro("Falha ao carregar usuários treinadores", response.code());
                }
            }

            @Override
            public void onFailure(Call<List<VideoResponse>> call, Throwable t) {
                // Verifica se o Fragment ainda está associado a um Context antes de exibir o Toast
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Falha ao carregar os vídeos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Método para filtrar vídeos com base na categoria
//        private void filterVideos (String category){
//            // Limpa o grid de vídeos atual
//            videoGrid.removeAllViews();
//
//            // Chama a API para buscar vídeos com Retrofit
//            apiService.searchVideos(category).enqueue(new Callback<List<Video>>() {
//                @Override
//                public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        List<Video> videos = response.body();
//                        populateVideoGrid(videos);
//                    } else {
//                        Toast.makeText(getContext(), "Nenhum vídeo encontrado", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Video>> call, Throwable t) {
//                    // Verifica se o Fragment ainda está associado a um Context antes de exibir o Toast
//                    if (getContext() != null) {
//                        Toast.makeText(getContext(), "Falha ao carregar os vídeos", Toast.LENGTH_SHORT).show();
//                    }
//                    // Outras ações de falha
//                }


        // Método para preencher o grid de vídeos
//                private void populateVideoGrid(List<Video> videos) {
//                    for (Video video : videos) {
//                        // Verifica se o contexto está disponível
//                        if (getContext() == null) return;
//
//                        View videoItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, videoGrid, false);
//
//                        // Configura a miniatura do vídeo
//                        ImageView videoThumbnail = videoItemView.findViewById(R.id.videoThumbnail);
//                        if (video.getThumbnail() != null) {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(video.getThumbnail(), 0, video.getThumbnail().length);
//                            videoThumbnail.setImageBitmap(bitmap);
//                        }
//
//                        // Configura o título do vídeo
//                        TextView videoTitle = videoItemView.findViewById(R.id.tituloVideo);
//                        videoTitle.setText(video.getTitulo() != null ? video.getTitulo() : "Título não disponível");
//
//                        // Configura o nome do canal
//                        TextView channelName = videoItemView.findViewById(R.id.nomeCanal);
////            channelName.setText(canal.getNome() != null && video.getCanal().getNome() != null ? video.getCanal().getNome() : "Canal não disponível");
//
//                        // Configura a data de postagem do vídeo
//                        TextView videoDate = videoItemView.findViewById(R.id.DataPubliVideo);
//                        videoDate.setText(video.getDataPubli() != null ? video.getDataPubli() : "Data não disponível");
//
//                        // Adiciona a view do item ao GridLayout
//                        videoGrid.addView(videoItemView);
//                    }
//                });


    }

    private void addVideoToGrid(Video video, Canal canal, Usuario usuario) {
        if (video == null) {
            Log.e("Erro", "O objeto video é null, não será adicionado ao grid.");
            return;
        }
        View videoItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, videoGrid, false);

        // configura thumbnail do vídeo
        ImageView videoThumbnail = videoItemView.findViewById(R.id.videoThumbnail);
//        if (video.getThumbnail() != null) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(video.getThumbnail(), 0, video.getThumbnail().length);
//            videoThumbnail.setImageBitmap(bitmap);
//        }

        TextView videoTitle = videoItemView.findViewById(R.id.tituloVideo);
        videoTitle.setText(video.getTitulo() != null ? video.getTitulo() : "Título não disponível");

        // Configura o nome do canal
        TextView channelName = videoItemView.findViewById(R.id.nomeCanal);
        channelName.setText(canal.getNome() != null ? canal.getNome() : "Canal não disponível");

        TextView videoVisualizacoes = videoItemView.findViewById(R.id.visualizacoesVideo);
//        videoVisualizacoes.setText(video.getVisualizacoes() != null ? video.getVisualizacoes() : "visualizacoes não disponível");

        TextView canalSeguidores = videoItemView.findViewById(R.id.seguidoresCanal);
//        canalSeguidores.setText(canal.getSeguidores() != null ? canal.getSeguidores() : "visualizacoes não disponível");

        // Configura a foto de perfil do usuário (treinador)
        ImageView userFoto = videoItemView.findViewById(R.id.fotoCanal);
        if (usuario.getFoto() != null) {
//            Bitmap userBitmap = BitmapFactory.decodeByteArray(usuario.getFoto(), 0, usuario.getFoto().is);
//            userFoto.setImageBitmap(userBitmap);
        }

        // Configura a data de postagem do vídeo
        TextView videoData = videoItemView.findViewById(R.id.DataPubliVideo);
        @SuppressLint({"NewApi", "LocalSuppress"}) String relativeDate = getRelativeTime(video.getDataPubli());
        videoData.setText(relativeDate);

        // Adiciona a view do item ao GridLayout
        videoGrid.addView(videoItemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getRelativeTime(String dataPubli) {
        try {
            // Obtemos a data atual em milissegundos no fuso horário local
            ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
            long dataAtual = now.toInstant().toEpochMilli();
            Log.d("TimeDifference", "Data atual (milissegundos): " + dataAtual);

            // Interpreta a data de publicação como um Instant (UTC) e converte para milissegundos
            long dataVideo = Instant.parse(dataPubli).toEpochMilli();
            Log.d("TimeDifference", "Data do vídeo (milissegundos): " + dataVideo);

            // Calculamos as diferenças em milissegundos
            long diferencaMillis = dataAtual - dataVideo;
            Log.d("TimeDifference", "Diferença em milissegundos: " + diferencaMillis);

            // Cálculos em várias unidades de tempo
            long dataEmSegundos = diferencaMillis / 1000;
            long dataEmMinutos = diferencaMillis / (1000 * 60);
            long dataEmHoras = diferencaMillis / (1000 * 60 * 60);
            long dataEmDias = diferencaMillis / (1000 * 60 * 60 * 24);
            long dataEmSemanas = diferencaMillis / (1000 * 60 * 60 * 24 * 7);
            long dataEmMeses = diferencaMillis / (1000 * 60 * 60 * 24 * 30); // Aproximado
            long dataEmAnos = diferencaMillis / (1000 * 60 * 60 * 24 * 365); // Aproximado

            // Logs das diferenças
            Log.d("TimeDifference", "Diferença em segundos: " + dataEmSegundos);
            Log.d("TimeDifference", "Diferença em minutos: " + dataEmMinutos);
            Log.d("TimeDifference", "Diferença em horas: " + dataEmHoras);
            Log.d("TimeDifference", "Diferença em dias: " + dataEmDias);
            Log.d("TimeDifference", "Diferença em semanas: " + dataEmSemanas);
            Log.d("TimeDifference", "Diferença em meses: " + dataEmMeses);
            Log.d("TimeDifference", "Diferença em anos: " + dataEmAnos);

            // Variáveis para armazenar o resultado
            long dataContada = 0;
            String diaString = "";

            // Lógica para determinar o formato da string de saída
            if (dataEmDias >= 1) {
                dataContada = Math.round(dataEmDias);
                diaString = (dataContada != 1) ? "dias" : "dia";
            } else if (dataEmHoras >= 1) {
                dataContada = Math.round(dataEmHoras);
                diaString = (dataContada != 1) ? "horas" : "hora";
            } else if (dataEmMinutos >= 1) {
                dataContada = Math.round(dataEmMinutos);
                diaString = (dataContada != 1) ? "minutos" : "minuto";
            } else {
                dataContada = Math.round(dataEmSegundos);
                diaString = (dataContada >= 0) ? ((dataContada != 1) ? "segundos" : "segundo") : "agora";
            }

            // Log do resultado final
            String resultado = dataContada + " " + diaString + " atrás";
            Log.d("TimeDifference", "Resultado: " + resultado);
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TimeDifference", "Erro ao processar a data: " + e.getMessage());
            return "Data inválida";
        }
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
