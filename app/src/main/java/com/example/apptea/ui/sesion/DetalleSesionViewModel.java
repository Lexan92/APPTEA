package com.example.apptea.ui.sesion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.DetalleSesion;
import roomsqlite.repositorios.DetalleSesionRepository;

public class DetalleSesionViewModel extends AndroidViewModel {
    private final DetalleSesionRepository detalleSesionRepository;

    public DetalleSesionViewModel(@NonNull Application application) {
        super(application);
        detalleSesionRepository = new DetalleSesionRepository(application);
    }

    public LiveData<List<DetalleSesion>> obtenerDetalleSesiones(int id){
        return detalleSesionRepository.obtenerDetalleSesiones(id);
    }
}
