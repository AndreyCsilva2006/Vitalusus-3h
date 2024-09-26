package com.br.projeto.vitalusus.api;
import com.br.projeto.vitalusus.model.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoApi {

    // Endpoint que retorna os vídeos postados por canais que o usuário segue
    @GET("videos/seguidos")
    Call<List<Video>> getVideosSeguidos();
}
