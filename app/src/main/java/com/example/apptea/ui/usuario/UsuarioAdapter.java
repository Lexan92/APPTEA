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
import java.util.List;
import roomsqlite.entidades.Usuario;



public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {

class UsuarioHolder extends RecyclerView.ViewHolder{

    private final TextView nombreItemView;
    private final TextView correoItemView;
    private final TextView telItemView;

    private UsuarioHolder(View itemView){
        super(itemView);
        nombreItemView = itemView.findViewById((R.id.txt_nombreUsuario));
        correoItemView = itemView.findViewById((R.id.txt_correoUsuario));
        telItemView = itemView.findViewById((R.id.txt_telefonoUsuario));

    }
}

    private final LayoutInflater mInflater;
    private List<Usuario> usuarioList;

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
            holder.nombreItemView.setText(current.getUsuario_nombre());
            holder.correoItemView.setText(current.getCorreo());
            holder.telItemView.setText(String.valueOf(current.getTelefono()));
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
