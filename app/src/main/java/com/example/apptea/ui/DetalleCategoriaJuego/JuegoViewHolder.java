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

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.google.android.material.button.MaterialButton;

public class JuegoViewHolder extends RecyclerView.ViewHolder {
    public TextView nombreJuego;
    public MaterialButton editar;
    public MaterialButton eliminar;


    public JuegoViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreJuego = itemView.findViewById(R.id.nombre_item_juego);
        editar = itemView.findViewById(R.id.btn_editar_juego_lista);
        eliminar = itemView.findViewById(R.id.btn_eliminar_juego_lista);

    }
}
