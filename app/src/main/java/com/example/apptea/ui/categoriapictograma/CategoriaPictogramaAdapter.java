

package com.example.apptea.ui.categoriapictograma;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;


import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaJuegoViewHolder;
import com.example.apptea.ui.personaTea.PersonaTeaAdapter;

import java.util.ArrayList;
import java.util.List;



import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

public class CategoriaPictogramaAdapter extends RecyclerView.Adapter<CategoriaPictogramaViewHolder> implements View.OnClickListener, Filterable {

    private List<CategoriaPictograma> categoriaPictogramaList;
    private final LayoutInflater mInflater;
    private  View.OnClickListener listener;
    public boolean isVocabulary;
    private List<CategoriaPictograma> categoriaPictogramaListFull;
    private ArrayList<Pictograma> pictogramas;
    private CategoriaPictogramaAdapter.ButtonClickedCatPicto buttonClickedCatPicto;


    public interface ButtonClickedCatPicto{
        void deleteClickedCatPicto(CategoriaPictograma categoriaPictograma, View v);
        void updateClickedCatPicto(CategoriaPictograma categoriaPictograma, View v);
        void itemClickedCatPicto(CategoriaPictograma categoriaPictograma, View v);
    }

    public void setButtonClickedCatPicto (ButtonClickedCatPicto buttonClickedCatPicto){
        this.buttonClickedCatPicto= buttonClickedCatPicto;
    }


    public CategoriaPictogramaAdapter(Context context) {

        mInflater = LayoutInflater.from(context);

    }



    @Override
    public CategoriaPictogramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = mInflater.inflate(R.layout.fragment_item_categoria_pictograma,parent, false);

        //layoutView.setOnClickListener(this);
        return new CategoriaPictogramaViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaPictogramaViewHolder holder, int position) {
        if (categoriaPictogramaList != null && position < categoriaPictogramaList.size()) {

            CategoriaPictograma categoriaPictograma = categoriaPictogramaList.get(position);
            if(isVocabulary==true){
                holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
                holder.cancelar.setVisibility(View.INVISIBLE);
                holder.editar.setVisibility(View.INVISIBLE);
                holder.setIsRecyclable(false);


            }else{
                if (categoriaPictograma.isPredeterminado()==true){
                    holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
                    holder.editar.setVisibility(View.GONE);
                    holder.cancelar.setVisibility(View.GONE);
                    holder.setIsRecyclable(false);
                }
                else {
                holder.nombreCategoria.setText(categoriaPictograma.getCat_pictograma_nombre());
                holder.setIsRecyclable(false);
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickedCatPicto.itemClickedCatPicto(categoriaPictograma,v);
                }
            });

            holder.editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickedCatPicto.updateClickedCatPicto(categoriaPictograma, v);
                }
            });

            holder.cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickedCatPicto.deleteClickedCatPicto(categoriaPictograma, v);
                }
            });


        } else {
            // Covers the case of data not being ready yet.
            holder.nombreCategoria.setText("No existe ninguna categoria de pictogramas");
        }
    }

   public void setCategoria(List<CategoriaPictograma> categorias){
        categoriaPictogramaList = categorias;
       categoriaPictogramaListFull= new ArrayList<>(categoriaPictogramaList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(categoriaPictogramaList != null)
            return categoriaPictogramaList.size();
       else
        return 0;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onViewRecycled(@NonNull CategoriaPictogramaViewHolder holder) {
        super.onViewRecycled(holder);
        holder.nombreCategoria.setText(null);
        holder.setIsRecyclable(false);
    }





    ////////////////
    @Override
    public Filter getFilter() {
        return categoriaPictogramaFilter;
    }

    private Filter categoriaPictogramaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CategoriaPictograma> listaFiltrada = new ArrayList<>();

            if (constraint != null||constraint.length() != 0){

                String patronDeFiltrado = constraint.toString().toLowerCase().trim();

                for(CategoriaPictograma item:categoriaPictogramaListFull){
                    if (item.getCat_pictograma_nombre().toLowerCase().contains(patronDeFiltrado)){

                        listaFiltrada.add(item);
                    }
                }
            } else {
                listaFiltrada= new ArrayList<>(categoriaPictogramaList);
                notifyDataSetChanged();
            }


            FilterResults results = new FilterResults();
            results.values = listaFiltrada;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoriaPictogramaList.clear();
            categoriaPictogramaList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

/////////////////////

}
