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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

public class CategoriaHabCotidianaAdapter extends RecyclerView.Adapter<CategoriaHabCotidianaAdapter.CategoriaHabCotidianaHolder> implements View.OnClickListener, Filterable {

    private  View.OnClickListener listener;
    private final LayoutInflater mInflater;
    private String mSearchTerm;
    private List<CategoriaHabCotidiana> categoriaHabCotidianaList;
    private List<CategoriaHabCotidiana> categoriaHabCotidianaListFull;
    private CategoriaHabCotidianaAdapter.ButtonClicked buttonClicked;
    OnCategoriaHabiCotiListener onCategoriaHabiCotiListener;
    public boolean isVistaNiño = false;
    AdministarSesion idioma ;

    public interface ButtonClicked{
        void deleteClickedCatHab(CategoriaHabCotidiana categoriaHabCotidiana);
        void updateClickedCatHab(CategoriaHabCotidiana categoriaHabCotidiana);
        void itemLongClicked(CategoriaHabCotidiana categoriaHabCotidiana);
    }

    public void setButtonClicked(CategoriaHabCotidianaAdapter.ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }


    class CategoriaHabCotidianaHolder extends RecyclerView.ViewHolder {
        private final TextView categoriaItemView;
        private final Button btnEdit, btnDelete;
        private final ImageView imagen;



        private CategoriaHabCotidianaHolder(View itemView){
            super(itemView);
            categoriaItemView = itemView.findViewById((R.id.txt_categoria_hab_cotidiana));
            btnEdit = itemView.findViewById(R.id.btn_editar_cat_hab);
            btnDelete = itemView.findViewById(R.id.btn_delete_cat_hab);
            imagen = itemView.findViewById(R.id.img_cat_hab);




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
        idioma = new AdministarSesion(context);
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
            PictogramaDAO pictogramaDAO = appDatabase.getDatabase(holder.itemView.getContext()).pictogramaDAO();
            CategoriaHabCotidiana current = categoriaHabCotidianaList.get(position);

            if(current.getPictograma_id() == 0){
                if(idioma.getIdioma()==1){
                    holder.categoriaItemView.setText(current.getCat_hab_cotidiana_nombre());
                }else{
                    holder.categoriaItemView.setText(current.getCat_hab_cotidiana_name());}
            }else {
                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(pictogramaDAO.findbyPictoId(current.getPictograma_id()).getPictograma_imagen()))
                        .thumbnail(0.5f)
                        .into(holder.imagen);
                System.out.println("Idioma es  " + idioma.getIdioma());
                if (idioma.getIdioma() == 1) {
                    holder.categoriaItemView.setText(current.getCat_hab_cotidiana_nombre());
                } else {
                    holder.categoriaItemView.setText(current.getCat_hab_cotidiana_name());
                }
            }

            if (isVistaNiño == true || current.isCat_predeterminado()) {
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.setIsRecyclable(false);
            } else {
                holder.setIsRecyclable(false);
                holder.imagen.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        buttonClicked.itemLongClicked(current);
                        return true;
                    }
                });
            }
        }else{
            holder.categoriaItemView.setText(holder.itemView.getContext().getString(R.string.noExisteCatHabili));
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


////////////////
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

/////////////////////

}
