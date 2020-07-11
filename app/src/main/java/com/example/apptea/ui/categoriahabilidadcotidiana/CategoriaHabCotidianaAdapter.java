/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.categoriahabilidadcotidiana;

import android.content.Context;
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
import com.example.apptea.ui.pictograma.PictogramaAdapter;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Pictograma;

public class CategoriaHabCotidianaAdapter extends RecyclerView.Adapter<CategoriaHabCotidianaAdapter.CategoriaHabCotidianaHolder> implements View.OnClickListener, Filterable {

    private  View.OnClickListener listener;
    private final LayoutInflater mInflater;
    private String mSearchTerm;
    private List<CategoriaHabCotidiana> categoriaHabCotidianaList;
    private List<CategoriaHabCotidiana> categoriaHabCotidianaListFull;
    private CategoriaHabCotidianaAdapter.ButtonClicked buttonClicked;
    OnCategoriaHabiCotiListener onCategoriaHabiCotiListener;





    public interface ButtonClicked{
        void deleteClickedCatHab(CategoriaHabCotidiana categoriaHabCotidiana);
        void updateClickedCatHab(CategoriaHabCotidiana categoriaHabCotidiana);
    }

    public void setButtonClicked(CategoriaHabCotidianaAdapter.ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }


    class CategoriaHabCotidianaHolder extends RecyclerView.ViewHolder {
        private final TextView categoriaItemView;
        private final Button btnEdit, btnDelete;



        private CategoriaHabCotidianaHolder(View itemView){
            super(itemView);
            categoriaItemView = itemView.findViewById((R.id.txt_categoria_hab_cotidiana));
            btnEdit = itemView.findViewById(R.id.btn_editar_cat_hab);
            btnDelete = itemView.findViewById(R.id.btn_delete_cat_hab);




            btnDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    buttonClicked.deleteClickedCatHab(categoriaHabCotidianaList.get(getAdapterPosition()));
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked.updateClickedCatHab(categoriaHabCotidianaList.get(getAdapterPosition()));
                }
            });
        }
    }




    CategoriaHabCotidianaAdapter(Context context){
        mInflater = LayoutInflater.from(context);

    }



    @Override
    public CategoriaHabCotidianaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recyclerview_item_categoria_hab_cotidiana,parent, false);

        itemview.setOnClickListener(this);

        return new CategoriaHabCotidianaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CategoriaHabCotidianaAdapter.CategoriaHabCotidianaHolder holder, int position) {
        int mposition = position;
        if (categoriaHabCotidianaList != null && position < categoriaHabCotidianaList.size()) {
            CategoriaHabCotidiana current = categoriaHabCotidianaList.get(position);
            if (current.isCat_predeterminado() == true) {
                holder.categoriaItemView.setText(current.getCat_hab_cotidiana_nombre());
                holder.btnDelete.setVisibility(View.GONE);
                holder.setIsRecyclable(false);

            } else {
                holder.categoriaItemView.setText(current.getCat_hab_cotidiana_nombre());
                holder.setIsRecyclable(false);

            }
        }else{
            holder.categoriaItemView.setText("No existe ninguna categoria para habilidades cotidianas");
        }
    }

    void setCategoria(List<CategoriaHabCotidiana> categorias){

        categoriaHabCotidianaList = categorias;
        categoriaHabCotidianaListFull= new ArrayList<>(categoriaHabCotidianaList);
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        if(categoriaHabCotidianaList != null)
            return categoriaHabCotidianaList.size();
       else
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onViewRecycled(@NonNull CategoriaHabCotidianaHolder holder) {
        super.onViewRecycled(holder);
        holder.categoriaItemView.setText(null);
        holder.setIsRecyclable(false);
    }



    @Override
    public Filter getFilter() {
        return categoricalHabitCarotidFilter;
    }

    private Filter categoricalHabitCarotidFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CategoriaHabCotidiana> listaFiltrada = new ArrayList<>();

         if (constraint != null||constraint.length() != 0){

                String patronDeFiltrado = constraint.toString().toLowerCase().trim();

                for(CategoriaHabCotidiana item:categoriaHabCotidianaListFull){
                    if (item.getCat_hab_cotidiana_nombre().toLowerCase().contains(patronDeFiltrado)){

                        listaFiltrada.add(item);
                    }
                }
            }


            FilterResults results = new FilterResults();
            results.values = listaFiltrada;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoriaHabCotidianaList.clear();
            categoriaHabCotidianaList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public void setTextQueryChanged(String newText) {
        mSearchTerm = newText;
    }


    public interface OnCategoriaHabiCotiListener{
        void onCategorHabCotiClick(CategoriaHabCotidiana posicion);
    }



}
