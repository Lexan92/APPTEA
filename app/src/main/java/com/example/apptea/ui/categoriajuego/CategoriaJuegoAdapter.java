

package com.example.apptea.ui.categoriajuego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;

public class CategoriaJuegoAdapter extends RecyclerView.Adapter<CategoriaJuegoViewHolder> {

    private List<CategoriaJuego> categoriasJuego;

  /*  public static class CategoriaViewHolder extends RecyclerView.ViewHolder{
        public final TextView nombreCategoria;

        public CategoriaViewHolder(View itemView){
            super(itemView);
            nombreCategoria = itemView.findViewById(R.id.nombre_categoria_juego);
        }
    }*/
    private final LayoutInflater cjInflater;


    
     public CategoriaJuegoAdapter(Context context) {
       //this.categoriasJuegos = categoriaJuegos;
        cjInflater = LayoutInflater.from(context);
    }

    @Override
    public CategoriaJuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layoutView = cjInflater.inflate(R.layout.fragment_item_categoria_juego,parent,false);
        //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_categoria_juego,parent,false);
        return new CategoriaJuegoViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoriaJuegoViewHolder holder, int position) {
    if (categoriasJuego != null && position < categoriasJuego.size()){
        CategoriaJuego categoriaJuego = categoriasJuego.get(position);
        holder.nombreCategoria.setText(categoriaJuego.getCategoriaJuegoNombre());
    }else{
        holder.nombreCategoria.setText("No hay Categorias");
    }
    }

    public void setCategoriasJuegos(List<CategoriaJuego> categoriasJuegos){
        categoriasJuego = categoriasJuegos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (categoriasJuego != null)
            return categoriasJuego.size();
        else return 0;
    }
}
