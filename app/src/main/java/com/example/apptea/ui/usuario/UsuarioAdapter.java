/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.usuario;

//Import para UsuarioAdapter
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaAdapter;
import com.example.apptea.ui.pais.PaisViewModel;

import java.util.List;

import roomsqlite.dao.PaisDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.Usuario;
import roomsqlite.repositorios.PaisRepository;


public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {


    private UsuarioAdapter.ButtonClicked buttonClicked;

    public interface ButtonClicked{
       void updateClickedUsuario(Usuario usuario);
    }

    public void setButtonClicked(UsuarioAdapter.ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    class UsuarioHolder extends RecyclerView.ViewHolder{


    private final TextView nombreItemView;

    private final TextView correoItemView;
    private final TextView telItemView;
    private final TextView paisItemView;
    private final Button btnEdit;
    /*private final TextView direccionItemView;*/

    private UsuarioHolder(View itemView){
        super(itemView);
        nombreItemView = itemView.findViewById((R.id.txt_nombreUsuario));
        telItemView = itemView.findViewById((R.id.txt_telefonoUsuario));
        correoItemView = itemView.findViewById((R.id.txt_correoUsuario));
        paisItemView = itemView.findViewById((R.id.txt_pais));
        btnEdit = itemView.findViewById(R.id.btnEdit);
        /*direccionItemView = itemView.findViewById((R.id.txt_direccion));*/


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked.updateClickedUsuario(usuarioList.get(getAdapterPosition()));
            }
        });


    }
}

    private final LayoutInflater mInflater;
    private List<Usuario> usuarioList;

    PaisRepository paisRepository;


   UsuarioAdapter(Context context){
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public UsuarioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recyclerview_item_mi_perfil,parent, false);

        return new UsuarioHolder(itemview);
    }

    @Override
    public void onBindViewHolder(UsuarioHolder holder, int position) {

       //Se obtiene una instancia del DAO (aca no se implementa Repository genera error)
        PaisDao paisDao = (PaisDao) appDatabase.getDatabase(mInflater.getContext()).paisDao();

        if (usuarioList != null) {
            Usuario current = usuarioList.get(position);
            int idP= current.getPais_id();
            holder.nombreItemView.setText("Hola \n"+current.getUsuario_nombre()+" "+current.getUsuario_apellido()+"!");
            holder.correoItemView.setText(current.getCorreo());
            holder.telItemView.setText(String.valueOf(current.getTelefono()));
            holder.paisItemView.setText(paisDao.findPaisById(current.getPais_id()).getPais_nombre());
           /*holder.direccionItemView.setText(current.getDireccion());*/
        } else {
            // Covers the case of data not being ready yet.
            holder.nombreItemView.setText("No existe ninguna categoria para habilidades cotidianas");//mensaje aunq no se debera mostrar ya que es campo obligatorio
            holder.correoItemView.setText("Aún no has agregado tu correo"); //mensaje aunq no se debera mostrar ya que es campo obligatorio
            holder.telItemView.setText("Aún no has agregado el telefono");
        }
    }

    void setUsuario(List<Usuario> usuarios){
        usuarioList = usuarios;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(usuarioList != null)
            return usuarioList.size();
        else
            return 0;
    }

}
