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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.apptea.R;


//Fragment para consultar la pantalla mi perfil del usuario
public class UsuarioFragment extends Fragment {



    public UsuarioFragment(){
        //requiere un constructor vacio
    }



    public View OnCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View vista = inflater.inflate(R.layout.fragment_mi_perfil, container, false);










        return vista;
    }










}
