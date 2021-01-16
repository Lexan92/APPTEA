package com.example.apptea.ui.DetalleCategoriaJuego;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Juego;

public class JuegoAdapterPaciente extends RecyclerView.Adapter<JuegoAdapterPaciente.JuegoViewHolder> {

    private OnJuegoListener mOnJuegoListener;
    private List<Juego> juegos;
    private final LayoutInflater cjInflater;
    AdministarSesion idioma ;


    public JuegoAdapterPaciente(Context context, OnJuegoListener onJuegoListener) {
        cjInflater = LayoutInflater.from(context);
        this.mOnJuegoListener = onJuegoListener;
        idioma = new AdministarSesion(context);
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
            PictogramaDAO pictogramaDAO = appDatabase.getDatabase(holder.itemView.getContext()).pictogramaDAO();
            Juego juego = juegos.get(position);
            if(juego.getPictograma_id() == 0){
                if(idioma.getIdioma()==1){
                    holder.nombreJuego.setText(juego.getJuego_nombre());
                }else{
                    holder.nombreJuego.setText(juego.getName_game());}
            }else {

                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(pictogramaDAO.findbyPictoId(juego.getPictograma_id()).getPictograma_imagen()))
                        .thumbnail(0.5f)
                        .into(holder.imagen);

                if (idioma.getIdioma() == 1) {
                    holder.nombreJuego.setText(juego.getJuego_nombre());
                } else {
                    holder.nombreJuego.setText(juego.getName_game());
                }
            }
            holder.eliminar.setVisibility(View.GONE);


        }

    }


    class JuegoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombreJuego;
        public MaterialButton eliminar;
        OnJuegoListener onJuegoListener;
        public ImageView imagen;

        public JuegoViewHolder(@NonNull View itemView, OnJuegoListener onJuegoListener) {
            super(itemView);
            nombreJuego = itemView.findViewById(R.id.nombre_item_juego);
            eliminar = itemView.findViewById(R.id.btn_eliminar_juego_lista);
            imagen = itemView.findViewById(R.id.img_juego);
            eliminar.setVisibility(View.INVISIBLE);
            this.onJuegoListener = onJuegoListener;
            itemView.setOnClickListener(this);
            itemView.setTag(this);
        }

        @Override
        public void onClick(View v) {
            JuegoViewHolder holder = (JuegoViewHolder) v.getTag();
            holder.setIsRecyclable(false);
            onJuegoListener.onJuegoClick(juegos.get(getAdapterPosition()),v);

        }
    }

    public void setJuegos(List<Juego> listjuegos) {
        juegos = listjuegos;
        notifyDataSetChanged();
    }


    public interface OnJuegoListener {
        void onJuegoClick(Juego juego, View v);
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
