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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;

public class CatalogoHabCotidianaViewModel  extends AndroidViewModel {

    private LiveData<List<CategoriaHabCotidiana>> catalogoHabCotidianas;

    public CatalogoHabCotidianaViewModel(@NonNull Application application) {
        super(application);
    }
}
