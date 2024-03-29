


package com.teakids.apptea.ui.habilidadCotidiana;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teakids.apptea.R;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.TTSManagerSecuencia;


import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class PictoSecuenciaAdapter extends RecyclerView.Adapter<PictoSecuenciaAdapter.PictoSecuenciaHolder> {
    private List<Pictograma> pictoFraseList;
    private int posicionMarcada = 0;
    AdministarSesion idioma ;

    public class PictoSecuenciaHolder extends RecyclerView.ViewHolder{
        private final TextView nombrePictograma;
        public ImageView imagen;
        public ImageView flecha;
        public ImageView check;



        private PictoSecuenciaHolder(View itemView){
            super(itemView);
            nombrePictograma = itemView.findViewById(R.id.nombre_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);
            flecha = itemView.findViewById(R.id.img_flecha);
            check = itemView.findViewById(R.id.img_check);
        }
    }
    public PictoSecuenciaAdapter(ArrayList<Pictograma> pictoFraseList){
        this.pictoFraseList=pictoFraseList;
    }

    public PictoSecuenciaAdapter(FragmentActivity activity){}

    @Override
    public PictoSecuenciaAdapter.PictoSecuenciaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_pic_secuencia,parent, false);
        idioma = new AdministarSesion(parent.getContext());
        return new PictoSecuenciaAdapter.PictoSecuenciaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PictoSecuenciaAdapter.PictoSecuenciaHolder holder, int position) {
        final int pos = position;

        if (position == pictoFraseList.size() - 1) {
            holder.flecha.setVisibility(View.GONE);
        }
        if (pictoFraseList != null) {
            Pictograma current = pictoFraseList.get(position);

            Glide.with(holder.itemView.getContext())
                    .load(ImageConverter.convertirByteArrayAImagen(current.getPictograma_imagen()))
                    .thumbnail(0.5f)
                    .into(holder.imagen);

            System.out.println("Idioma es  " + idioma.getIdioma());
            if(idioma.getIdioma()==1){
                holder.nombrePictograma.setText(current.getPictograma_nombre());
            }else{
                holder.nombrePictograma.setText(current.getPictograma_name());}


            holder.setIsRecyclable(false);

        } else {
            // Covers the case of data not being ready yet.
            Glide.with(holder.itemView.getContext()).clear(holder.imagen);
            holder.nombrePictograma.setText(holder.itemView.getContext().getString(R.string.noExistePicto));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //holder.check.setVisibility(View.VISIBLE);
                posicionMarcada = pos;
                TTSManagerSecuencia.pictogramaSeleccion = pictoFraseList.get(pos);
                TTSManagerSecuencia.pictogramaIdSeleccion= pos+1;
                notifyDataSetChanged();
                return true;
            }
        });

        if(posicionMarcada == position){
            holder.check.setVisibility(View.VISIBLE);
        }else{
            holder.check.setVisibility(View.GONE);
        }

    }

    void setPictograma(List<Pictograma> pic){
        pictoFraseList = pic;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(pictoFraseList != null)
            return pictoFraseList.size();
        else
            return 0;
    }

    @Override
    public void onViewRecycled(@NonNull PictoSecuenciaAdapter.PictoSecuenciaHolder holder) {
        super.onViewRecycled(holder);
        holder.imagen.setImageBitmap(null);
        holder.imagen.setImageDrawable(null);
        holder.nombrePictograma.setText(null);
        holder.setIsRecyclable(false);
    }
}
