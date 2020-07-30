

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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.Detalle_Juego;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class PictogramaAdapter extends RecyclerView.Adapter<PictogramaAdapter.PictogramaHolder> implements Filterable {


    private OnPictogramaListener mOnPictogramaListener;
    private final LayoutInflater mInflater;
    public boolean isVocabulary=false;
    private List<Pictograma> pictogramaList;
    private List<Pictograma> pictogramaListBusqueda;
    private ButtonClickedPictograma buttonClickedPictograma;

    public interface OnPictogramaListener{
        void onPictogramaClick(Pictograma posicion);
    }
    public interface ButtonClickedPictograma{
        void deleteClickedPictograma (Pictograma pictograma);
        void updateClicledPictograma (Pictograma pictograma);
        void itemClickedPictograma(Pictograma pictograma);
    }


    public void setButtonClickedPictograma(ButtonClickedPictograma buttonClickedPictograma){
        this.buttonClickedPictograma= buttonClickedPictograma;
    }

    class PictogramaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView pictogramaItemView;
        public Button eliminar;
        public Button editar;
        public ImageView imagen;
        OnPictogramaListener onPictogramaListener;



        private PictogramaHolder(View itemView,OnPictogramaListener onPictogramaListener){
            super(itemView);
            pictogramaItemView = itemView.findViewById((R.id.nombre_pictograma));
            eliminar = itemView.findViewById(R.id.btn_eliminar_pictograma);
            editar = itemView.findViewById(R.id.btn_editar_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);
            this.onPictogramaListener = onPictogramaListener;
            itemView.setOnClickListener(this);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickedPictograma.deleteClickedPictograma(pictogramaList.get(getAdapterPosition()));
                }
            });

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickedPictograma.updateClicledPictograma(pictogramaList.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onClick(View v) {
            onPictogramaListener.onPictogramaClick(pictogramaList.get(getAdapterPosition()));
        }
    }





    public PictogramaAdapter(Context context, OnPictogramaListener onPictogramaListener){
        mInflater = LayoutInflater.from(context);
        this.mOnPictogramaListener = onPictogramaListener;
    }




    @Override
    public PictogramaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_pictograma,parent, false);



        return new PictogramaHolder(itemview, mOnPictogramaListener);
    }

    @Override
    public void onBindViewHolder(PictogramaAdapter.PictogramaHolder holder, int position) {
        if (pictogramaList != null) {
            Pictograma current = pictogramaList.get(position);

            if(isVocabulary==true){
                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                        .thumbnail(0.5f)
                        .into(holder.imagen);
                holder.pictogramaItemView.setText(current.getPictograma_nombre());
                holder.eliminar.setVisibility(View.GONE);
                holder.editar.setVisibility(View.GONE);
                holder.setIsRecyclable(false);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClickedPictograma.itemClickedPictograma(current);
                    }
                });
            }else{
                if(current.isPredeterminado()){

                    // holder.imagen.setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.predeterminada));
                    // holder.imagen.setImageBitmap(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()));
                    Glide.with(holder.itemView.getContext())
                            .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                            .thumbnail(0.5f)
                            .into(holder.imagen);

                    holder.pictogramaItemView.setText(current.getPictograma_nombre());
                    holder.editar.setVisibility(View.GONE);
                    holder.eliminar.setVisibility(View.GONE);
                    holder.setIsRecyclable(false);
                }
                else {
                    //holder.imagen.setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.predeterminada));
                    //  holder.imagen.setImageBitmap(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()));

                    Glide.with(holder.itemView.getContext())
                            .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                            .thumbnail(0.5f)
                            .into(holder.imagen);
                    holder.pictogramaItemView.setText(current.getPictograma_nombre());
                    holder.setIsRecyclable(false);
                }
            }


        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
            holder.pictogramaItemView.setText("No existe pictogramas");
        }
    }

    public void setPictograma(List<Pictograma> pic){
        pictogramaList = pic;
        pictogramaListBusqueda = new ArrayList<>(pictogramaList);
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
        holder.imagen.setImageBitmap(null);
        holder.imagen.setImageDrawable(null);
        holder.pictogramaItemView.setText(null);
        holder.setIsRecyclable(false);
    }


    @Override
    public Filter getFilter() {
        return pictogramaFilter;
    }

    private Filter pictogramaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pictograma> listaFiltrada = new ArrayList<>();

            if (constraint != null||constraint.length() != 0){

                String patronDeFiltrado = constraint.toString().toLowerCase().trim();

                for(Pictograma item:pictogramaListBusqueda){
                    if (item.getPictograma_nombre().toLowerCase().contains(patronDeFiltrado)){

                        listaFiltrada.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listaFiltrada;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pictogramaList.clear();
            pictogramaList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };



}
