package com.example.apptea.ui.habilidadCotidiana;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.habilidadCotidiana.HabilidadCotidianaAdapter;
import com.example.apptea.ui.pictograma.PictogramaAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Secuencia;
import roomsqlite.repositorios.SecuenciaRepository;

import static androidx.camera.core.CameraX.getContext;

public class HabilidadCotidianaAdapter extends RecyclerView.Adapter<HabilidadCotidianaAdapter.HabilidadCotidianaHolder>{

    private  View.OnClickListener listener;
    private OnHabilidadListener mOnHabilidadListener;
    private final LayoutInflater mInflater;
    public boolean isHabilidad = false;
    private List<HabilidadCotidiana> habilidadCotidianaList;
    private List<HabilidadCotidiana> habilidadCotidianaListBusqueda;
    private List<Secuencia> pictoFraseList;



    class HabilidadCotidianaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView habilidadItemView;
        public Button eliminar;
        public Button editar;
        public ImageView imagen;
        OnHabilidadListener onHabilidadListener;

        public HabilidadCotidianaHolder( View itemView,OnHabilidadListener onHabilidadListener) {
            super(itemView);
            habilidadItemView = itemView.findViewById((R.id.nombre_hab_cotidiana));
            eliminar = itemView.findViewById(R.id.btn_eliminar_hab_cotidiana);
            editar = itemView.findViewById(R.id.btn_editar_hab_cotidiana);
            imagen = itemView.findViewById(R.id.img_hab_cotidiana);
            this.onHabilidadListener = onHabilidadListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHabilidadListener.onHabilidadClick(habilidadCotidianaList.get(getAdapterPosition()));
            notifyDataSetChanged();
        }
    }

    public HabilidadCotidianaAdapter(Context context, OnHabilidadListener onHabilidadListener){
        mInflater = LayoutInflater.from(context);
        this.mOnHabilidadListener = onHabilidadListener;
    }


    @Override
    public HabilidadCotidianaHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recyclerview_item_hab_cotidiana,parent, false);

        return new HabilidadCotidianaHolder(itemview, mOnHabilidadListener);
    }

    @Override
    public void onBindViewHolder(HabilidadCotidianaAdapter.HabilidadCotidianaHolder holder, int position) {

        if (habilidadCotidianaList != null) {
            HabilidadCotidiana current = habilidadCotidianaList.get(position);

        if(isHabilidad==true ||current.isPredeterminado()){
                holder.habilidadItemView.setText(current.getHabilidad_cotidiana_nombre());
                holder.editar.setVisibility(View.INVISIBLE);
                holder.eliminar.setVisibility(View.INVISIBLE);
                holder.setIsRecyclable(false);
        }else {
               /* Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                        .thumbnail(0.5f)
                        .into(holder.imagen);*/
                holder.habilidadItemView.setText(current.getHabilidad_cotidiana_nombre());
                holder.setIsRecyclable(false);
            }


        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
            holder. habilidadItemView.setText("No existe habilidades");
        }

    }

    @Override
    public int getItemCount() {
        if(habilidadCotidianaList != null)
            return habilidadCotidianaList.size();
        else
            return 0;
    }


    public interface OnHabilidadListener{
        void onHabilidadClick(HabilidadCotidiana posicion);
    }

    void setHabiilidad(List<HabilidadCotidiana> habilidadesCotidianas){
        habilidadCotidianaList = habilidadesCotidianas;
        habilidadCotidianaListBusqueda = new ArrayList<>(habilidadCotidianaList);
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull HabilidadCotidianaAdapter.HabilidadCotidianaHolder holder) {
        super.onViewRecycled(holder);
        holder.imagen.setImageBitmap(null);
        holder.imagen.setImageDrawable(null);
        holder.habilidadItemView.setText(null);
        holder.setIsRecyclable(false);
    }


}
