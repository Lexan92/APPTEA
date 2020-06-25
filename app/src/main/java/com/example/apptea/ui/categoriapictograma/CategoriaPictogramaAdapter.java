

package com.example.apptea.ui.categoriapictograma;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;


import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaJuegoViewHolder;

import java.util.ArrayList;
import java.util.List;


import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

public class CategoriaPictogramaAdapter extends RecyclerView.Adapter<CategoriaPictogramaViewHolder> implements View.OnClickListener {

    private List<CategoriaPictograma> categoriaPictogramaList;
    private final LayoutInflater mInflater;
    private  View.OnClickListener listener;


    private ArrayList<Pictograma> pictogramas;


    public CategoriaPictogramaAdapter(Context context) {

        mInflater = LayoutInflater.from(context);

    }



    @Override
    public CategoriaPictogramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = mInflater.inflate(R.layout.fragment_item_categoria_pictograma,parent, false);

        layoutView.setOnClickListener(this);
        return new CategoriaPictogramaViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaPictogramaViewHolder holder, int position) {
        if (categoriaPictogramaList != null && position < categoriaPictogramaList.size()) {

            CategoriaPictograma categoriaPictograma = categoriaPictogramaList.get(position);
            if (categoriaPictograma.isPredeterminado()==true){
                holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
               // holder.editar.setVisibility(View.INVISIBLE);
                holder.cancelar.setVisibility(View.GONE);
                holder.setIsRecyclable(false);
            }
            else {
            holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
            holder.setIsRecyclable(false);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.nombreCategoria.setText("No existe ninguna categoria de pictogramas");
        }
    }

   public void setCategoria(List<CategoriaPictograma> categorias){
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onViewRecycled(@NonNull CategoriaPictogramaViewHolder holder) {
        super.onViewRecycled(holder);
        holder.nombreCategoria.setText(null);
        holder.setIsRecyclable(false);
    }
}
