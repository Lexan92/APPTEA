package com.teakids.apptea.ui.juegoSeleccion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.teakids.apptea.R;
import java.util.List;
import roomsqlite.entidades.DetalleResultado;


public class DetalleResultadoAdapter extends RecyclerView.Adapter<DetalleResultadoAdapter.DetalleResultadoHolder> {

    private List<DetalleResultado> detalleResultadoList;


    public class DetalleResultadoHolder extends RecyclerView.ViewHolder{
        private final TextView pregunta;
        private final TextView fallos;




        private DetalleResultadoHolder(View itemView){
            super(itemView);
            pregunta = itemView.findViewById((R.id.item_number));
            fallos= itemView.findViewById((R.id.content));

        }
    }



    public DetalleResultadoAdapter(){}

    @NonNull
    @Override
    public DetalleResultadoAdapter.DetalleResultadoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item,parent, false);
        return new DetalleResultadoAdapter.DetalleResultadoHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleResultadoAdapter.DetalleResultadoHolder holder, int position) {
        if (detalleResultadoList != null) {
            DetalleResultado detalleResultado = detalleResultadoList.get(position);
            holder.pregunta.setText(detalleResultado.getNombre_pregunta());
            holder.fallos.setText(String.valueOf(detalleResultado.getCantidad_fallos()));


        } else {
            // Covers the case of data not being ready yet.
            holder.pregunta.setText("No se han ingresado terapeutas a la lista");
        }

    }

    public  void setDetalleResultados(List<DetalleResultado> detalleResultados){
        detalleResultadoList = detalleResultados;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(detalleResultadoList != null)
            return detalleResultadoList.size();
        else
            return 0;
    }


}
