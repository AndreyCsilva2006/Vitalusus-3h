package com.br.projeto.vitalusus.network;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Canal;
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

    @GET("canais")
    Call<List<Canal>> findAllCanal();

    @GET("treinadores")
    Call<List<Treinador>> findAllTreinadores();

    @GET("usuarios")
    Call<List<Usuario>> findUsuariosTreinadores(@Query("tipoUsuario") String tipoUsuario);

    @GET("/search")
    Call<List<Video>> searchVideos(@Query("q") String query);  // Temos que substituir para o nosso endpoint real

    // Método para buscar um Usuario por ID
    @GET("/usuarios/{id}")
    Call<Usuario> getUsuarioById(@Path("id") int id);

    // Método para buscar um Treinador por ID
    @GET("/treinadores/{id}")
    Call<Treinador> getTreinadorById(@Path("id") int id);

    // Método para buscar um Canal por ID
    @GET("/canais/{id}")
    Call<Canal> getCanalById(@Path("id") int id);

    // POST
    @POST("/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @POST("/alunos")
    Call<Aluno> createAluno(@Body Aluno aluno);

}
