/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.categoriapictograma;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

public class CategoriaPictogramaViewHolder extends RecyclerView.ViewHolder{

    public TextView nombreCategoria;
    public Button cancelar;
    public Button editar;


    public CategoriaPictogramaViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreCategoria = itemView.findViewById(R.id.txt_categoria_pictograma);
        cancelar = itemView.findViewById(R.id.btn_cancelarCPic);
        editar = itemView.findViewById(R.id.btn_editarCPic);

    }




}
