package com.teakids.apptea.ui.habilidadCotidiana;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Secuencia;
import roomsqlite.repositorios.SecuenciaRepository;

public class SecuenciaViewModel extends AndroidViewModel {

    private SecuenciaRepository secuenciaRepository;
    private LiveData<List<Secuencia>>secuenciaAll;

    public SecuenciaViewModel(@NonNull Application application) {
        super(application);
        secuenciaRepository = new SecuenciaRepository(application);
    }

    public List<Secuencia> getSecuenciaById(int id){
        return secuenciaRepository.getSecuenciaByHabilidadCotidiana(id);
    }

    public void insert(Secuencia secuencia){
        secuenciaRepository.insert(secuencia);
    }

    public void update(Secuencia secuencia){
        secuenciaRepository.update(secuencia);
    }

    public void delete(Secuencia secuencia){
        secuenciaRepository.delete(secuencia);
    }

      public LiveData<Secuencia> getSecuenciaForImagen(int id){
        return secuenciaRepository.getSecuenciaForImagen(id);
    }

}
