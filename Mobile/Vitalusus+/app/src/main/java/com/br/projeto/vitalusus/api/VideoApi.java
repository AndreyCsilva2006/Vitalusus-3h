package com.br.projeto.vitalusus.api;
import com.br.projeto.vitalusus.model.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VideoApi {

    // Endpoint que retorna os vídeos postados por canais que o usuário segue

        @GET("videos/{id}")
        Call<Video> getVideoById(@Path("id") long videoId);

        @GET("videos/seguidos")
        Call<List<Video>> getVideosSeguidos();


}
