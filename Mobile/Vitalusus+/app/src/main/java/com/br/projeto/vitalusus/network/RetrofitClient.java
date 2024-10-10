package com.br.projeto.vitalusus.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:3030/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
        }
        return retrofit;
    }

}
