package com.example.apptea.ui.cerrarSesion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.example.apptea.utilidades.AdministarSesion;

public class CerrarSesionUsuario extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();

        ocultarTeclado();
    }

    private void ocultarTeclado() {
        View vieww = getCurrentFocus();
        if (vieww != null) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_sesion_usuario);

        AdministarSesion administarSesion = new AdministarSesion(this);
        Button guardar, cancelar;
        EditText comentario;
        guardar = findViewById(R.id.btn_guardar_sesion);
        cancelar = findViewById(R.id.btn_cancelar_sesion);
        comentario= findViewById(R.id.edit_text_guardar_sesion);



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CerrarSesionUsuario.this, MainActivity.class);
                intent.putExtra("bandera", false);
                startActivity(intent);
                finish();
            }
        });


    }


}