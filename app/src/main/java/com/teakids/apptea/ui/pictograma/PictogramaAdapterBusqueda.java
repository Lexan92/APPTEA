

/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.pictograma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teakids.apptea.R;
import com.teakids.apptea.utilidades.AdministarSesion;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class PictogramaAdapterBusqueda extends RecyclerView.Adapter<PictogramaAdapterBusqueda.PictogramaHolder> implements Filterable {


    private OnPictogramaListener mOnPictogramaListener;
    private final LayoutInflater mInflater;
    private List<Pictograma> pictogramaList;
    private List<Pictograma> pictogramaListBusqueda;
    AdministarSesion idioma ;


    public interface OnPictogramaListener{
        void onPictogramaClick(Pictograma posicion);
    }


    class PictogramaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView pictogramaItemView;
        public ImageView imagen;
        OnPictogramaListener onPictogramaListener;



        private PictogramaHolder(View itemView,OnPictogramaListener onPictogramaListener){
            super(itemView);
            pictogramaItemView = itemView.findViewById((R.id.nombre_pictograma_busqueda));
            imagen = itemView.findViewById(R.id.img_pictograma_busqueda);
            this.onPictogramaListener = onPictogramaListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onPictogramaListener.onPictogramaClick(pictogramaList.get(getAdapterPosition()));
        }
    }


    public PictogramaAdapterBusqueda(Context context, OnPictogramaListener onPictogramaListener){
        mInflater = LayoutInflater.from(context);
        this.mOnPictogramaListener = onPictogramaListener;
        idioma = new AdministarSesion(context);
    }




    @Override
    public PictogramaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_pictograma_busqueda,parent, false);



        return new PictogramaHolder(itemview, mOnPictogramaListener);
    }

    @Override
    public void onBindViewHolder(PictogramaAdapterBusqueda.PictogramaHolder holder, int position) {
        if (pictogramaList != null) {
            Pictograma current = pictogramaList.get(position);



            Glide.with(holder.itemView.getContext())
            .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
            .thumbnail(0.5f)
            .into(holder.imagen);
            if(idioma.getIdioma()==1){
                holder.pictogramaItemView.setText(current.getPictograma_nombre());
            }else{
                holder.pictogramaItemView.setText(current.getPictograma_name());}

            holder.setIsRecyclable(false);



        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
            holder.pictogramaItemView.setText(holder.itemView.getContext().getString(R.string.noExistePicto));
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

                    if(idioma.getIdioma()==1){
                        if (item.getPictograma_nombre().toLowerCase().contains(patronDeFiltrado)){

                            listaFiltrada.add(item);
                        }
                    }else{
                        if (item.getPictograma_name().toLowerCase().contains(patronDeFiltrado)){

                            listaFiltrada.add(item);
                        }}

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
