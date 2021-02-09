package com.teakids.apptea.ui.juegoSeleccion;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.DetalleResultado;
import roomsqlite.repositorios.DetalleResultadoRepository;

public class DetalleResultadoViewModel extends AndroidViewModel {

    private DetalleResultadoRepository detalleResultadoRepository;
    private LiveData<List<DetalleResultado>> detalleResultadoAll;

    public DetalleResultadoViewModel( Application application) {
        super(application);
        detalleResultadoRepository = new DetalleResultadoRepository(application);
        detalleResultadoAll = detalleResultadoRepository.GetDetalleResultado();
    }

    public  void insertResultado(DetalleResultado detalleResultado){detalleResultadoRepository.insertDetalleResultado(detalleResultado);}

    public LiveData<List<DetalleResultado>> getDetalleResultadoByResultado(int id) {
        return detalleResultadoRepository.finfByResultadoId(id);
    }
}
