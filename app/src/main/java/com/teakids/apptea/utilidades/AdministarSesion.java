package com.teakids.apptea.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

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




    public AdministarSesion(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NOMBRE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void guardarSesionTEA(PersonaTea personaTea){
        //save session of user whenever user is logged in
        int id = personaTea.getPersona_id();
        editor.putInt(persona_Tea,id);
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

    public int obtenerIdPersonaTea(){

        int id = sharedPreferences.getInt(persona_Tea,0);
        return id;
    }

    //no creo que sea necesario usa este metodo......
    public List<Integer> obtenerSesionUsuario(){

        int id = sharedPreferences.getInt(user,0);
        int rol = sharedPreferences.getInt(usuario_Rol,0);
        return Arrays.asList(id,rol);
    }

    public void cerrarSesionUsuario(){
        editor.putInt(user,-1).commit();
    }

    public void cerrarSesionPersonaTea(){

        editor.putInt(persona_Tea,-1);
        editor.putInt("idSesion",0);
        editor.putInt(user,0);
        editor.putInt("esAdmin",-1);
        editor.putInt(persona_Tea,0);
        editor.commit();

    }

    public void guardarIDSesion(int id){
        editor.putInt("idSesion",id);
        editor.commit();
    }

    public int obtenerIDSesion(){
        return sharedPreferences.getInt("idSesion",-1);
    }


    //CONFIGURACION

    public void configuracionIdioma(int idioma){

        editor.putInt("codIdioma", idioma);
        editor.commit();
    }

    public  void configurarDesbloqueo(int desbloqueo){
        editor.putInt("codDesbloqueo", desbloqueo);
        editor.commit();
    }

    public int getIdioma(){
        return  sharedPreferences.getInt("codIdioma",-1);
    }

    public  int getDesbloqueo(){
        return sharedPreferences.getInt("codDesbloqueo",-1);
    }

}
