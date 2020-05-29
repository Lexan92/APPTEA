

package com.example.apptea.ui.categoriapictograma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;


import roomsqlite.entidades.CategoriaPictograma;

public class CategoriaPictogramaAdapter extends RecyclerView.Adapter<CategoriaPictogramaAdapter.CategoriaPictogramaHolder> {


    class CategoriaPictogramaHolder extends RecyclerView.ViewHolder{
        private final TextView categoriaItemView;

        private CategoriaPictogramaHolder(View itemView){
            super(itemView);
            categoriaItemView = itemView.findViewById((R.id.txt_categoria_pictograma));
        }
    }

    private final LayoutInflater mInflater;
    private List<CategoriaPictograma> categoriaPictogramaList;

    CategoriaPictogramaAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public CategoriaPictogramaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_categoria_pictograma,parent, false);



        return new CategoriaPictogramaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CategoriaPictogramaAdapter.CategoriaPictogramaHolder holder, int position) {
        if (categoriaPictogramaList != null) {
            CategoriaPictograma current = categoriaPictogramaList.get(position);
            holder.categoriaItemView.setText(current.getCat_pictograma_nombre());
        } else {
            // Covers the case of data not being ready yet.
            holder.categoriaItemView.setText("No existe ninguna categoria para habilidades cotidianas");
        }
    }

    void setCategoria(List<CategoriaPictograma> categorias){
        categoriaPictogramaList = categorias;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(categoriaPictogramaList != null)
            return categoriaPictogramaList.size();
       else
        return 0;
    }
}
