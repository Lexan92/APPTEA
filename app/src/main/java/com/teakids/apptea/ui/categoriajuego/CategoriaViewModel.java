/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.categoriajuego;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.repositorios.CategoriaJuegoRepository;

public class CategoriaViewModel extends AndroidViewModel {

    private CategoriaJuegoRepository categoriaJuegoRepository;
    private LiveData<List<CategoriaJuego>> allCategoriasJuegos;

    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        categoriaJuegoRepository = new CategoriaJuegoRepository(application);
        allCategoriasJuegos = categoriaJuegoRepository.getAllCategoriaJuego();
    }

    public LiveData<List<CategoriaJuego>> getAllCategoriasJuegos(){
        return allCategoriasJuegos;
    }

    public void insert(CategoriaJuego categoriaJuego){
        categoriaJuegoRepository.insert(categoriaJuego);
    }

    public LiveData<CategoriaJuego> findById(int id){
        return categoriaJuegoRepository.findByIdCategoria(id);
    }
}
