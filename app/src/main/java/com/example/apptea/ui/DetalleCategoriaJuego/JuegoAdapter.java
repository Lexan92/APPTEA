package com.example.apptea.ui.DetalleCategoriaJuego;

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


public class JuegoAdapter extends RecyclerView.Adapter<JuegoAdapter.JuegoViewHolder> {

    private OnJuegoListener mOnJuegoListener;
    private List<Juego> juegos;
    private final LayoutInflater cjInflater;
    AdministarSesion idioma ;
    private JuegoAdapter.ButtonClicked buttonClicked;


    public JuegoAdapter(Context context, OnJuegoListener onJuegoListener) {
        cjInflater = LayoutInflater.from(context);
        this.mOnJuegoListener = onJuegoListener;
        idioma = new AdministarSesion(context);

    }


    public interface ButtonClicked {
        void deleteClickedCatHab(Juego juego);
    }

    public void setButtonClicked(JuegoAdapter.ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    class JuegoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombreJuego;
        public MaterialButton eliminar;
        OnJuegoListener onJuegoListener;


        public JuegoViewHolder(@NonNull View itemView, OnJuegoListener onJuegoListener) {
            super(itemView);
            nombreJuego = itemView.findViewById(R.id.nombre_item_juego);
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
        View layoutView = cjInflater.inflate(R.layout.fragment_item__juego, parent, false);
        return new JuegoViewHolder(layoutView, mOnJuegoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JuegoViewHolder holder, int position) {
        if (juegos != null && position < juegos.size()) {
            Juego juego = juegos.get(position);
            System.out.println("Idioma es  " + idioma.getIdioma());
            if(idioma.getIdioma()==1){
                holder.nombreJuego.setText(juego.getJuego_nombre());
            }else{
                holder.nombreJuego.setText(juego.getName_game());}

            if (juego.isJuego_predeterminado()) {
                holder.eliminar.setVisibility(View.GONE);
                holder.setIsRecyclable(false);

            } else {
                holder.setIsRecyclable(false);
            }
        }
    }

    public void setJuegos(List<Juego> listjuegos) {
        juegos = listjuegos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (juegos != null)
            return juegos.size();
        else return 0;
    }





    @Override
    public void onViewAttachedToWindow(@NonNull JuegoViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }


    @Override
    public void onViewDetachedFromWindow(@NonNull JuegoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


    public interface OnJuegoListener {
        void onJuegoClick(Juego juego);
    }
}
