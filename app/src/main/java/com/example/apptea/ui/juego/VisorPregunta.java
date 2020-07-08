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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
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
    Button nuevapregunta;
    TextView tituloPregunta, tituloJuego, contadorPreguntas,txt1,txt2,txt3,txt4;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    Juego juego = new Juego();
    LottieAnimationView checkedDone1 ;
    LottieAnimationView checkedDone2 ;
    LottieAnimationView checkedDone3 ;
    LottieAnimationView checkedDone4 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pregunta);
        nuevapregunta = findViewById(R.id.nueva_pregunta_visor);
        tituloJuego = findViewById(R.id.editNombreJuegoVisor);
        tituloPregunta = findViewById(R.id.nombre_pregunta_visor);
        contadorPreguntas = findViewById(R.id.contador_preguntas);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        juego = (Juego) getIntent().getSerializableExtra("juego");
        opcionBoton1 = findViewById(R.id.opcion_uno_visor);
        opcionBoton2 = findViewById(R.id.opcion_dos_visor);
        opcionBoton3 = findViewById(R.id.opcion_tres_visor);
        opcionBoton4 = findViewById(R.id.opcion_cuatro_visor);
        txt1 = findViewById(R.id.nombre_opcion_uno_visor);
        txt2 = findViewById(R.id.nombre_opcion_dos_visor);
        txt3 = findViewById(R.id.nombre_opcion_tres_visor);
        txt4 = findViewById(R.id.nombre_opcion_cuatro_visor);
        checkedDone1 = findViewById(R.id.check_opcion_uno_visor);
        checkedDone2 = findViewById(R.id.check_opcion_dos_visor);
         checkedDone3 = findViewById(R.id.check_opcion_tres_visor);
         checkedDone4 = findViewById(R.id.check_opcion_cuatro_visor);


        listadoPreguntas = preguntaViewModel.getPreguntasByIdJuego(juego.getJuego_id());

        listadoPreguntas.observe(VisorPregunta.this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {
                    tituloPregunta.setText(preguntas.get(0).getTitulo_pregunta());
                    tituloJuego.setText(juego.getJuego_nombre());
                    setearOpciones(preguntas.get(0).getPregunta_id());
                    contadorPreguntas.setText("1 / " + preguntas.size());

            }
        });

        //NUEVA PREGUNTA
        nuevapregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listadoPreguntas.observe(VisorPregunta.this, new Observer<List<Pregunta>>() {
                    @Override
                    public void onChanged(List<Pregunta> preguntas) {
                        if ((preguntas.size())+1<=10){
                            Intent intent = new Intent(getApplicationContext(),DefinirPregunta.class);
                            intent.putExtra("juegoNuevo",juego);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Ya ha alcanzado el máximo de 10 preguntas, no puede crear más preguntas",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });

    }

    private void setearOpciones(int id) {
        listaOpciones = opcionViewModel.getOcionesByIdPregunta(id);
        listaOpciones.observe(VisorPregunta.this, new Observer<List<Opcion>>() {
            @Override
            public void onChanged(List<Opcion> opciones) {
                if (opciones.get(0)!=null){
//                    opcionBoton1.setImageBitmap(opciones.get(0));

                    LiveData<Pictograma> pictograma;

                    pictograma = pictogramaViewModel.getPictogramaById(opciones.get(0).getPictograma_id());

                    //seteado de imagen y texto del pictograma
                    pictograma.observe(VisorPregunta.this, new Observer<Pictograma>() {
                        @Override
                        public void onChanged(Pictograma pictograma) {
                            opcionBoton1.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            txt1.setText(pictograma.getPictograma_nombre());

                        }
                    });
                    //seteado de checkbox
                    if (opciones.get(0).isOpcion_respuesta()){
                        checkedDone1.setMinAndMaxProgress(1.0f,1.0f);
                        checkedDone1.playAnimation();
                    }else {
                        checkedDone1.setMinAndMaxProgress(0.0f,0.0f);
                        checkedDone1.playAnimation();
                    }




                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        listadoPreguntas=null;
        listaOpciones=null;

        Runtime.getRuntime().gc();
        Log.d("life","Ondestroy visorpregunta");
    }
}