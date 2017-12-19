package com.pokedex.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pokedex.pokedex.api.ApiServices;
import com.pokedex.pokedex.api.ResponsePokemon;
import com.pokedex.pokedex.model.Pokemon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Poke-Api: ";
     private Retrofit retrofit ;
     private RecyclerView recyclerView;
     private ListPokemonAdapter listPokemonAdapter;
    private int offset ;
    private boolean onLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                    if(dy > 0){

                        int visibleCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                        if(onLoad){
                            if((visibleCount+pastVisibleItems)>= totalItemCount){
                                Log.d(TAG, "addOnScrollListener: onScrolled : Llegamos al Final !");
                                onLoad = false;
                                offset += 20 ;
                                obtenerDatos(offset);
                            }
                        }

                    }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        onLoad = true;
        offset = 0;
        obtenerDatos(offset);
    }
    private void obtenerDatos(int offset){
        ApiServices services = retrofit.create(ApiServices.class);

        Call<ResponsePokemon> responsePokemon = services.obtenerListaPokemon(20,offset);

        responsePokemon.enqueue(new Callback<ResponsePokemon>() {
            @Override
            public void onResponse(Call<ResponsePokemon> call, Response<ResponsePokemon> response) {
                Log.d(TAG, "onResponse: onResponse");
                onLoad = true;
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: isSuccessful");
                    ResponsePokemon pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    listPokemonAdapter.addListPokemon(listaPokemon);
                }else{

                    Log.d(TAG, "onResponse: " +response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponsePokemon> call, Throwable t) {
                onLoad = true;
                Log.e(TAG, "onResponse: onFailure" + t.getMessage());
            }
        });
    }
}
