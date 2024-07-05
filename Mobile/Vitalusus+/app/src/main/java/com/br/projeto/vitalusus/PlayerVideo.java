package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayerVideo extends AppCompatActivity  {

    private VideoView videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_video);

        videoPlayer = findViewById(R.id.videoV);

        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoPlayer);
        videoPlayer.setMediaController(mediaController);
        videoPlayer.setKeepScreenOn(true);
        videoPlayer.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video));
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