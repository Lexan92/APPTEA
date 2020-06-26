

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

public class CategoriaJuegoAdapter extends RecyclerView.Adapter<CategoriaJuegoViewHolder> implements View.OnClickListener  {

    private List<CategoriaJuego> categoriasJuego;
    private final LayoutInflater cjInflater;
    private View.OnClickListener listener;



    
     public CategoriaJuegoAdapter(Context context) {

        cjInflater = LayoutInflater.from(context);

    }

    @Override
    public CategoriaJuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layoutView = cjInflater.inflate(R.layout.fragment_item_categoria_juego,parent,false);

        layoutView.setOnClickListener(this);
        return new CategoriaJuegoViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoriaJuegoViewHolder holder, int position) {
    if (categoriasJuego != null && position < categoriasJuego.size()){
        CategoriaJuego categoriaJuego = categoriasJuego.get(position);
        holder.nombreCategoria.setText(categoriaJuego.getCategoriaJuegoNombre());
        holder.setIsRecyclable(false);

    }else{
        holder.nombreCategoria.setText("No hay Categorias");
        holder.setIsRecyclable(false);
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

    public void setOnClickListener(View.OnClickListener listener){
         this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onViewRecycled(@NonNull CategoriaJuegoViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(false);
    }
}
