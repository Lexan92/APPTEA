/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.departamento;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Departamento;
import roomsqlite.repositorios.DepartamentoRepository;

public class DepartamentoViewModel /*extends AndroidViewModel */ {
    /*private DepartamentoRepository departamentoRepository;
    private LiveData<List<Departamento>> departamentoAll;

    public DepartamentoViewModel(Application application){
        super(application);
        departamentoRepository =new DepartamentoRepository(application);
        departamentoAll = departamentoRepository.getDepartamento();
    }

    public LiveData<List<Departamento>> getDepartamentoAll(){
        return departamentoAll;
    }

    public void insert(Departamento departamento){
        departamentoRepository.insert(departamento);
    }*/
}
