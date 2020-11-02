package com.example.apptea.ui.juegoSeleccion;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Resultado;
import roomsqlite.repositorios.ResultadoRepository;

public class ResultadoViewModel  extends AndroidViewModel {

    private ResultadoRepository resultadoRepository;
    private LiveData<List<Resultado>> resultadoAll;

    public ResultadoViewModel( Application application) {
        super(application);
        resultadoRepository = new ResultadoRepository(application);
        resultadoAll = resultadoRepository.getResultado();
    }

    public  void insertResultado(Resultado resultado){resultadoRepository.insertResultado(resultado);}

    public Resultado obtenerResultado(){return resultadoRepository.obtenerResultado();}




    }
