package com.teakids.apptea.ui.juego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.juegoSeleccion.DetalleResultadoAdapter;
import com.teakids.apptea.ui.juegoSeleccion.DetalleResultadoViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.TTSManager;

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
        String frase = getResources().getString(R.string.finJuegoBienHecho);
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
            nombreJuego.setText(getResources().getString(R.string.nDeFallos)+" "+resultado.getNombre_juego());
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