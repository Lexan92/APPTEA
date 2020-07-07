/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.juego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.SharedElementCallback;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

import roomsqlite.entidades.Juego;

public class JuegoPrincipal extends AppCompatActivity {

    EditText nombreJuego;
    JuegoViewModel juegoViewModel;
    Button guardar, nuevaPregunta;
    Juego juegoNuevo = new Juego();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_principal);
        nombreJuego = findViewById(R.id.editNombreJuego);
        guardar = findViewById(R.id.guardar_nombre_juego);
        nuevaPregunta = findViewById(R.id.nueva_pregunta);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);



            //setea el nombre del juego
            juegoNuevo = (Juego) getIntent().getSerializableExtra("juego");
            nombreJuego.setText(juegoNuevo.getJuego_nombre());



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                juegoNuevo.setJuego_nombre(nombreJuego.getText().toString());
                juegoViewModel.update(juegoNuevo);

            }
        });


        nuevaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DefinirPregunta.class);
                intent.putExtra("juegoNuevo",juegoNuevo);
                Log.d("Pregunta","titulo: "+juegoNuevo.getJuego_nombre());
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}