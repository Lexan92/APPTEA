
package com.teakids.apptea.ui.categoriapictograma;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;

import roomsqlite.repositorios.CategoriaPictogramaRepository;

public class CategoriaPictogramaViewModel extends AndroidViewModel {

    private CategoriaPictogramaRepository categoriaPictogramaRepository;
    private LiveData<List<CategoriaPictograma>> allCategoriaPictograma;

    public CategoriaPictogramaViewModel(@NonNull Application application){
        super(application);
        categoriaPictogramaRepository = new CategoriaPictogramaRepository(application);
        allCategoriaPictograma = categoriaPictogramaRepository.getAllCategoriaPictograma();
    }

    public LiveData<List<CategoriaPictograma>> getAllCategoriaPictograma(){
        return allCategoriaPictograma;
    }

    public void insert(CategoriaPictograma categoriaPictograma){
        categoriaPictogramaRepository.insert(categoriaPictograma);
    }

    public void update(CategoriaPictograma categoriaPictograma){
        categoriaPictogramaRepository.update(categoriaPictograma);
    }

    public void delete(CategoriaPictograma categoriaPictograma){
        categoriaPictogramaRepository.delete(categoriaPictograma);
    }

    public LiveData<CategoriaPictograma> findById(int id){
        return categoriaPictogramaRepository.findByIdCategoria(id);
    }

    public int numPictoJuego(int id){return categoriaPictogramaRepository.numPictoJuego(id);}

    public int numPictoHabilidad(int id){return categoriaPictogramaRepository.numPictoHabilidad(id);}

}
