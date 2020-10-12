package com.example.apptea.utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Usuario;

public class AdministarSesion {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NOMBRE = "sesion";
    String user = "idUsuario";
    String usuario_Rol = "idRolUsuario";
    String persona_Tea = "idPersonaTea";
    String persona_Rol = "idRolPersonaTea";



    public AdministarSesion(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NOMBRE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void guardarSesionTEA(PersonaTea personaTea){
        //save session of user whenever user is logged in
        int id = personaTea.getPersona_id();
        int idRol = personaTea.getRol_id();
        editor.putInt(persona_Tea,id);
        editor.putInt(persona_Rol,idRol);
        editor.commit();

    }

    public void setearTipoUsuario(int bandera) {
        /*
        * -1 = error
        * 0=terapeuta
        * 1=paciente
        * */
        editor.putInt("esAdmin",bandera).commit();
    }

    public int obtenerTipoUsuario(){
        return sharedPreferences.getInt("esAdmin",-1);
    }

    public void guardarSesion(Usuario usuario){
        //save session of user whenever user is logged in
        int id = usuario.getUsuario_id();
        int idRol  = usuario.getRol_id();
        editor.putInt(user,id);
        editor.putInt(usuario_Rol,idRol);
        editor.commit();
    }

    public List<Integer> obtenerSesionPersonaTea(){
        //return user id whose session is saved
        int id = sharedPreferences.getInt(persona_Tea,0);
        int rol = sharedPreferences.getInt(persona_Rol,0);
        return Arrays.asList(id,rol);
    }

    //no creo que sea necesario usa este metodo......
    public List<Integer> obtenerSesionUsuario(){
        //return user id whose session is saved
        int id = sharedPreferences.getInt(user,0);
        int rol = sharedPreferences.getInt(usuario_Rol,0);
        return Arrays.asList(id,rol);
    }

    public void cerrarSesionUsuario(){
        editor.putInt(user,-1).commit();
    }

    public void cerrarSesionPersonaTea(){
        editor.putInt(persona_Tea,-1).commit();
    }

    public void guardarIDSesion(int id){
        editor.putInt("idSesion",id);
        editor.commit();
    }

    public int obtenerIDSesion(){
        return sharedPreferences.getInt("idSesion",-1);
    }
}
