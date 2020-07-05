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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;

import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class PictoFrasesAdapter extends RecyclerView.Adapter<PictoFrasesAdapter.PictoFrasesHolder> {
    private List<Pictograma> pictogramaList;
    private ClickedPicto clickedPicto;

    public interface ClickedPicto{
        void PictoClicked(Pictograma pictograma);
    }

    public void setClickedPicto (ClickedPicto clickedPicto){
        this.clickedPicto= clickedPicto;
    }

    public class PictoFrasesHolder extends RecyclerView.ViewHolder{
        private final TextView nombrePictograma;
        public ImageView imagen;


        private PictoFrasesHolder(View itemView){
            super(itemView);
            nombrePictograma = itemView.findViewById(R.id.nombre_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);

        }

    }

    public PictoFrasesAdapter(FragmentActivity activity){}

    @Override
    public PictoFrasesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_pic_frases,parent, false);
        return new PictoFrasesHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PictoFrasesAdapter.PictoFrasesHolder holder, int position) {
        if (pictogramaList != null) {
            Pictograma pictograma = pictogramaList.get(position);

               Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()))
                        .thumbnail(0.5f)
                        .into(holder.imagen);

                holder.nombrePictograma.setText(pictograma.getPictograma_nombre());
                holder.setIsRecyclable(false);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickedPicto.PictoClicked(pictograma);
                    }
                });

        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
            holder.nombrePictograma.setText("No existe pictogramas");
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
    public void onViewRecycled(@NonNull PictoFrasesHolder holder) {
        super.onViewRecycled(holder);
        holder.imagen.setImageBitmap(null);
        holder.imagen.setImageDrawable(null);
        holder.nombrePictograma.setText(null);
        holder.setIsRecyclable(false);
    }

}
