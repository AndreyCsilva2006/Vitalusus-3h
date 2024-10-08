package com.br.projeto.vitalusus.network;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.ChaveSeguranca;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.network.ApiService;

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

    @POST("chaveSeguranca")
    Call<ChaveSeguranca> createChaveSeguranca(@Body ChaveSeguranca chaveSeguranca);

    @FormUrlEncoded
    @POST("/usuarios")
    Call<Usuario> loginUser(
            @Field("email") String email,
            @Field("senha") String senha
    );
}


