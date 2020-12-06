/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.frases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class frasesAdapter extends RecyclerView.Adapter<frasesAdapter.frasesHolder> {
    private List<Pictograma> pictoFraseList;
    AdministarSesion idioma ;


    public class frasesHolder extends RecyclerView.ViewHolder{
        private final TextView nombrePictograma;
        public ImageView imagen;


        private frasesHolder(View itemView){
            super(itemView);
            nombrePictograma = itemView.findViewById(R.id.nombre_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);

        }

    }
    public frasesAdapter(ArrayList<Pictograma> pictoFraseList){
        this.pictoFraseList=pictoFraseList;
    }

    public frasesAdapter(FragmentActivity activity){}

    @Override
    public frasesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_pic_frases,parent, false);
        idioma = new AdministarSesion(parent.getContext());
        return new frasesHolder(itemview);
    }

    @Override
    public void onBindViewHolder(frasesAdapter.frasesHolder holder, int position) {
        if (pictoFraseList != null) {
            Pictograma current = pictoFraseList.get(position);

            Glide.with(holder.itemView.getContext())
                    .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                    .thumbnail(0.5f)
                    .into(holder.imagen);

            if(idioma.getIdioma()==1){
                holder.nombrePictograma.setText(current.getPictograma_nombre());
            }else{
                holder.nombrePictograma.setText(current.getPictograma_name());}

            holder.setIsRecyclable(false);

        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
            holder.nombrePictograma.setText("No existe pictogramas");
        }
    }

    void setPictograma(List<Pictograma> pic){
        pictoFraseList = pic;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(pictoFraseList != null)
            return pictoFraseList.size();
        else
            return 0;
    }

    @Override
    public void onViewRecycled(@NonNull frasesHolder holder) {
        super.onViewRecycled(holder);
        holder.imagen.setImageBitmap(null);
        holder.imagen.setImageDrawable(null);
        holder.nombrePictograma.setText(null);
        holder.setIsRecyclable(false);
    }
}
