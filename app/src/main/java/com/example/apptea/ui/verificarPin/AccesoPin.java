package com.example.apptea.ui.verificarPin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaJuegoFragment;
import com.example.apptea.ui.usuario.UsuarioViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.Objects;

import roomsqlite.entidades.Usuario;


public class AccesoPin extends Fragment {

    EditText entradaPin;
    Button acceder, cancelar;
    UsuarioViewModel usuarioViewModel;
    LiveData<List<Usuario>> usuario;


    public AccesoPin() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_acceso_pin, container, false);
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
                    usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);
                    //final boolean bandera = true;
                    //Bundle bundle = new Bundle();
                    //bundle.putBoolean("bandera", bandera);
                    Navigation.findNavController(getView()).navigate(R.id.accesoPin_a_CategoriaJuego);
                } else {
                    entradaPin.setError("Contraseña Incorrecta");
                }
            });
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_accesoPin_to_nav_home);
            }
        });
    }


}