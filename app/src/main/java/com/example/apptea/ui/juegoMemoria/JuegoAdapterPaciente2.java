package com.example.apptea.ui.juegoMemoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import roomsqlite.entidades.Juego;


public class JuegoAdapterPaciente2 extends RecyclerView.Adapter<JuegoAdapterPaciente2.JuegoViewHolder> {

    private OnJuegoListener mOnJuegoListener;
    private List<Juego> juegos;
    private final LayoutInflater cjInflater;
    AdministarSesion idioma ;




    public JuegoAdapterPaciente2(Context context, OnJuegoListener onJuegoListener) {
        cjInflater = LayoutInflater.from(context);
        this.mOnJuegoListener = onJuegoListener;
        idioma = new AdministarSesion(context);
    }


    @NonNull
    @Override
    public JuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull JuegoViewHolder holder, int position) {
        if (juegos != null && position < juegos.size()) {
            Juego juego = juegos.get(position);

            if(idioma.getIdioma()==1){
                holder.nombreJuego.setText(juego.getJuego_nombre());
            }else{
                holder.nombreJuego.setText(juego.getName_game());}
            holder.eliminar.setVisibility(View.GONE);
            holder.setIsRecyclable(true);

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class JuegoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView nombreJuego;
    public MaterialButton eliminar;
    OnJuegoListener onJuegoListener;

        public JuegoViewHolder(@NonNull View itemView, OnJuegoListener onJuegoListener) {
            super(itemView);
            nombreJuego = itemView.findViewById(R.id.nombre_item_juego);
            eliminar = itemView.findViewById(R.id.btn_eliminar_juego_lista);
            eliminar.setVisibility(View.INVISIBLE);
            this.onJuegoListener = onJuegoListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onJuegoListener.onJuegoClick(juegos.get(getAdapterPosition()),v);
        }
    }

    public void setJuegos(List<Juego> listjuegos) {
        juegos = listjuegos;
        notifyDataSetChanged();
    }

    public interface OnJuegoListener {
        void onJuegoClick(Juego juego,View v);
    }

    @Override
    public void onViewRecycled(@NonNull JuegoViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(false);
    }

}
