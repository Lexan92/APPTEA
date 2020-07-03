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

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaAdapter;
import com.example.apptea.ui.categoriahabilidadcotidiana.EditCategoriaHab;
import com.example.apptea.ui.categoriahabilidadcotidiana.NuevaCategoriaDialog;
import com.example.apptea.ui.pais.PaisViewModel;
import com.example.apptea.ui.personaTea.ActualizarPersonaTeaActivity;
import com.example.apptea.ui.personaTea.NuevaPersonaTea;
import com.example.apptea.utilidades.EnviarCorreo;
import com.example.apptea.utilidades.GenerarNumAleatorio;

import org.w3c.dom.Attr;

import java.util.List;

import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Usuario;
import roomsqlite.repositorios.UsuarioRepository;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {

    public static final int USUARIO_UPDATE_REQUEST_CODE = 2;
    private UsuarioRepository usuarioRepository;
    private LiveData<List<Usuario>> usuarioAll;
    RecyclerView recyclerView;
    private UsuarioViewModel usuarioViewModel;
    private ProgressBar progressBar;

    public UsuarioFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_mi_perfil, container, false);
        progressBar = (ProgressBar) vista.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_mi_perfil);
        final UsuarioAdapter adapter = new UsuarioAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        progressBar.setVisibility(View.INVISIBLE);
        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioAll().observe(getActivity(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(@Nullable final List<Usuario> usuarioList) {
                // Update the cached copy of the words in the adapter.
                adapter.setUsuario(usuarioList);
            }

        });


        //CardView para enviar correo y abrir nueva modal de captura de codigo
        CardView cardCorreo = vista.findViewById(R.id.cambiarContra);
        cardCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int codigo;
                progressBar.setVisibility(View.VISIBLE);



                //Se obtiene el usuario guardado se obtiene la primera fila.
                UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getContext()).usuarioDao();
                Usuario usuario = usuarioDao.obtenerUsuario();

                //Se genera el codigo aleatorio y se guarda en variable int codigo
                codigo = GenerarNumAleatorio.getNumeroAleatorio();

                //Se inicializa el metodo para enviar correo
                EnviarCorreo enviarCorreo = new EnviarCorreo();
                enviarCorreo.Enviar(codigo,usuario.getCorreo());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),"Correo Enviado con Exito",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(),ValidarCodigo.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);

            }

        });

        //CardView que llevara a la actividad de Acerca de..
        CardView cardAcercaDe = vista.findViewById(R.id.acerca_de);
        cardAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Acerca de TEAyudo Jugando",Toast.LENGTH_LONG).show();
            }
        });


        // METODOS PARA ACTUALIZAR Y ELIMINAR
        adapter.setButtonClicked(new UsuarioAdapter.ButtonClicked() {
            @Override
            public void updateClickedUsuario(Usuario usuario) {
                System.out.println("en el fragment"+usuario.getUsuario_id());
                Intent intentUpdate = new Intent(getActivity(), EditUsuario.class);
                intentUpdate.putExtra(EditUsuario.EXTRA_ID_USUARIO_UPDATE, usuario.getUsuario_id());
                intentUpdate.putExtra(EditUsuario.EXTRA_NOMBRE_USUARIO_UPDATE, usuario.getUsuario_nombre());
                intentUpdate.putExtra(EditUsuario.EXTRA_APELLIDO_USUARIO_UPDATE, usuario.getUsuario_apellido());
                intentUpdate.putExtra(EditUsuario.EXTRA_PAIS_USUARIO_UPDATE, usuario.getPais_id());
                intentUpdate.putExtra(EditUsuario.EXTRA_TELEFONO_USUARIO_UPDATE, usuario.getTelefono());
                intentUpdate.putExtra(EditUsuario.EXTRA_CORREO_USUARIO_UPDATE, usuario.getCorreo());
                intentUpdate.putExtra(EditUsuario. EXTRA_CONTRASEÑA_UPDATE, usuario.getContrasenia());
                startActivityForResult(intentUpdate,USUARIO_UPDATE_REQUEST_CODE);
            }
            });



        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       if (requestCode == USUARIO_UPDATE_REQUEST_CODE && resultCode == RESULT_OK){
            Usuario usuario = (Usuario) data.getSerializableExtra(EditUsuario.EXTRA_USUARIO_UPDATE);
            usuarioViewModel.update(usuario);
        } else {
            Toast.makeText(getActivity(),"No completo todos los campos",Toast.LENGTH_LONG).show();
        }
    }
}