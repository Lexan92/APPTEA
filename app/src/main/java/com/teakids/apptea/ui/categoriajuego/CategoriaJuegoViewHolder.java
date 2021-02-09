/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.categoriajuego;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.teakids.apptea.R;

public class CategoriaJuegoViewHolder extends RecyclerView.ViewHolder{

    public TextView nombreCategoria;
    public LottieAnimationView juegoImg;


    public CategoriaJuegoViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreCategoria = itemView.findViewById(R.id.nombre_categoria_juego);
        juegoImg = itemView.findViewById(R.id.juegoImagen);

    }




}
