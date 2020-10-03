package com.example.apptea.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Usuario;

public class AdministarSesion {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NOMBRE = "sesion";
    String SESION_CLAVE = "sesion_usuario";
    String NAME = "nombre_usuario";


    public AdministarSesion(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NOMBRE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void guardarSesionTEA(PersonaTea personaTea){
        //save session of user whenever user is logged in
        int id = personaTea.getPersona_id();
        editor.putInt(SESION_CLAVE,id).commit();

    }

    public void guardarSesion(Usuario usuario){
        //save session of user whenever user is logged in
        int id = usuario.getUsuario_id();
        editor.putInt(SESION_CLAVE,id).commit();
    }


    public int obtenerSesion(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESION_CLAVE, -1);
    }



    public void cerrarSesion(){
        editor.putInt(SESION_CLAVE,-1).commit();
    }
}
