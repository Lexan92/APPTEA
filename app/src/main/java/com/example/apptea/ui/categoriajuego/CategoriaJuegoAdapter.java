

package com.example.apptea.ui.categoriajuego;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;

public class CategoriaJuegoAdapter extends RecyclerView.Adapter<CategoriaJuegoAdapter.CategoriaViewHolder> {


    private List<CategoriaJuego> allCategoriasJuegos;

    class CategoriaViewHolder extends RecyclerView.ViewHolder{
        private final TextView nombreCategoria;

        private CategoriaViewHolder(View itemView){
            super(itemView);
            nombreCategoria = itemView.findViewById(R.id.nombre_categoria_juego);
        }
    }



    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gestion_juego,parent,false);
        return new CategoriaViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
    if (allCategoriasJuegos != null && position < allCategoriasJuegos.size()){
        CategoriaJuego categoriaJuego = allCategoriasJuegos.get(position);
        holder.nombreCategoria.setText(categoriaJuego.getCategoriaJuegoNombre());
    }else{
        holder.nombreCategoria.setText("No hay Categorias");
    }
    }

    void setCategoriasJuegos(List<CategoriaJuego> categoriasJuegos){
        allCategoriasJuegos = categoriasJuegos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (allCategoriasJuegos != null)
            return allCategoriasJuegos.size();
        else return 0;
    }
}
