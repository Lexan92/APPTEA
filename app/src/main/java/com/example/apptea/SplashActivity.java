/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.example.apptea.ui.usuario.UsuarioViewModel;

import java.util.List;

import roomsqlite.entidades.Usuario;

public class SplashActivity extends AppCompatActivity {
    UsuarioViewModel usuarioViewModel;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuarioAll().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                for(Usuario user : usuarios){
                    id= user.getUsuario_id();
                    System.out.println(user.getUsuario_id());
                    System.out.println(user.getUsuario_nombre());
                }

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                System.out.println("este es el id."+ id);

                if(id>0){
                    System.out.println("no entro");
                    //salto a listado de persona tea
                    Intent intent =new Intent(SplashActivity.this, ListadoInicioSesion.class);
                    startActivity(intent);
                }
                else{
                    System.out.println("se vino aqui");
                    Intent intent =new Intent(SplashActivity.this, instalacion.class);
                    startActivity(intent);
                }

                finish();
            }
        },3000);
    }
}
