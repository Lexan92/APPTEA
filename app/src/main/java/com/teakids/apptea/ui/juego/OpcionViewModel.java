/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.juego;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Opcion;
import roomsqlite.repositorios.OpcionRepository;

public class OpcionViewModel extends AndroidViewModel {
    private OpcionRepository opcionRepository;
    public OpcionViewModel(@NonNull Application application) {
        super(application);
        opcionRepository = new OpcionRepository(application);
    }

    public void insert(Opcion opcion) {opcionRepository.insert(opcion);}

    public void update(Opcion opcion) {opcionRepository.update(opcion);}

    public LiveData<List<Opcion>> getOcionesByIdPregunta(int id){
        return opcionRepository.findOpcionesByIdPregunta(id);
    }

    public int numeroOpciones(int id){
        return opcionRepository.numeroOpciones(id);
    }

   /* public int numeroPictogramaO(int id){return opcionRepository.numeroPictogramaO(id);}*/


}
