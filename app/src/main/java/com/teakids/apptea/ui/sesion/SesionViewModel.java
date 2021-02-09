package com.teakids.apptea.ui.sesion;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Sesion;
import roomsqlite.repositorios.SesionRepository;

public class SesionViewModel extends AndroidViewModel {
    private SesionRepository sesionRepository;
    public SesionViewModel(@NonNull Application application) {
        super(application);
        sesionRepository = new SesionRepository(application);
    }

    public LiveData<List<Sesion>> obtenerSesionesPorIDPersona(int id){
        return sesionRepository.obtenerSesionesPorPersona(id);
    }
}
