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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;
import com.example.apptea.ui.pictograma.PictogramaViewModel;

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
    ImageButton opcionBoton1, opcionBoton2, opcionBoton3, opcionBoton4, editarPregunta, borrarPregunta, preguntaSiguiente, preguntaAnterior;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    Button nuevapregunta;
    TextView tituloPregunta, tituloJuego, contadorPreguntas, txt1, txt2, txt3, txt4;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma;
    Juego juego = new Juego();
    LottieAnimationView checkedDone1;
    LottieAnimationView checkedDone2;
    LottieAnimationView checkedDone3;
    LottieAnimationView checkedDone4;
    int posicion;
    public int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pregunta);
        nuevapregunta = findViewById(R.id.nueva_pregunta_visor);
        editarPregunta = findViewById(R.id.editar_pregunta);
        borrarPregunta = findViewById(R.id.eliminar_pregunta);
        preguntaAnterior = findViewById(R.id.pregunta_anterior);
        preguntaSiguiente = findViewById(R.id.siguiente_pregunta);
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
                posicion = 0;
                tituloPregunta.setText(preguntas.get(0).getTitulo_pregunta());
                tituloJuego.setText(juego.getJuego_nombre());
                setearOpciones(preguntas.get(0).getPregunta_id());
                contadorPreguntas.setText((posicion + 1) + " / " + preguntas.size());


            }
        });

        //NUEVA PREGUNTA
        nuevapregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listadoPreguntas.observe(VisorPregunta.this, new Observer<List<Pregunta>>() {
                    @Override
                    public void onChanged(List<Pregunta> preguntas) {
                        if ((preguntas.size()) + 1 <= 10) {
                            Intent intent = new Intent(getApplicationContext(), DefinirPregunta.class);
                            intent.putExtra("juegoNuevo", juego);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ya ha alcanzado el máximo de 10 preguntas, no puede crear más preguntas",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });

        //EDITAR PREGUNTA

        editarPregunta.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Editar: en desarrollo",
                Toast.LENGTH_LONG).show());

        //BORRAR PREGUNTA
        borrarPregunta.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"Borrar: en desarrollo",
                Toast.LENGTH_LONG).show());

        //PREGUNTA SIGUIENTE

        preguntaSiguiente.setOnClickListener(v -> {
            posicion++;
            listadoPreguntas.observe(VisorPregunta.this, preguntas -> {
                int sizePreguntas = preguntas.size();
                if (posicion <= sizePreguntas - 1) {
                    tituloPregunta.setText(preguntas.get(posicion).getTitulo_pregunta());
                    tituloJuego.setText(juego.getJuego_nombre());
                    setearOpciones(preguntas.get(posicion).getPregunta_id());
                    contadorPreguntas.setText((posicion + 1) + " / " + preguntas.size());
                } else {
                    posicion--;
                }
            });


        });

        //PREGUNTA ANTERIOR
        preguntaAnterior.setOnClickListener(v -> {
            posicion--;
            listadoPreguntas.observe(VisorPregunta.this, preguntas -> {

                if (0 <= posicion) {
                    tituloPregunta.setText(preguntas.get(posicion).getTitulo_pregunta());
                    tituloJuego.setText(juego.getJuego_nombre());
                    setearOpciones(preguntas.get(posicion).getPregunta_id());
                    contadorPreguntas.setText((posicion + 1) + " / " + preguntas.size());
                } else {
                    posicion++;
                }
            });

        });

    }


    private void setearOpciones(int id) {

        listaOpciones = opcionViewModel.getOcionesByIdPregunta(id);
        listaOpciones.observe(VisorPregunta.this, opciones -> {
            int size = (opciones.size()) - 1;
            int count = contador;
            while (count <= size) {
                pictograma = pictogramaViewModel.getPictogramaById(opciones.get(count).getPictograma_id());
                //seteado de imagen y texto del pictograma
                int finalCount = count;
                pictograma.observe(VisorPregunta.this, pictograma -> {
                    switch (finalCount) {
                        case 0:
                            opcionBoton1.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            txt1.setText(pictograma.getPictograma_nombre());

                            // seteado de checkbox
                            if (opciones.get(0).isOpcion_respuesta()) {
                                checkedDone1.setMinAndMaxProgress(1.0f, 1.0f);
                            } else {
                                checkedDone1.setMinAndMaxProgress(0.0f, 0.0f);
                            }
                            checkedDone1.playAnimation();
                            break;

                        case 1:
                            opcionBoton2.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            txt2.setText(pictograma.getPictograma_nombre());
                            // seteado de checkbox
                            if (opciones.get(1).isOpcion_respuesta()) {
                                checkedDone2.setMinAndMaxProgress(1.0f, 1.0f);
                            } else {
                                checkedDone2.setMinAndMaxProgress(0.0f, 0.0f);
                            }
                            checkedDone2.playAnimation();
                            break;


                        case 2:
                            opcionBoton3.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            txt3.setText(pictograma.getPictograma_nombre());
                            // seteado de checkbox
                            if (opciones.get(2).isOpcion_respuesta()) {
                                checkedDone3.setMinAndMaxProgress(1.0f, 1.0f);
                            } else {
                                checkedDone3.setMinAndMaxProgress(0.0f, 0.0f);
                            }
                            checkedDone3.playAnimation();
                            break;

                        case 3:
                            opcionBoton4.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            txt4.setText(pictograma.getPictograma_nombre());
                            // seteado de checkbox
                            if (opciones.get(3).isOpcion_respuesta()) {
                                checkedDone4.setMinAndMaxProgress(1.0f, 1.0f);
                            } else {
                                checkedDone4.setMinAndMaxProgress(0.0f, 0.0f);
                            }
                            checkedDone4.playAnimation();
                            break;

                    }


                });
                count++;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        listadoPreguntas = null;
        listaOpciones = null;
        preguntaViewModel=null;
        pictograma=null;
        juego=null;
        pictogramaViewModel=null;
        opcionViewModel=null;

        Runtime.getRuntime().gc();

    }
}