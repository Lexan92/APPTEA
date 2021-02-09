/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.rol;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.Rol;
import roomsqlite.repositorios.RolRepository;

public class RolViewModel extends AndroidViewModel {
    private RolRepository rolRepository;
    private LiveData<List<Rol>> rolAll;
    private Rol rol;

    public RolViewModel(Application application){
        super(application);
        rolRepository =new RolRepository(application);
        rolAll = rolRepository.getRol();

    }

    public LiveData<List<Rol>> getRolAll(){
        rolAll = rolRepository.getRol();
        return rolAll;
    }

    public LiveData<Rol> getRolById(int id){
        return rolRepository.findRolById(id);
    }

    public void insert(Rol rol){
        rolRepository.insertRol(rol);
    }
}
