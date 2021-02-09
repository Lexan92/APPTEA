package com.teakids.apptea.ui.sesion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teakids.apptea.R;
import com.teakids.apptea.utilidades.UtilidadFecha;

import java.util.List;

import roomsqlite.entidades.DetalleSesion;

public class DetalleSesionAdapter extends RecyclerView.Adapter<DetalleSesionAdapter.DetalleSesionHolder> implements View.OnClickListener {

    private List<DetalleSesion> listaDetalle;
    private final OnDetalleListener mOnDetalleListener;
    private final LayoutInflater mInflater;


    @Override
    public void onClick(View v) {

    }

    public interface OnDetalleListener {
        void onDetalleClick(DetalleSesion detalleSesion);
    }

    public DetalleSesionAdapter(Context context, OnDetalleListener onDetalleListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnDetalleListener = onDetalleListener;
    }


    @Override
    public DetalleSesionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = mInflater.inflate(R.layout.fragment_item_detalle_sesion, parent, false);
        return new DetalleSesionHolder(itemview, mOnDetalleListener);
    }

    @Override
    public void onBindViewHolder( DetalleSesionHolder holder, int position) {

        if (listaDetalle!=null){
            DetalleSesion detalleSesion = listaDetalle.get(position);
            Log.d("ADAPTER", detalleSesion.getNombre_opcion());
            holder.detalle.setText(detalleSesion.getNombre_opcion());
            holder.fecha.setText(UtilidadFecha.obtenerFecha(detalleSesion.getHora_inicio()));
            holder.horaInicio.setText(UtilidadFecha.obtenerHora(detalleSesion.getHora_inicio()));
            holder.setIsRecyclable(false);
        }
    }

    @Override
    public int getItemCount() {
        if (listaDetalle != null)
            return listaDetalle.size();
        else
            return 0;
    }

    class DetalleSesionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView fecha;
        private final TextView horaInicio;
        private final TextView detalle;
        OnDetalleListener onDetaleListener;


        public DetalleSesionHolder(@NonNull View itemView, OnDetalleListener mOnDetalleListener) {
            super(itemView);
            fecha = itemView.findViewById(R.id.fecha_detalle_sesion);
            horaInicio = itemView.findViewById(R.id.hora_inicio_detalle);
            detalle = itemView.findViewById(R.id.nombre_detalle_sesion);
            this.onDetaleListener = mOnDetalleListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onDetaleListener.onDetalleClick(listaDetalle.get(getAdapterPosition()));
            notifyDataSetChanged();
        }

    }


    void setDetalles(List<DetalleSesion> sesiones){
        listaDetalle=sesiones;
        notifyDataSetChanged();
    }
}
