package com.br.projeto.vitalusus;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.br.projeto.vitalusus.api.VideoApi;
import com.br.projeto.vitalusus.model.Video;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoFragment extends Fragment {

    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private TextView tvVideoTitle, tvChannelName, tvDatePosted;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        playerView = view.findViewById(R.id.player_view);
        tvVideoTitle = view.findViewById(R.id.tv_video_title);
        tvChannelName = view.findViewById(R.id.tv_channel_name);
        tvDatePosted = view.findViewById(R.id.tv_date_posted);

        // Inicializar o ExoPlayer
        exoPlayer = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(exoPlayer);

        // Obter o ID do vídeo (você pode obter isso dos argumentos do fragmento)
        long videoId = getArguments().getLong("videoId");

        // Buscar vídeo do banco de dados usando Retrofit
        fetchVideoFromDatabase(videoId);

        return view;
    }

    private void fetchVideoFromDatabase(long videoId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3030/") // Insira a URL correta do servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VideoApi videoApi = retrofit.create(VideoApi.class);
        Call<Video> call = videoApi.getVideoById(videoId);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Video video = response.body();

                    // Exibir informações do vídeo
                    tvVideoTitle.setText(video.getTitulo());
//                    tvChannelName.setText(video.getCanal().getNome());
//                    tvDatePosted.setText(video.getCanal().getBio());
//
                    // Carregar e reproduzir o vídeo
//                    playVideoFromBytes(video.getThumbnail());
                } else {
                    Log.e("VideoFragment", "Erro ao buscar o vídeo");
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("VideoFragment", "Falha na conexão: " + t.getMessage());
            }
        });
    }

    private void playVideoFromBytes(byte[] videoBytes) {
        try {
            // Converter os bytes do vídeo em um arquivo temporário e obter o URI
            Uri videoUri = PlayerVideo.saveVideoToFile(requireContext(), videoBytes);

            // Preparar o ExoPlayer com o arquivo de vídeo
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
        } catch (IOException e) {
            Log.e("VideoFragment", "Erro ao salvar o vídeo temporariamente", e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
