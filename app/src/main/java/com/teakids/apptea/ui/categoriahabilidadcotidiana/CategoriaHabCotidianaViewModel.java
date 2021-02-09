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

package com.teakids.apptea.ui.categoriahabilidadcotidiana;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    public void update(CategoriaHabCotidiana categoriaHabCotidiana){
       categoriaHabCotidianaRepository.update(categoriaHabCotidiana);
    }

    public void delete(CategoriaHabCotidiana categoriaHabCotidiana){
        categoriaHabCotidianaRepository.delete(categoriaHabCotidiana);
    }
}
