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
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptea.R;
import com.example.apptea.ui.pais.PaisViewModel;

import java.util.List;

import roomsqlite.dao.PaisDao;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.Usuario;
import roomsqlite.repositorios.PaisRepository;


public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {

class UsuarioHolder extends RecyclerView.ViewHolder{

    private final TextView nombreItemView;

    private final TextView correoItemView;
    private final TextView telItemView;
    private final TextView paisItemView;
    private final TextView direccionItemView;

    private UsuarioHolder(View itemView){
        super(itemView);
        nombreItemView = itemView.findViewById((R.id.txt_nombreUsuario));

        telItemView = itemView.findViewById((R.id.txt_telefonoUsuario));
        correoItemView = itemView.findViewById((R.id.txt_correoUsuario));
        paisItemView = itemView.findViewById((R.id.txt_pais));
        direccionItemView = itemView.findViewById((R.id.txt_direccion));

    }
}

    private final LayoutInflater mInflater;
    private List<Usuario> usuarioList;
    private List<Pais> paises;

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
        if (usuarioList != null) {
            Usuario current = usuarioList.get(position);

            holder.nombreItemView.setText("Hola \n"+current.getUsuario_nombre()+" "+current.getUsuario_apellido()+"!");
            holder.correoItemView.setText(current.getCorreo());
            holder.telItemView.setText(String.valueOf(current.getTelefono()));
            holder.paisItemView.setText(String.valueOf(current.getPais_id()));
            holder.direccionItemView.setText(current.getDireccion());
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
