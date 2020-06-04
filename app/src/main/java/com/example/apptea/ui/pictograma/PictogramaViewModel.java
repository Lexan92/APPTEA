
/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.pictograma;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Pictograma;
import roomsqlite.repositorios.PictogramaRepository;

public class PictogramaViewModel extends AndroidViewModel {

    private PictogramaRepository pictogramaRepository;
    private LiveData<List<Pictograma>> allPictograma;

    public PictogramaViewModel(Application application){
        super(application);
        pictogramaRepository = new PictogramaRepository(application);
        allPictograma = pictogramaRepository.getAllPictograma();
    }

    public LiveData<List<Pictograma>> getAllCategoriaPictograma(){
        return allPictograma;
    }

    public void insert(Pictograma pictograma){
        pictogramaRepository.insert(pictograma);
    }

    public LiveData<List<Pictograma>> getAllPictogramaByCategoria(int id) {
        return pictogramaRepository.findPictogramasByCategoria(id);
    }
}
