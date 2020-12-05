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
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.List;

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
        public TextView nombreCategoria;

        private FrasesHolder(View itemView){
            super(itemView);
            nombreCategoria = itemView.findViewById(R.id.txt_categoria_pictograma);

        }

    }

    public CatPictoFrasesAdapter(FragmentActivity activity){}

    @Override
    public FrasesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_frases,parent, false);
        idioma = new AdministarSesion(parent.getContext());
        return new FrasesHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CatPictoFrasesAdapter.FrasesHolder holder, int position) {
        if (categoriaPictogramaList != null && position < categoriaPictogramaList.size()) {

            CategoriaPictograma categoriaPictograma = categoriaPictogramaList.get(position);

            System.out.println("Idioma es  " + idioma.getIdioma());
            if(idioma.getIdioma()==1){
                holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
            }else{
                holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_name());}

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
