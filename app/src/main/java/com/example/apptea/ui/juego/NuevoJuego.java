

package com.example.apptea.ui.juego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;
import com.google.android.material.snackbar.Snackbar;

import roomsqlite.entidades.Juego;

public class NuevoJuego extends AppCompatActivity {

    Button crear, cancelar;
    EditText nombreJuego;
    JuegoViewModel juegoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_juego);
        crear =  findViewById(R.id.crearJuego);
        cancelar = findViewById(R.id.cancelarCrearJuego);
        nombreJuego = findViewById(R.id.nombre_juego);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        //se obtiene el ID de categoria Juego
        int keyCategoriaJuego = getIntent().getIntExtra("categoriaJuego",0);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombreJuego.getText().toString().isEmpty()){
                    Snackbar.make(v,"Debe ingresar un Titulo de Juego", Snackbar.LENGTH_LONG)
                    .show();
                }
                else {
                    Juego juego = new Juego();
                    juego.setJuego_nombre(nombreJuego.getText().toString());
                    juego.setCategoria_juego_id(keyCategoriaJuego);
                    juego.setJuego_predeterminado(false);
                    juegoViewModel.insert(juego);

                    LiveData<Juego> juegoNuevo ;
                    juegoNuevo=juegoViewModel.obtenerUltimoJuego();

                    juegoNuevo.observe(NuevoJuego.this, new Observer<Juego>() {
                        @Override
                        public void onChanged(Juego juego) {
                            Intent intent = new Intent(getApplicationContext(),JuegoPrincipal.class);
                            intent.putExtra("juego",juego);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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