package com.example.apptea.ui.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.apptea.R;
import com.example.apptea.utilidades.TTSManager;

public class FinJuego extends AppCompatActivity {

    TTSManager ttsManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);
        ttsManager = new TTSManager();
        ttsManager.init(getApplication());
        String frase = "¡Fin del Juego! ¡Bien hecho! ";

        int milisegundos = 1000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ttsManager.initQueue(frase);
            }
        },milisegundos);

    }

    public void listaJuegos(View view) {
        finish();
    }
}