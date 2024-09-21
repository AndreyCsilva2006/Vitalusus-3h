package com.br.projeto.vitalusus.network;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.response.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @GET("/usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("usuarios")
    Call<List<Usuario>> findAll();

    @POST("/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

//    @GET("/vitalusus/usuario/findAll")
//    Call<List<Usuario>> findAll();
}
