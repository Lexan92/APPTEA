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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;

import roomsqlite.entidades.Juego;

public class JuegoPrincipal extends AppCompatActivity {

    EditText nombreJuego;
    JuegoViewModel juegoViewModel;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_principal);
        nombreJuego = findViewById(R.id.editNombreJuego);
        guardar = findViewById(R.id.guardar_nombre_juego);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        LiveData<Juego> juego = juegoViewModel.obtenerUltimoJuego();
        Juego juegoNuevo = new Juego();

        juego.observe(this, new Observer<Juego>() {
            @Override
            public void onChanged(Juego juego) {
                nombreJuego.setText(juego.getJuego_nombre());

                juegoNuevo.setJuego_id(juego.getJuego_id());
                juegoNuevo.setCategoria_juego_id(juego.getCategoria_juego_id());
                juegoNuevo.setJuego_nombre(juego.getJuego_nombre());
                juegoNuevo.setJuego_predeterminado(juego.isJuego_predeterminado());
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                juegoNuevo.setJuego_nombre(nombreJuego.getText().toString());
                juegoViewModel.update(juegoNuevo);

            }
        });

    }


}