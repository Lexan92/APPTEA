
/* Clase Java para crear el pictograma que ira en la aprte superior de la pantalla*/

package com.example.apptea.ui.habilidadCotidiana;

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


import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class PictoSecuenciaAdapter extends RecyclerView.Adapter<PictoSecuenciaAdapter.PictoSecuenciaHolder> {
    private List<Pictograma> pictoFraseList;



    public class PictoSecuenciaHolder extends RecyclerView.ViewHolder{
        private final TextView nombrePictograma;
        public ImageView imagen;


        private PictoSecuenciaHolder(View itemView){
            super(itemView);
            nombrePictograma = itemView.findViewById(R.id.nombre_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);

        }

    }
    public PictoSecuenciaAdapter(ArrayList<Pictograma> pictoFraseList){
        this.pictoFraseList=pictoFraseList;
    }

    public PictoSecuenciaAdapter(FragmentActivity activity){}

    @Override
    public PictoSecuenciaAdapter.PictoSecuenciaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_pic_secuencia,parent, false);
        return new PictoSecuenciaAdapter.PictoSecuenciaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PictoSecuenciaAdapter.PictoSecuenciaHolder holder, int position) {
        if (pictoFraseList != null) {
            Pictograma current = pictoFraseList.get(position);

            Glide.with(holder.itemView.getContext())
                    .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                    .thumbnail(0.5f)
                    .into(holder.imagen);

            holder.nombrePictograma.setText(current.getPictograma_nombre());
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
    public void onViewRecycled(@NonNull PictoSecuenciaAdapter.PictoSecuenciaHolder holder) {
        super.onViewRecycled(holder);
        holder.imagen.setImageBitmap(null);
        holder.imagen.setImageDrawable(null);
        holder.nombrePictograma.setText(null);
        holder.setIsRecyclable(false);
    }
}
