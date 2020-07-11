


package com.example.apptea.ui.juego;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;

import roomsqlite.entidades.Juego;

public class JuegoPrincipal extends AppCompatActivity {

    TextView nombreJuego;
    JuegoViewModel juegoViewModel;
    Button nuevaPregunta;
    Juego juegoNuevo = new Juego();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_principal);
        nombreJuego = findViewById(R.id.editNombreJuego);

        nuevaPregunta = findViewById(R.id.nueva_pregunta);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);

        //setea el nombre del juego
        juegoNuevo = (Juego) getIntent().getSerializableExtra("juego");
        nombreJuego.setText(juegoNuevo.getJuego_nombre());


        nuevaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DefinirPregunta.class);
                intent.putExtra("juegoNuevo", juegoNuevo);
                Log.d("Pregunta", "titulo: " + juegoNuevo.getJuego_nombre());
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