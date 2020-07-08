/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.juego;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Pregunta;
import roomsqlite.repositorios.PreguntaRepository;

public class PreguntaViewModel extends AndroidViewModel {
    private PreguntaRepository preguntaRepository;
    public PreguntaViewModel(@NonNull Application application) {
        super(application);
        preguntaRepository = new PreguntaRepository(application);
    }

    public  void insert(Pregunta pregunta){
        preguntaRepository.insert(pregunta);
    }

    public void update(Pregunta pregunta){
        preguntaRepository.update(pregunta);
    }

    public void delete(Pregunta pregunta){
        preguntaRepository.delete(pregunta);
    }

    public LiveData<List<Pregunta>> getPreguntasByIdJuego(int id){
        return preguntaRepository.findPreguntasByIdJuego(id);
    }

    public int numeroPreguntas(int id){
        return preguntaRepository.numeroPreguntas(id);
    }

    public LiveData<Pregunta> obtenerPreguntaPorID(int id){
        return  preguntaRepository.obtenerPreguntaporID(id);
    }

}
