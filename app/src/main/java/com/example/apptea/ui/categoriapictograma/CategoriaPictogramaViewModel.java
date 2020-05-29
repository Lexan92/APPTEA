
package com.example.apptea.ui.categoriapictograma;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;

import roomsqlite.repositorios.CategoriaPictogramaRepository;

public class CategoriaPictogramaViewModel extends AndroidViewModel {

    private CategoriaPictogramaRepository categoriaPictogramaRepository;
    private LiveData<List<CategoriaPictograma>> allCategoriaPictograma;

    public CategoriaPictogramaViewModel(Application application){
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



}
