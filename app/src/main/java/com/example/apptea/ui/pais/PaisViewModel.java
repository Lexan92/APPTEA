/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.pais;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Pais;
import roomsqlite.repositorios.PaisRepository;

public class PaisViewModel extends AndroidViewModel {
    private PaisRepository paisRepository;
    private LiveData<List<Pais>> paisAll;

    public PaisViewModel(Application application){
        super(application);
        paisRepository =new PaisRepository(application);
        paisAll = paisRepository.getPais();
    }

    public LiveData<List<Pais>> getPaisAll(){
        return paisAll;
    }

    public void insert(Pais pais){
        paisRepository.insert(pais);
    }
}
