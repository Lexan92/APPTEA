/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.frases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.List;

import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaPictograma;

public class CatPictoFrasesAdapter extends RecyclerView.Adapter<CatPictoFrasesAdapter.FrasesHolder> {
    private List<CategoriaPictograma> categoriaPictogramaList;
    private ClickedCatPicto clickedCatPicto;
    AdministarSesion idioma ;

    public interface ClickedCatPicto{
        void CategoriaClicked(CategoriaPictograma categoriaPictograma);

    }

    public void setClickedCatPicto (ClickedCatPicto clickedCatPicto){
        this.clickedCatPicto=clickedCatPicto;
    }

    public class FrasesHolder extends RecyclerView.ViewHolder{
        private final TextView nombreCategoria;
        public ImageView imagen;

        private FrasesHolder(View itemView){
            super(itemView);
            nombreCategoria = itemView.findViewById(R.id.nombre_pictograma);
            imagen = itemView.findViewById(R.id.img_pictograma);

        }

    }

    public CatPictoFrasesAdapter(FragmentActivity activity){}

    @Override
    public FrasesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_pic_frases,parent, false);
        idioma = new AdministarSesion(parent.getContext());
        return new FrasesHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CatPictoFrasesAdapter.FrasesHolder holder, int position) {
        if (categoriaPictogramaList != null && position < categoriaPictogramaList.size()) {
            PictogramaDAO pictogramaDAO = appDatabase.getDatabase(holder.itemView.getContext()).pictogramaDAO();

            CategoriaPictograma categoriaPictograma = categoriaPictogramaList.get(position);
            if (categoriaPictograma.getPictograma_id() == 0) {
                if(idioma.getIdioma()==1){
                    holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
                }else{
                    holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_name());}
                holder.setIsRecyclable(false);
            }else {
                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(pictogramaDAO.findbyPictoId(categoriaPictograma.getPictograma_id()).getPictograma_imagen()))
                        .thumbnail(0.5f)
                        .into(holder.imagen);
                if(idioma.getIdioma()==1){
                    holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
                }else{
                    holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_name());}
                holder.setIsRecyclable(false);
            }
            holder.setIsRecyclable(false);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedCatPicto.CategoriaClicked(categoriaPictograma);
                }
            });


        } else {
            // Covers the case of data not being ready yet.
            holder.nombreCategoria.setText("No existe ninguna categoria de pictogramas");
        }
    }

    public void setCategoria(List<CategoriaPictograma> categorias){
        categoriaPictogramaList = categorias;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(categoriaPictogramaList != null)
            return categoriaPictogramaList.size();
        else
            return 0;
    }

    @Override
    public void onViewRecycled(CatPictoFrasesAdapter.FrasesHolder holder) {
        super.onViewRecycled(holder);
        holder.nombreCategoria.setText(null);
        holder.setIsRecyclable(false);
    }
}
