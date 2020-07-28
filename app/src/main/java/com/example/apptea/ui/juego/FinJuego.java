package com.example.apptea.ui.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        ttsManager.init(getApplicationContext());
        ttsManager.initQueue("Fin del Juego, Bien hecho ");

    }

    public void listaJuegos(View view) {
        finish();
    }
}