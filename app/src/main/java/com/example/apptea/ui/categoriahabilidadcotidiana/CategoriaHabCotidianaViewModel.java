/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

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
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.repositorios.CategoriaHabCotidianaRepository;

public class CategoriaHabCotidianaViewModel extends AndroidViewModel {

    private CategoriaHabCotidianaRepository categoriaHabCotidianaRepository;
    private LiveData<List<CategoriaHabCotidiana>> categoriaHabCotidianaAll;

    public CategoriaHabCotidianaViewModel(Application application){
        super(application);
        categoriaHabCotidianaRepository = new CategoriaHabCotidianaRepository(application);
        categoriaHabCotidianaAll = categoriaHabCotidianaRepository.getTodasCategoriaHabCotidiana();
    }

    public LiveData<List<CategoriaHabCotidiana>> getCategoriaHabCotidianaAll(){
        return categoriaHabCotidianaAll;
    }

    public void insert(CategoriaHabCotidiana categoriaHabCotidiana){
        categoriaHabCotidianaRepository.insert(categoriaHabCotidiana);
    }



}
