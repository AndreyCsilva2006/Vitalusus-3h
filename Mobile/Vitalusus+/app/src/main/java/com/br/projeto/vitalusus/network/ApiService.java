package com.br.projeto.vitalusus.network;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.EmailMessage;
import com.br.projeto.vitalusus.model.Equipamento;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.model.VideoResponse;
import com.br.projeto.vitalusus.response.AlunoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("/usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("usuarios2")
    Call<List<Usuario>> findAll();

    @GET("canais")
    Call<List<Canal>> findAllCanal();

    @GET("treinadores")
    Call<List<Treinador>> findAllTreinadores();

    @GET("videos")
    Call<List<Video>> findAllVideo();

    @GET("videos/com-detalhes")
    Call<List<VideoResponse>> findAllVideosComDetalhes();

    @GET("usuarios")
    Call<List<Usuario>> findUsuariosTreinadores(@Query("tipoUsuario") String tipoUsuario);

    @GET("/search")
    Call<List<Video>> searchVideos(@Query("q") String query);  // Temos que substituir para o nosso endpoint real

    @GET("/usuarios/{id}")
    Call<Usuario> getUsuarioById(@Path("id") int id);

    @GET("/treinadores/{id}")
    Call<Treinador> getTreinadorById(@Path("id") int id);

    @GET("/canais/{id}")
    Call<Canal> getCanalById(@Path("id") int id);

    @GET("/videos/{id}")
    Call<Video> getVideolById(@Path("id") int id);

    @GET("videos/com-detalhes/canal/{canalId}")
    Call<List<VideoResponse>> getVideosByCanalId(@Path("canalId") int canalId);

    @GET("/usuarios/{idade}")
    Call<Usuario> getIdade(@Path("idade") int idade);

    // POST

    @POST("chamarApi8080")
    Call<Void> verificarEmail(@Body EmailMessage emailMessage);

}


