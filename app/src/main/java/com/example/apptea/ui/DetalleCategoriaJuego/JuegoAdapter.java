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






public class JuegoAdapter extends RecyclerView.Adapter<JuegoAdapter.JuegoViewHolder> implements View.OnClickListener {

    private OnJuegoListener mOnJuegoListener;
    private List<Juego> juegos;
    private final LayoutInflater cjInflater;
    private View.OnClickListener listener;
    private JuegoAdapter.ButtonClicked buttonClicked;


    public JuegoAdapter(Context context, OnJuegoListener onJuegoListener) {
       cjInflater = LayoutInflater.from(context);
       this.mOnJuegoListener=onJuegoListener;
    }



    public interface ButtonClicked{
        void deleteClickedCatHab(Juego juego);
        void updateClickedCatHab(Juego juego);
    }

    public void setButtonClicked(JuegoAdapter.ButtonClicked buttonClicked){
        this.buttonClicked = buttonClicked;
    }

    class JuegoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nombreJuego;
        public MaterialButton editar;
        public MaterialButton eliminar;
        OnJuegoListener onJuegoListener;





        public JuegoViewHolder(@NonNull View itemView, OnJuegoListener onJuegoListener) {
            super(itemView);
            nombreJuego = itemView.findViewById(R.id.nombre_item_juego);
            editar = itemView.findViewById(R.id.btn_editar_juego_lista);
            eliminar = itemView.findViewById(R.id.btn_eliminar_juego_lista);
            this.onJuegoListener = onJuegoListener;
            itemView.setOnClickListener(this);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    buttonClicked.deleteClickedCatHab(juegos.get(getAdapterPosition()));
                }
            });

        }

        @Override
        public void onClick(View v) {
            onJuegoListener.onJuegoClick(juegos.get(getAdapterPosition()));
        }
    }


    @NonNull
    @Override
    public JuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = cjInflater.inflate(R.layout.fragment_item__juego,parent,false);
        layoutView.setOnClickListener(this);
        return new JuegoViewHolder(layoutView,mOnJuegoListener);
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
               // holder.setIsRecyclable(false);
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


    @Override
    public void onViewAttachedToWindow(@NonNull JuegoViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        animateCircularReveal(holder.itemView);
    }

    private void animateCircularReveal(View itemView) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = (int) Math.hypot(itemView.getWidth(),itemView.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(itemView,centerX,centerY,startRadius,endRadius);
        itemView.setVisibility(View.VISIBLE);
        animation.start();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull JuegoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


    public interface OnJuegoListener{
        void onJuegoClick(Juego juego);
    }
}
