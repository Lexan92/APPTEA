

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
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.Detalle_Juego;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class PictogramaAdapter extends RecyclerView.Adapter<PictogramaAdapter.PictogramaHolder> {


    class PictogramaHolder extends RecyclerView.ViewHolder{
        private final TextView pictogramaItemView;
        public Button eliminar;
        public Button editar;
        public ImageView imagen;


        private PictogramaHolder(View itemView){
            super(itemView);
            pictogramaItemView = itemView.findViewById((R.id.nombre_pictograma));
            eliminar = itemView.findViewById(R.id.btn_eliminar_pictograma);
            editar = itemView.findViewById(R.id.btn_editar_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);


        }
    }

    private final LayoutInflater mInflater;
    private List<Pictograma> pictogramaList;

    PictogramaAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public PictogramaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_pictograma,parent, false);



        return new PictogramaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PictogramaAdapter.PictogramaHolder holder, int position) {
        if (pictogramaList != null) {
            Pictograma current = pictogramaList.get(position);

            if(current.isPredeterminado()==true){

               // holder.imagen.setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.predeterminada));
               // holder.imagen.setImageBitmap(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()));
                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                        .into(holder.imagen);

                holder.pictogramaItemView.setText(current.getPictograma_nombre());
                holder.editar.setVisibility(View.INVISIBLE);
                holder.eliminar.setVisibility(View.INVISIBLE);
            }
            else {
                //holder.imagen.setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.predeterminada));
              //  holder.imagen.setImageBitmap(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()));

                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                        .into(holder.imagen);
                holder.pictogramaItemView.setText(current.getPictograma_nombre());
            }




        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
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

    @Override
    public void onViewRecycled(@NonNull PictogramaHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(true);
    }
}
