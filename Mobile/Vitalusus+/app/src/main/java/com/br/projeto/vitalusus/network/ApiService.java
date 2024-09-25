package com.br.projeto.vitalusus.network;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.response.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("treinadores")
    Call<List<Treinador>> findAllTreinadores();

    @GET("usuarios")
    Call<List<Usuario>> findUsuariosTreinadores(@Query("tipoUsuario") String tipoUsuario);

    @POST("/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @GET("/search")
    Call<List<Video>> searchVideos(@Query("q") String query);  // Temos que substituir para o nosso endpoint real




//    @GET("vitalusus/usuario/findAll")
//    Call<List<Usuario>> findAll();
}
