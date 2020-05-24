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

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.repositorios.CategoriaHabCotidianaRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaHabCotidianaFragment extends Fragment {

    private CategoriaHabCotidianaRepository categoriaHabCotidianaRepository;
    private LiveData<List<CategoriaHabCotidiana>> categoriaHabCotidianaAll;

    public CategoriaHabCotidianaFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_habilidad, container, false);




    }

/*
    public CategoriaHabCotidianaFragment(Application application) {

        categoriaHabCotidianaRepository = new CategoriaHabCotidianaRepository(application);
        categoriaHabCotidianaAll = categoriaHabCotidianaRepository.getTodasCategoriaHabCotidiana();
    }

    public LiveData<List<CategoriaHabCotidiana>> getCategoriaHabCotidianaAll(){
        return categoriaHabCotidianaAll;
    }

    public void insert(CategoriaHabCotidiana categoriaHabCotidiana){
        categoriaHabCotidianaRepository.insert(categoriaHabCotidiana);
    }*/


}
