package com.example.apptea.ui.habilidadCotidiana;

import android.content.Context;
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
import com.example.apptea.utilidades.AdministarSesion;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.HabilidadCotidiana;

public class HabilidadCotidianaAdapter extends RecyclerView.Adapter<HabilidadCotidianaAdapter.HabilidadCotidianaHolder> implements View.OnClickListener {

    private View.OnClickListener listener;
    private OnHabilidadListener mOnHabilidadListener;
    private final LayoutInflater mInflater;
    public boolean isHabilidad = false;
    private List<HabilidadCotidiana> habilidadCotidianaList;
    private List<HabilidadCotidiana> habilidadCotidianaListBusqueda;
    AdministarSesion idioma ;

    private HabilidadCotidianaAdapter.ButtonClicked buttonClicked;

    @Override
    public void onClick(View v) {

    }

    public interface ButtonClicked {
        void deleteClickedHab(HabilidadCotidiana habilidadCotidiana);

        void updateClickedHab(HabilidadCotidiana habilidadCotidiana);
    }

    public void setButtonClicked(HabilidadCotidianaAdapter.ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    class HabilidadCotidianaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView habilidadItemView;
        public Button eliminar;
        public Button editar;
        public ImageView imagen;
        OnHabilidadListener onHabilidadListener;

        public HabilidadCotidianaHolder(View itemView, OnHabilidadListener onHabilidadListener) {
            super(itemView);
            habilidadItemView = itemView.findViewById((R.id.nombre_hab_cotidiana));
            eliminar = itemView.findViewById(R.id.btn_eliminar_hab_cotidiana);
            editar = itemView.findViewById(R.id.btn_editar_hab_cotidiana);
            imagen = itemView.findViewById(R.id.img_hab_cotidiana);
            this.onHabilidadListener = onHabilidadListener;
            itemView.setOnClickListener(this);


            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked.deleteClickedHab(habilidadCotidianaList.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked.updateClickedHab(habilidadCotidianaList.get(getAdapterPosition()));
                }
            });


        }

        @Override
        public void onClick(View v) {
            onHabilidadListener.onHabilidadClick(habilidadCotidianaList.get(getAdapterPosition()));
            notifyDataSetChanged();
        }
    }

    public HabilidadCotidianaAdapter(Context context, OnHabilidadListener onHabilidadListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnHabilidadListener = onHabilidadListener;
        idioma = new AdministarSesion(context);
    }


    @Override
    public HabilidadCotidianaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recyclerview_item_hab_cotidiana, parent, false);

        return new HabilidadCotidianaHolder(itemview, mOnHabilidadListener);
    }

    @Override
    public void onBindViewHolder(HabilidadCotidianaHolder holder, int position) {


        if (habilidadCotidianaList != null) {
            HabilidadCotidiana current = habilidadCotidianaList.get(position);

            PictogramaDAO pictogramaDAO = appDatabase.getDatabase(holder.itemView.getContext()).pictogramaDAO();

            Glide.with(holder.itemView.getContext())
                    .load(ImageConverter.convertirByteArrayAImagen(pictogramaDAO.findbyPictoId(current.getPictograma_id()).getPictograma_imagen()))
                    .thumbnail(0.5f)
                    .into(holder.imagen);

            if(idioma.getIdioma()==1){
                holder.habilidadItemView.setText(current.getHabilidad_cotidiana_nombre());
            }else{
                holder.habilidadItemView.setText(current.getEveryday_skills_name());}

            if (isHabilidad == true || current.isPredeterminado()) {

                holder.editar.setVisibility(View.GONE);
                holder.eliminar.setVisibility(View.GONE);
                holder.setIsRecyclable(false);
            } else {
                holder.setIsRecyclable(false);
            }


        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);

        }

    }

    @Override
    public int getItemCount() {
        if (habilidadCotidianaList != null)
            return habilidadCotidianaList.size();
        else
            return 0;
    }


    public interface OnHabilidadListener {
        void onHabilidadClick(HabilidadCotidiana posicion);
    }

    void setHabiilidad(List<HabilidadCotidiana> habilidadesCotidianas) {
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
