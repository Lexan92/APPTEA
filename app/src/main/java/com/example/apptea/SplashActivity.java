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

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.ui.inicioSesion.AlertaSesion;
import com.example.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.example.apptea.ui.usuario.UsuarioViewModel;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.List;
import java.util.Locale;

import roomsqlite.entidades.Usuario;

public class SplashActivity extends AppCompatActivity {
    UsuarioViewModel usuarioViewModel;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdministarSesion administarSesion = new AdministarSesion(getApplicationContext());
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioAll().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                for (Usuario user : usuarios) {
                    id = user.getUsuario_id();
                }

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (id > 0) {

                    if (administarSesion.obtenerIdPersonaTea()>0){
                        Intent intent = new Intent(SplashActivity.this, AlertaSesion.class);
                        startActivity(intent);
                    }else {
                        //salto a listado de persona tea
                        Intent intent = new Intent(SplashActivity.this, ListadoInicioSesion.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(SplashActivity.this, instalacion.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 3000);

        // IDIOMA 1 = ESPAÑOL 2 = INGLES
        int español= 1, ingles= 2;
       /* String idioma;
        if(administarSesion.getIdioma() == -1){
            idioma = Locale.getDefault().getLanguage();

            if(idioma.equals("es")|| idioma.equals("ES")){
                administarSesion.configuracionIdioma(español);
                System.out.println("Idioma es español " + idioma);
            }
            else if(idioma.equals("en")|| idioma.equals("EN")){
                administarSesion.configuracionIdioma(ingles);
                System.out.println("Idioma  es ingles " + idioma);
            }
            else if(!idioma.equals("en")|| !idioma.equals("EN") ||!idioma.equals("es")|| !idioma.equals("ES")){
                administarSesion.configuracionIdioma(español);
                System.out.println("Idioma es otro " + idioma);
            }

        }else if(administarSesion.getIdioma()==1){
            idioma= "es";
        }else
            idioma= "en";*/

        // DESBLOQUEO 1 = POR CONTRASEÑA , 2 = POR HUELLA
        administarSesion.configurarDesbloqueo(1);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
}
