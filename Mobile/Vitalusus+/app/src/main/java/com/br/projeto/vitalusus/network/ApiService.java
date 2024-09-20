package com.br.projeto.vitalusus.network;
import com.br.projeto.vitalusus.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @GET("/usuarios")
    Call<List<Usuario>> getUsuarios();

    @POST("/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @GET("/vitalusus/usuario/findAll")
    Call<List<Usuario>> findAll();
}
