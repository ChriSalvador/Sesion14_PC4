package com.example.sesion14_pc4.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JokeApi {
    @GET("joke/{category}")
    Call<Object> getJoke(@Path("category") String category, @Query("lang") String language);

}
