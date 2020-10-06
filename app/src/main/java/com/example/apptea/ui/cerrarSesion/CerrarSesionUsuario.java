package com.example.apptea.ui.cerrarSesion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apptea.R;
import com.example.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.example.apptea.utilidades.AdministarSesion;

public class CerrarSesionUsuario extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        verificarSesion();
        ocultarTeclado();
    }

    private void ocultarTeclado() {
        View vieww = getCurrentFocus();
        if (vieww != null) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

    private void verificarSesion() {
        AdministarSesion administarSesion = new AdministarSesion(this);
        int ban = administarSesion.obtenerTipoUsuario();
        if (ban == 0) {
            administarSesion.setearTipoUsuario(-1);
            administarSesion.cerrarSesionUsuario();
            Intent intent = new Intent(CerrarSesionUsuario.this, ListadoInicioSesion.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_sesion_usuario);
    }
}