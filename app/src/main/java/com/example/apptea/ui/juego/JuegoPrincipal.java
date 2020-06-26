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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;

import roomsqlite.entidades.Juego;

public class JuegoPrincipal extends AppCompatActivity {

    EditText nombreJuego;
    JuegoViewModel juegoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_principal);
        nombreJuego = findViewById(R.id.editNombreJuego);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        LiveData<Juego> juego = juegoViewModel.obtenerUltimoJuego();

       // nombreJuego.setText(juego.getValue().getJuego_nombre());


    }
}