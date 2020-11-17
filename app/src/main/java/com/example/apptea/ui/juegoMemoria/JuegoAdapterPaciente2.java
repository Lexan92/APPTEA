package com.example.apptea.ui.juegoMemoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import roomsqlite.entidades.Juego;


public class JuegoAdapterPaciente2 extends RecyclerView.Adapter<JuegoAdapterPaciente2.JuegoViewHolder> {

    private OnJuegoListener mOnJuegoListener;
    private List<Juego> juegos;
    private final LayoutInflater cjInflater;




    public JuegoAdapterPaciente2(Context context, OnJuegoListener onJuegoListener) {
        cjInflater = LayoutInflater.from(context);
        this.mOnJuegoListener = onJuegoListener;
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
            holder.nombreJuego.setText(juego.getJuego_nombre());
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
            onJuegoListener.onJuegoClick(juegos.get(getAdapterPosition()));
        }
    }

    public void setJuegos(List<Juego> listjuegos) {
        juegos = listjuegos;
        notifyDataSetChanged();
    }

    public interface OnJuegoListener {
        void onJuegoClick(Juego juego);
    }

    @Override
    public void onViewRecycled(@NonNull JuegoViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(false);
    }

}
