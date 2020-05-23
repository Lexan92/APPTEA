package com.example.apptea.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.CatalogoHabCotidiana;

public class CatalogoHabCotidianaViewModel  extends AndroidViewModel {

    private LiveData<List<CatalogoHabCotidiana>> catalogoHabCotidianas;

    public CatalogoHabCotidianaViewModel(@NonNull Application application) {
        super(application);
    }
}
