package com.example.apptea.ui.DetalleCategoriaJuego;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import roomsqlite.entidades.Juego;

public class JuegoAdapterPaciente extends RecyclerView.Adapter<JuegoAdapterPaciente.JuegoViewHolder> {

    private OnJuegoListener mOnJuegoListener;
    private List<Juego> juegos;
    private final LayoutInflater cjInflater;


    public JuegoAdapterPaciente(Context context, OnJuegoListener onJuegoListener) {
        cjInflater = LayoutInflater.from(context);
        this.mOnJuegoListener = onJuegoListener;
    }

    @NonNull
    @Override
    public JuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = cjInflater.inflate(R.layout.fragment_item__juego, parent, false);
        return new JuegoViewHolder(layoutView, mOnJuegoListener);
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


    class JuegoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
    public int getItemCount() {
        if (juegos != null)
            return juegos.size();
        else return 0;
    }

    @Override
    public void onViewRecycled(@NonNull JuegoViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(false);
    }


    @Override
    public void onViewAttachedToWindow(@NonNull JuegoViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        animateCircularReveal(holder.itemView);
    }

    private void animateCircularReveal(View itemView) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = (int) Math.hypot(itemView.getWidth(), itemView.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(itemView, centerX, centerY, startRadius, endRadius);
        itemView.setVisibility(View.VISIBLE);
        animation.start();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull JuegoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


}
