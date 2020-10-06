/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.habilidadCotidiana;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Secuencia;
import roomsqlite.repositorios.HabilidadCotidianaRepository;
import roomsqlite.repositorios.SecuenciaRepository;

public class HabilidadCotidianaViewModel extends AndroidViewModel{

    private HabilidadCotidianaRepository habilidadCotidianaRepository;
    private SecuenciaRepository secuenciaRepository;
    private LiveData<List<HabilidadCotidiana>> habilidadCotidianaAll;

    public HabilidadCotidianaViewModel(@NonNull Application application) {
        super(application);
        habilidadCotidianaRepository = new HabilidadCotidianaRepository(application);
        secuenciaRepository = new SecuenciaRepository(application);
    }

    public LiveData<List<HabilidadCotidiana>> getHabilidadCotidianaAll(int id){
        return habilidadCotidianaRepository.findHabilidadCotidianaByCategoriaHab(id);
    }

    public void insert(HabilidadCotidiana habilidadCotidiana){
        habilidadCotidianaRepository.insert(habilidadCotidiana);
    }

    public void update(HabilidadCotidiana habilidadCotidiana){
        habilidadCotidianaRepository.update(habilidadCotidiana);
    }

    public void delete(HabilidadCotidiana habilidadCotidiana){
        habilidadCotidianaRepository.delete(habilidadCotidiana);
    }

    public LiveData<Secuencia> getSecuenciaForImagen(int id){
        return secuenciaRepository.getSecuenciaForImagen(id);
    }

    public Secuencia getSecuenciaForImagenSec(int id){
        return secuenciaRepository.getSecuenciaForImagenSec(id);
    }

}
