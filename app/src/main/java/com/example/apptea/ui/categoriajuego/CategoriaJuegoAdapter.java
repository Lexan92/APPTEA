

package com.example.apptea.ui.categoriajuego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.DetalleCategoriaJuego;

import java.net.SocketOptions;
import java.util.List;

import roomsqlite.entidades.CategoriaJuego;

public class CategoriaJuegoAdapter extends RecyclerView.Adapter<CategoriaJuegoViewHolder>  {

    private List<CategoriaJuego> categoriasJuego;
    private final LayoutInflater cjInflater;
    Context miContext;



    
     public CategoriaJuegoAdapter(Context context) {
        this.miContext = context;
        cjInflater = LayoutInflater.from(context);
  ;
    }

    @Override
    public CategoriaJuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layoutView = cjInflater.inflate(R.layout.fragment_item_categoria_juego,parent,false);

       layoutView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(miContext,"prueba click " + String.valueOf(viewType) , Toast.LENGTH_SHORT).show();
              // DetalleCategoriaJuego detalleCategoriaJuego = new DetalleCategoriaJuego();

           }
       });
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
