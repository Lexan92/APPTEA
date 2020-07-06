/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.juego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.pictograma.PictogramaViewModel;

import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pregunta;

public class VisorPregunta extends AppCompatActivity {
    boolean isCheckedOpcionUno = false;
    boolean isCheckedOpcionDos = false;
    boolean isCheckedOpcionTres = false;
    boolean isCheckedOpcionCuatro = false;
    ImageButton opcionBoton1,opcionBoton2,opcionBoton3,opcionBoton4;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    TextView tituloPregunta, tituloJuego, contadorPreguntas,txt1,txt2,txt3,txt4;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    boolean tienePreguntas = false;
    Juego juego = new Juego();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pregunta);
        tituloJuego = findViewById(R.id.editNombreJuegoVisor);
        tituloPregunta = findViewById(R.id.nombre_pregunta_visor);
        contadorPreguntas = findViewById(R.id.contador_preguntas);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        juego = (Juego) getIntent().getSerializableExtra("juego");
        opcionBoton1 = findViewById(R.id.opcion_uno_visor);
        opcionBoton2 = findViewById(R.id.opcion_dos_visor);
        opcionBoton3 = findViewById(R.id.opcion_tres_visor);
        opcionBoton4 = findViewById(R.id.opcion_cuatro_visor);
        txt1 = findViewById(R.id.nombre_opcion_uno_visor);
        txt2 = findViewById(R.id.nombre_opcion_dos_visor);
        txt3 = findViewById(R.id.nombre_opcion_tres_visor);
        txt4 = findViewById(R.id.nombre_opcion_cuatro_visor);
        LottieAnimationView checkedDone1 = findViewById(R.id.check_opcion_uno_visor);
        LottieAnimationView checkedDone2 = findViewById(R.id.check_opcion_dos_visor);
        LottieAnimationView checkedDone3 = findViewById(R.id.check_opcion_tres_visor);
        LottieAnimationView checkedDone4 = findViewById(R.id.check_opcion_cuatro_visor);


        listadoPreguntas = preguntaViewModel.getPreguntasByIdJuego(juego.getJuego_id());

        listadoPreguntas.observe(VisorPregunta.this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {

                if(preguntas.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),JuegoPrincipal.class);
                    intent.putExtra("juego",juego);
                    startActivity(intent);
                    finish();
                }else {
                    tituloPregunta.setText(preguntas.get(0).getTitulo_pregunta());
                    tituloJuego.setText(juego.getJuego_nombre());
                    setearOpciones(preguntas.get(0).getPregunta_id());
                    contadorPreguntas.setText("1 / " + preguntas.size());

                }

            }
        });

    }

    private void setearOpciones(int id) {
        listaOpciones = opcionViewModel.getOcionesByIdPregunta(id);
        listaOpciones.observe(VisorPregunta.this, new Observer<List<Opcion>>() {
            @Override
            public void onChanged(List<Opcion> opciones) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}