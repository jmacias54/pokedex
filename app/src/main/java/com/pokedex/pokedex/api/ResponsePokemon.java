package com.pokedex.pokedex.api;

import com.pokedex.pokedex.model.Pokemon;

import java.util.ArrayList;

/**
 * Created by Hitss on 19/12/2017.
 */

public class ResponsePokemon {

    private ArrayList<Pokemon> results;


    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
