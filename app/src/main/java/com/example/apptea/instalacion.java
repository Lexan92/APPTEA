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

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.apptea.ui.usuario.registro_usuario;

public class instalacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalacion);
        TextView textViewResultados = findViewById(R.id.terminosycondiciones);
        textViewResultados.setMovementMethod(new ScrollingMovementMethod());
    }

    public void acceso(View view){

        Intent acceso = new Intent(this, MainActivity.class);
        startActivity(acceso);
    }

    public void siguiente(View view){

        Intent siguiente = new Intent(this, registro_usuario.class);
        startActivity(siguiente);
    }
}
