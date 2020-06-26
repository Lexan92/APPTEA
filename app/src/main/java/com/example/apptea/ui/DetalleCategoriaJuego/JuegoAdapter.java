/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaJuegoViewHolder;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Juego;

public class JuegoAdapter extends RecyclerView.Adapter<JuegoViewHolder> implements View.OnClickListener {

    private List<Juego> juegos;
    private final LayoutInflater cjInflater;
    private View.OnClickListener listener;

    public JuegoAdapter(Context context) {
       cjInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public JuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = cjInflater.inflate(R.layout.fragment_item__juego,parent,false);
        layoutView.setOnClickListener(this);
        return new JuegoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull JuegoViewHolder holder, int position) {
        if (juegos != null && position < juegos.size()) {
            Juego juego = juegos.get(position);
            if (juego.isJuego_predeterminado() == true) {
                holder.nombreJuego.setText(juego.getJuego_nombre());
                holder.editar.setVisibility(View.GONE);
                holder.eliminar.setVisibility(View.GONE);
                holder.setIsRecyclable(false);

            } else {
                holder.nombreJuego.setText(juego.getJuego_nombre());
                holder.setIsRecyclable(false);
            }
        }
    }

    public void setJuegos(List<Juego> listjuegos){
       juegos = listjuegos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(juegos!=null)
            return juegos.size();
        else return 0;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onViewRecycled(@NonNull JuegoViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(false);
    }
}
