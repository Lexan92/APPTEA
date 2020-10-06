package com.example.apptea.ui.terapeuta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptea.R;
import java.util.List;
import roomsqlite.entidades.Terapeuta;

public class TerapeutaAdapter extends RecyclerView.Adapter<TerapeutaAdapter.TerapeutaHolder> {


    private List<Terapeuta> terapeutaList;
    private TerapeutaAdapter.ButtonClicked buttonClicked;

    public interface ButtonClicked{
        void deleteClickedTerapeuta(Terapeuta terapeuta);
        void updateClickedTerapeuta(Terapeuta terapeuta);
    }

    public void setButtonClicked(TerapeutaAdapter.ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public class TerapeutaHolder extends RecyclerView.ViewHolder{
        private final TextView nombreTerapeuta;
        private final TextView correoTerapeuta;
        private final TextView telefonoTerapeuta;
        public Button eliminar;
        public Button editar;


        private TerapeutaHolder(View itemView){
            super(itemView);
            nombreTerapeuta = itemView.findViewById((R.id.nombre_terapeuta));
            correoTerapeuta= itemView.findViewById((R.id.correo_terapeuta));
            telefonoTerapeuta = itemView.findViewById((R.id.telefono_terapeuta));
            eliminar= itemView.findViewById(R.id.btn_eliminar_terapeuta);
            editar = itemView.findViewById(R.id.btn_editar_terapeuta);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked.deleteClickedTerapeuta(terapeutaList.get(getAdapterPosition()));
                }
            });

            editar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    buttonClicked.updateClickedTerapeuta(terapeutaList.get(getAdapterPosition()));
                }
            });


        }
    }



    public TerapeutaAdapter(){}

    @NonNull
    @Override
    public TerapeutaAdapter.TerapeutaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_terapeuta,parent, false);
        return new TerapeutaAdapter.TerapeutaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull TerapeutaAdapter.TerapeutaHolder holder, int position) {
        if (terapeutaList != null) {
            Terapeuta terapeuta = terapeutaList.get(position);
            holder.nombreTerapeuta.setText(terapeuta.getTerapeuta_nombre());
            holder.correoTerapeuta.setText(terapeuta.getTerapeuta_correo());
            holder.telefonoTerapeuta.setText(terapeuta.getTerapeuta_telefono());

        } else {
            // Covers the case of data not being ready yet.
            holder.nombreTerapeuta.setText("No se han ingresado terapeutas a la lista");
        }

    }

    void setTerapeutas(List<Terapeuta> terapeutas){
        terapeutaList = terapeutas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(terapeutaList != null)
            return terapeutaList.size();
        else
            return 0;
    }
}
