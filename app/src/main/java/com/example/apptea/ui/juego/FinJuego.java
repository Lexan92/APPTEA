package com.example.apptea.ui.juego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.apptea.R;
import com.example.apptea.ui.juegoSeleccion.DetalleResultadoAdapter;
import com.example.apptea.ui.juegoSeleccion.DetalleResultadoViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.TTSManager;

import java.util.List;

import roomsqlite.entidades.DetalleResultado;
import roomsqlite.entidades.Resultado;

public class FinJuego extends AppCompatActivity {

    TTSManager ttsManager = null;
    RecyclerView recyclerView;
    private DetalleResultadoViewModel detalleResultadoViewModel;
    Resultado resultado = new Resultado();
    TextView nombreJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);
        resultado = (Resultado) getIntent().getSerializableExtra("resultado");
        nombreJuego = findViewById(R.id.ResNombreJuego);
        ttsManager = new TTSManager();
        ttsManager.init(getApplication());
        String frase = "¡Fin del Juego! ¡Bien hecho! ";
        AdministarSesion administarSesion = new AdministarSesion(this);

        int milisegundos = 1000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ttsManager.initQueue(frase);
            }
        },milisegundos);

        if (administarSesion.obtenerIDSesion() > 0) {
            nombreJuego.setText("N° de fallos: "+resultado.getNombre_juego());
            recyclerView =findViewById(R.id.lista_detalleRes);
            final DetalleResultadoAdapter detalleResultadoAdapter = new DetalleResultadoAdapter();
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
            recyclerView.setAdapter(detalleResultadoAdapter);
            detalleResultadoViewModel = new ViewModelProvider(this).get(DetalleResultadoViewModel.class);
            detalleResultadoViewModel.getDetalleResultadoByResultado(resultado.getResultado_id()).observe(this, new Observer<List<DetalleResultado>>() {
                @Override
                public void onChanged(List<DetalleResultado> detalleResultados) {
                    detalleResultadoAdapter.setDetalleResultados(detalleResultados);
                }
            });
        }

    }

    public void listaJuegos(View view) {
        finish();
    }
}