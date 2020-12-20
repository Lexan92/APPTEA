


package com.example.apptea.ui.juego;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.utilidades.AdministarSesion;

import roomsqlite.entidades.Juego;

public class JuegoPrincipal extends AppCompatActivity {

    TextView nombreJuego;
    Button nuevaPregunta;
    Juego juegoNuevo = new Juego();
    AdministarSesion idioma ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_principal);
        nombreJuego = findViewById(R.id.editNombreJuego);
        nuevaPregunta = findViewById(R.id.nueva_pregunta);
        idioma = new AdministarSesion(getApplicationContext());


        //setea el nombre del juego
        juegoNuevo = (Juego) getIntent().getSerializableExtra("juego");
        if(idioma.getIdioma()==1){
            nombreJuego.setText(juegoNuevo.getJuego_nombre());
        }else{
            nombreJuego.setText(juegoNuevo.getName_game());}


        //tomar la categoria 1 o 2 para enviar a la actividad correspondiente

        if (juegoNuevo.getCategoria_juego_id()==1){
            nuevaPregunta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DefinirPregunta.class);
                    intent.putExtra("juegoNuevo", juegoNuevo);
                    Log.d("Pregunta", "titulo: " + juegoNuevo.getJuego_nombre());
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            //enviar a definir juego de  memoria
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
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