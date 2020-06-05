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

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.Usuario;
import roomsqlite.repositorios.UsuarioRepository;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {

    private UsuarioRepository usuarioRepository;
    private LiveData<List<Usuario>> usuarioAll;
    RecyclerView recyclerView;
    private UsuarioViewModel usuarioViewModel;

    public UsuarioFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_mi_perfil);
        final UsuarioAdapter adapter = new UsuarioAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioAll().observe(getActivity(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(@Nullable final List<Usuario> usuarioList) {
                // Update the cached copy of the words in the adapter.
                adapter.setUsuario(usuarioList);
            }

        });


        return vista;
    }
}