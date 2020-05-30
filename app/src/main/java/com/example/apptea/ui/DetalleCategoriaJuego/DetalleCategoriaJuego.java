/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaJuegoFragment;

public class DetalleCategoriaJuego extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_categoria_juego);


        Intent intent = getIntent();
        String message = intent.getStringExtra(CategoriaJuegoFragment.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.text_nombre_detalle_categoria_juego);
        textView.setText(message);
    }
}
