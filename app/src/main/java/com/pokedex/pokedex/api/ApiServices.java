package com.pokedex.pokedex.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hitss on 19/12/2017.
 */

public interface ApiServices {

    @GET("pokemon")
    Call<ResponsePokemon> obtenerListaPokemon(@Query("limit") int limit , @Query("offset")int offset);
}
