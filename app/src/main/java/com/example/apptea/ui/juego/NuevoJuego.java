

package com.example.apptea.ui.juego;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.ui.juegoMemoria.VisorMemoria;
import com.example.apptea.utilidades.ValidacionCadenas;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import roomsqlite.entidades.Juego;

public class NuevoJuego extends AppCompatActivity {

    Button crear, cancelar;
    TextInputEditText nombreJuego;
    JuegoViewModel juegoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_juego);
        crear = findViewById(R.id.crearJuego);
        cancelar = findViewById(R.id.cancelarCrearJuego);
        nombreJuego = findViewById(R.id.nombre_juego);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        //se obtiene el ID de categoria Juego
        int keyCategoriaJuego = getIntent().getIntExtra("categoriaJuego", 0);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreJuego.getText().toString().isEmpty()) {
                    Snackbar.make(v, getResources().getString(R.string.debeIngresarUnTituloDeJuego), Snackbar.LENGTH_LONG)
                            .show();
                } else if (ValidacionCadenas.validarTamaño(nombreJuego.getText().toString(),25)){
                    Snackbar.make(v, getResources().getString(R.string.elTituloDebeSerMenor25), Snackbar.LENGTH_LONG)
                            .show();

                } else {
                    Juego juego = new Juego();
                    juego.setJuego_nombre(ValidacionCadenas.capitalizaCadena(nombreJuego.getText().toString()));
                    juego.setName_game(ValidacionCadenas.capitalizaCadena(nombreJuego.getText().toString()));
                    juego.setCategoria_juego_id(keyCategoriaJuego);
                    juego.setJuego_predeterminado(false);
                    juegoViewModel.insert(juego);

                    LiveData<Juego> juegoNuevo;
                    juegoNuevo = juegoViewModel.obtenerUltimoJuego();

                    juegoNuevo.observe(NuevoJuego.this, new Observer<Juego>() {
                        @Override
                        public void onChanged(Juego juego) {
                            //validacion por KEY (categoria del juego)
                            if(keyCategoriaJuego==1){
                                Intent intent = new Intent(getApplicationContext(), JuegoPrincipal.class);
                                intent.putExtra("juego", juego);
                                startActivity(intent);
                                finish();
                            } else {
                                //categoria 2 del juego
                                Intent intent = new Intent(getApplicationContext(), VisorMemoria.class);
                                intent.putExtra("juego",juego);
                                startActivity(intent);
                                finish();

                            }

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