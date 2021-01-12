package com.example.apptea.ui.sesion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.utilidades.UtilidadFecha;

import java.util.List;

import roomsqlite.entidades.DetalleSesion;
import roomsqlite.entidades.Sesion;


public class SesionAdapter extends RecyclerView.Adapter<SesionAdapter.SesionHolder> implements View.OnClickListener {


    private List<Sesion> listaSesiones;
    private OnSesionListener mOnSesionListener;
    private LayoutInflater mInflater;
    private SesionAdapter.ButtonCliked buttonCliked;


    public SesionAdapter(Context context, OnSesionListener onSesionListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnSesionListener = onSesionListener;

    }


    public interface ButtonCliked {
        void comentarioCliked(Sesion sesion);
    }


    @Override
    public SesionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = mInflater.inflate(R.layout.fragment_sesion_adapter, parent, false);


        return new SesionHolder(itemview, mOnSesionListener);
    }

    @Override
    public void onBindViewHolder(SesionHolder holder, int position) {

        if (listaSesiones != null) {
            Sesion sesionActual = listaSesiones.get(position);
            holder.fecha.setText(UtilidadFecha.obtenerFecha(sesionActual.getInicio_sesion()));
            holder.horaInicio.setText(UtilidadFecha.obtenerHora(sesionActual.getInicio_sesion()));
            holder.horaFin.setText(UtilidadFecha.obtenerHora(sesionActual.getFin_sesion()));
            holder.setIsRecyclable(false);
        }
    }

    @Override
    public int getItemCount() {
        if (listaSesiones != null)
            return listaSesiones.size();
        else
            return 0;
    }


    @Override
    public void onClick(View v) {

    }

    public interface OnSesionListener {
        void onSesionClick(Sesion posicion);
    }

    public void setButtonClicked(SesionAdapter.ButtonCliked buttonClicked) {
        this.buttonCliked = buttonClicked;
    }

    class SesionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView fecha;
        private final TextView horaInicio;
        private final TextView horaFin;
        private Button verComentario;
        OnSesionListener onSesionListener;

        public SesionHolder(View itemView, OnSesionListener onSesionListener) {
            super(itemView);

            fecha = itemView.findViewById(R.id.fecha_sesion);
            horaInicio = itemView.findViewById(R.id.hora_inicio);
            horaFin = itemView.findViewById(R.id.hora_fin);
            this.onSesionListener = onSesionListener;
            itemView.setOnClickListener(this);
            verComentario = itemView.findViewById(R.id.btn_editar_sesion);

            verComentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonCliked.comentarioCliked(listaSesiones.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onClick(View v) {
            onSesionListener.onSesionClick(listaSesiones.get(getAdapterPosition()));
            notifyDataSetChanged();
        }


    }

    void setSesiones(List<Sesion> sesiones) {
        listaSesiones = sesiones;
        notifyDataSetChanged();

    }
}