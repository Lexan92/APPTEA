package com.example.apptea.ui.juegoSeleccion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptea.R;
import com.example.apptea.ui.juego.OpcionViewModel;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;

public class SeleccionaOpcion extends AppCompatActivity {

    MaterialCardView opcion1, opcion2, opcion3, opcion4;
    TextView tituloPregunta, tituloJuego, txt1, txt2, txt3, txt4;
    Button siguiente;
    ImageView imagen1,imagen2,imagen3,imagen4;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    Juego juego = new Juego();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_opcion);
        opcion1 = findViewById(R.id.card_1);
        opcion2 = findViewById(R.id.card_2);
        opcion3 = findViewById(R.id.card_3);
        opcion4 = findViewById(R.id.card_4);
        tituloJuego = findViewById(R.id.titulo_juego);
        tituloPregunta = findViewById(R.id.titulo_pregunta);
        txt1 = findViewById(R.id.titulo_pictograma_uno);
        txt2 = findViewById(R.id.titulo_pictograma_dos);
        txt3 = findViewById(R.id.titulo_pictograma_tres);
        txt4 = findViewById(R.id.titulo_pictograma_cuatro);
        imagen1 = findViewById(R.id.pictograma_uno);
        imagen2 = findViewById(R.id.pictograma_dos);
        imagen3 = findViewById(R.id.pictograma_tres);
        imagen4 = findViewById(R.id.pictograma_cuatro);
        siguiente = findViewById(R.id.pregunta_siguiente);
        juego = (Juego) getIntent().getSerializableExtra("juego");

        //seteado de nombre del juego
        tituloJuego.setText(juego.getJuego_nombre());






    }
}