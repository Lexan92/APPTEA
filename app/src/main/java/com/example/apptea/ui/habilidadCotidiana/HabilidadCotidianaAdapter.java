package com.example.apptea.ui.habilidadCotidiana;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apptea.R;
import com.example.apptea.ui.habilidadCotidiana.HabilidadCotidianaAdapter;
import com.example.apptea.ui.pictograma.PictogramaAdapter;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;

public class HabilidadCotidianaAdapter extends RecyclerView.Adapter<HabilidadCotidianaAdapter.HabilidadCotidianaHolder> {

    private OnHabilidadListener mOnHabilidadListener;
    private final LayoutInflater mInflater;
    private List<HabilidadCotidiana> habilidadCotidianaList;
    private List<HabilidadCotidiana> habilidadCotidianaListBusqueda;


    class HabilidadCotidianaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView habilidadItemView;
        OnHabilidadListener onHabilidadListener;

        public HabilidadCotidianaHolder( View itemView, HabilidadCotidianaAdapter.OnHabilidadListener onHabilidadListener) {
            super(itemView);
            habilidadItemView = itemView.findViewById((R.id.nombre_habilidad));
            this.onHabilidadListener = onHabilidadListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHabilidadListener.onHabilidadClick(habilidadCotidianaList.get(getAdapterPosition()));
        }
    }

    public HabilidadCotidianaAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
/*
    public HabilidadCotidianaAdapter(Context context, OnHabilidadListener onHabilidadListener){
        mInflater = LayoutInflater.from(context);
        this.mOnHabilidadListener = onHabilidadListener;
    }*/


    @Override
    public HabilidadCotidianaHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_habilidad,parent, false);

        return new HabilidadCotidianaHolder(itemview, mOnHabilidadListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HabilidadCotidianaHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
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
}
