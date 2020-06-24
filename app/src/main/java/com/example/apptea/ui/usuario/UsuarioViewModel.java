/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.usuario;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Usuario;
import roomsqlite.repositorios.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {
    private UsuarioRepository usuarioRepository;
    private LiveData<List<Usuario>> usuarioAll;

    public UsuarioViewModel(Application application){
        super(application);
        usuarioRepository =new UsuarioRepository(application);
        usuarioAll = usuarioRepository.getUsuario();
    }

    public LiveData<List<Usuario>> getUsuarioAll(){
        return usuarioAll;
    }

    public void insert(Usuario usuario){
        usuarioRepository.insert(usuario);
    }

    public void update(Usuario usuario){
        usuarioRepository.update(usuario);
    }

}
