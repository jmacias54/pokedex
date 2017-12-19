package com.pokedex.pokedex;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pokedex.pokedex.model.Pokemon;

import java.util.ArrayList;

/**
 * Created by Hitss on 19/12/2017.
 */

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataSet;
    private Context context;

    public ListPokemonAdapter (Context context){
        this.context = context;
        dataSet = new ArrayList<> ();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataSet.get(position);
        holder.nameTxtView.setText(p.getName());

        Glide.with( context ).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getNumber()+".png")
        .centerCrop()
        .crossFade()
        .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.pokemonImgView);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public  void addListPokemon(ArrayList<Pokemon> listaPokemon) {
        dataSet.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    class ViewHolder  extends RecyclerView.ViewHolder{

        private ImageView pokemonImgView;
        private TextView nameTxtView;

       public ViewHolder(View itemView) {
           super(itemView);

           pokemonImgView= (ImageView) itemView.findViewById(R.id.pokemonImgView);
           nameTxtView= (TextView) itemView.findViewById(R.id.nameTxtView);

       }
   }



}
