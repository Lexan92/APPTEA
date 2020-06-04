

/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.pictograma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.Pictograma;

public class PictogramaAdapter extends RecyclerView.Adapter<PictogramaAdapter.PictogramaHolder> {

        ArrayList <Pictograma> pictogramas;
    class PictogramaHolder extends RecyclerView.ViewHolder{
        private final TextView pictogramaItemView;

        private PictogramaHolder(View itemView){
            super(itemView);
            pictogramaItemView = itemView.findViewById((R.id.txt_categoria_pictograma));
        }
    }

    private final LayoutInflater mInflater;
    private List<Pictograma> pictogramaList;

    PictogramaAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public PictogramaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_categoria_pictograma,parent, false);



        return new PictogramaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PictogramaAdapter.PictogramaHolder holder, int position) {
        if (pictogramaList != null) {
            Pictograma current = pictogramaList.get(position);
            holder.pictogramaItemView.setText(current.getPictograma_nombre());
        } else {
            // Covers the case of data not being ready yet.
            holder.pictogramaItemView.setText("No existe pictogramas");
        }
    }

    void setPictograma(List<Pictograma> pic){
        pictogramaList = pic;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(pictogramaList != null)
            return pictogramaList.size();
       else
        return 0;
    }
}
