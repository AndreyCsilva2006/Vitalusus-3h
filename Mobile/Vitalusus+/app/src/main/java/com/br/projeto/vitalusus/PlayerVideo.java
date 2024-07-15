package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayerVideo extends AppCompatActivity  {

    private VideoView videoPlayer;
    // URL da plataforma blender
 // String videoUrl = "https://video.blender.org/download/videos/3d95fb3d-c866-42c8-9db1-fe82f48ccb95-804.mp4";
    String videoUrl = "https://www.youtube.com/embed/_ttcR7VDouE?si=75jU7L3DEy4Vytmm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_video);

        videoPlayer = findViewById(R.id.videoV);

        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoPlayer);
        videoPlayer.setMediaController(mediaController);
        videoPlayer.setKeepScreenOn(true);
        // Pega onde tá localizado o vídeo.
        videoPlayer.setVideoURI(Uri.parse(videoUrl));
        videoPlayer.start();

        videoPlayer.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setOnVideoSizeChangedListener((player, width, height) -> {
                MediaController controller = new MediaController(PlayerVideo.this);
                videoPlayer.setMediaController(controller);
                controller.setAnchorView(videoPlayer);
            });
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            ViewGroup.LayoutParams layoutParams = videoPlayer.getLayoutParams();
            layoutParams.width = 20; // Defina a largura desejada
            layoutParams.height = 20; // Defina a altura desejada
            videoPlayer.setLayoutParams(layoutParams);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ViewGroup.LayoutParams layoutParams = videoPlayer.getLayoutParams();
            layoutParams.width = 200; // Defina a largura desejada
            layoutParams.height = 200; // Defina a altura desejada
            videoPlayer.setLayoutParams(layoutParams);
        }
    }
}