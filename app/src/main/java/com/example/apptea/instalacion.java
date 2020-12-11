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

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.ui.usuario.RegistroUsuarioActivity;

public class instalacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalacion);
        TextView textViewResultados = findViewById(R.id.terminosycondiciones);
        textViewResultados.setMovementMethod(new ScrollingMovementMethod());
    }

   /* public void acceso(View view){

        Intent acceso = new Intent(this, MainActivity.class);
        startActivity(acceso);
    }*/

    public void siguiente(View view){

        Intent siguiente = new Intent(this, RegistroUsuarioActivity.class);
        startActivity(siguiente);
        finish();

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
