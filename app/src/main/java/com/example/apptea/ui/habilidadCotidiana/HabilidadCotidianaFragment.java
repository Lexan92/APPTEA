/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.habilidadCotidiana;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;
import roomsqlite.repositorios.HabilidadCotidianaRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class HabilidadCotidianaFragment extends Fragment {

    private HabilidadCotidianaRepository habilidadCotidianaRepository;
    private LiveData<List<HabilidadCotidiana>> HabCotidianaAll;
    RecyclerView recyclerView;
    private HabilidadCotidianaAdapter adapter= null;
    private HabilidadCotidianaViewModel habilidadCotidianaViewModel;
    private CategoriaHabCotidiana categoriaHabCotidiana = null;
    public static final int NEW_HAB_REQUEST_CODE = 1;
    public static final int HAB_UPDATE_REQUEST_CODE = 2;


    public HabilidadCotidianaFragment(){
        //constructor vacio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_detalle_habilidad_cotidiana, container, false);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_hab_cotidiana);
        this.adapter = new HabilidadCotidianaAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        habilidadCotidianaViewModel = new ViewModelProvider(getActivity()).get(HabilidadCotidianaViewModel.class);

        Bundle objetoHabilidad = getArguments();
        if(objetoHabilidad != null){
           categoriaHabCotidiana = (CategoriaHabCotidiana) objetoHabilidad.getSerializable("elementos");
            habilidadCotidianaViewModel.getHabilidadCotidianaAll(categoriaHabCotidiana.getCat_hab_cotidiana_id()).observe(getActivity(), new Observer<List<HabilidadCotidiana>>() {
                @Override
                public void onChanged(List<HabilidadCotidiana> habilidadCotidianas) {
                    adapter.setHabiilidad(habilidadCotidianas);
                }
            });
        }

        //Setteando Toolbar para categorias
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categoria: " + categoriaHabCotidiana.getCat_hab_cotidiana_nombre());

        return vista;
    }


}
