/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Juego;
import roomsqlite.repositorios.JuegoRepository;

public class JuegoViewModel extends AndroidViewModel {

    private JuegoRepository juegoRepository;

    public JuegoViewModel(@NonNull Application application) {
        super(application);
        juegoRepository = new JuegoRepository(application);

    }

    public void insert(Juego juego){
        juegoRepository.insert(juego);
    }

    public void update(Juego juego){
        juegoRepository.update(juego);
    }

    public void delete (Juego juego){
        juegoRepository.delete(juego);
    }

    public LiveData<List<Juego>> findJuegosByCategoriaId(int id){
        return juegoRepository.findJuegosByCategoria(id);
    }

    public LiveData<Juego> obtenerUltimoJuego(){
        return juegoRepository.obtenerUltimo();
    }

}
