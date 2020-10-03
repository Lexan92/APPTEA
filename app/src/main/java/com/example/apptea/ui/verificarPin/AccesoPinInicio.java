package com.example.apptea.ui.verificarPin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.apptea.R;
import com.example.apptea.ui.usuario.UsuarioViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Usuario;

public class AccesoPinInicio extends Fragment {

    EditText entradaPin;
    Button acceder, cancelar;
    UsuarioViewModel usuarioViewModel;
    LiveData<List<Usuario>> usuario;

    public AccesoPinInicio() {
        //constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acceso_pin_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entradaPin = view.findViewById(R.id.edit_word);
        acceder = view.findViewById(R.id.boton_acceder);
        cancelar = view.findViewById(R.id.boton_cancelar);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);


        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" ");


        acceder.setOnClickListener(v -> {

            usuario = usuarioViewModel.getUsuarioAll();
            usuario.observe(getActivity(), usuarios -> {
                if (entradaPin.getText().toString().isEmpty()) {
                    entradaPin.setError("Campo Vacío, ingresa la contraseña");
                } else if (usuarios.get(0).getContrasenia().equals(entradaPin.getText().toString())) {

                    //Se obtiene el usuario guardado se obtiene la primera fila.
                    UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getContext()).usuarioDao();
                    Usuario usuario = usuarioDao.obtenerUsuario();
                    //se inicializa la sesion
                    AdministarSesion administarSesion = new AdministarSesion(getContext());
                    administarSesion.guardarSesion(usuario);
                    Navigation.findNavController(getView()).navigate(R.id.action_accesoPinInicio_to_nav_home);
                } else {
                    entradaPin.setError("Contraseña Incorrecta");
                }
            });
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(R.id.action_accesoPinInicio_to_listadoInicioSesion);

            }
        });
    }

}