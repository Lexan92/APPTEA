/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.categoriahabilidadcotidiana;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;

public class CategoriaHabCotidianaAdapter extends RecyclerView.Adapter<CategoriaHabCotidianaAdapter.CategoriaHabCotidianaHolder> {

    private  View.OnClickListener listener;

    class CategoriaHabCotidianaHolder extends RecyclerView.ViewHolder{
        private final TextView categoriaItemView;


        private CategoriaHabCotidianaHolder(View itemView){
            super(itemView);
            categoriaItemView = itemView.findViewById((R.id.txt_categoria_hab_cotidiana));
        }
    }

    private final LayoutInflater mInflater;
    private List<CategoriaHabCotidiana> categoriaHabCotidianaList;

    CategoriaHabCotidianaAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public CategoriaHabCotidianaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recyclerview_item_categoria_hab_cotidiana,parent, false);



        return new CategoriaHabCotidianaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CategoriaHabCotidianaAdapter.CategoriaHabCotidianaHolder holder, int position) {
        if (categoriaHabCotidianaList != null) {
            CategoriaHabCotidiana current = categoriaHabCotidianaList.get(position);
            holder.categoriaItemView.setText(current.getCat_hab_cotidiana_nombre());
        } else {
            // Covers the case of data not being ready yet.
            holder.categoriaItemView.setText("No existe ninguna categoria para habilidades cotidianas");
        }
    }

    void setCategoria(List<CategoriaHabCotidiana> categorias){
        categoriaHabCotidianaList = categorias;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        if(categoriaHabCotidianaList != null)
            return categoriaHabCotidianaList.size();
       else
        return 0;
    }
}
