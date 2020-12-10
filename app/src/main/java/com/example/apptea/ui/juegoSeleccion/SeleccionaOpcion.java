package com.example.apptea.ui.juegoSeleccion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;
import com.example.apptea.ui.juego.FinJuego;
import com.example.apptea.ui.juego.OpcionViewModel;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.juegoMemoria.FinJuegoMemoria;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.TTSManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.DetalleResultado;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;
import roomsqlite.entidades.Resultado;

public class SeleccionaOpcion extends AppCompatActivity {

    MaterialCardView opcion1, opcion2, opcion3, opcion4;
    LottieAnimationView globos;
    TextView tituloPregunta, tituloJuego, txt1, txt2, txt3, txt4;
    boolean bandera1, bandera2, bandera3, bandera4, duracion=false;
    Button siguiente;
    ImageView imagen1, imagen2, imagen3, imagen4;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    DetalleResultadoViewModel detalleResultadoViewModel;
    Juego juego = new Juego();
    Resultado resultado = new Resultado();
    DetalleResultado detalleResultado= new DetalleResultado();
    AdministarSesion sesion;
    TTSManager ttsManager = null;
    int longitudPreguntas;
    int posicion = 0;
    int contador = 0;
    int numeroCorrectas = 0;
    int numeroFallos=0;
    private int milisegundos = 1000;
    String pregunta;


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
        globos = findViewById(R.id.lottie_globos);
        juego = (Juego) getIntent().getSerializableExtra("juego");
        resultado = (Resultado) getIntent().getSerializableExtra("resultado");
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        detalleResultadoViewModel = new ViewModelProvider(this).get(DetalleResultadoViewModel.class);
        sesion = new AdministarSesion(this);
        ttsManager = new TTSManager();
        ttsManager.init(getApplicationContext());

        //boton oculto
        siguiente.setVisibility(View.INVISIBLE);
        //reiniciar vistas
        reiniciarCards();


        //seteado de nombre del juego
        tituloJuego.setText(juego.getJuego_nombre());
        listadoPreguntas = preguntaViewModel.getPreguntasByIdJuego(juego.getJuego_id());
        listadoPreguntas.observe(SeleccionaOpcion.this, preguntas -> {
            longitudPreguntas = preguntas.size();
        });


        setearOpciones(posicion);

        //ONCLICK DE OPCIONES POR PREGUNTA , LISTENER CARDVIEWS,
        opcion1.setOnClickListener(v -> {
            ttsManager.initQueue(txt1.getText().toString());
            //SI RESPUESTA ES CORRECTA
            if (bandera1) {
                opcion1.setCardBackgroundColor(getResources().getColor(R.color.correcto));
                globos.setSpeed(2);
                globos.playAnimation();
                numeroCorrectas--;
                if (numeroCorrectas <= 0){
                    siguiente.setVisibility(View.VISIBLE);
                }
            //SI RESPUESTA ES INCORRECTA
            } else {
                opcion1.setCardBackgroundColor(getResources().getColor(R.color.incorrecto));
                numeroFallos++;
            }
            txt1.setTextColor(getResources().getColor(R.color.colorSplash));
        });

        opcion2.setOnClickListener(v -> {
            ttsManager.initQueue(txt2.getText().toString());
            if (bandera2) {
                opcion2.setCardBackgroundColor(getResources().getColor(R.color.correcto));
                globos.setSpeed(2);
                globos.playAnimation();
                numeroCorrectas--;
                if (numeroCorrectas <= 0) {
                    siguiente.setVisibility(View.VISIBLE);
                }
            } else {
                opcion2.setCardBackgroundColor(getResources().getColor(R.color.incorrecto));
                numeroFallos++;
            }
            txt2.setTextColor(getResources().getColor(R.color.colorSplash));

        });

        opcion3.setOnClickListener(v -> {
            ttsManager.initQueue(txt3.getText().toString());
            if (bandera3) {
                opcion3.setCardBackgroundColor(getResources().getColor(R.color.correcto));
                globos.setSpeed(2);
                globos.playAnimation();
                numeroCorrectas--;
                if (numeroCorrectas <= 0) {
                    siguiente.setVisibility(View.VISIBLE);
                }
            } else {
                opcion3.setCardBackgroundColor(getResources().getColor(R.color.incorrecto));
                numeroFallos++;
            }
            txt3.setTextColor(getResources().getColor(R.color.colorSplash));

        });

        opcion4.setOnClickListener(v -> {
            ttsManager.initQueue(txt4.getText().toString());
            if (bandera4) {
                opcion4.setCardBackgroundColor(getResources().getColor(R.color.correcto));
                globos.setSpeed(2);
                globos.playAnimation();
                numeroCorrectas--;
                if (numeroCorrectas <= 0) {
                    siguiente.setVisibility(View.VISIBLE);
                }
            } else {
                opcion4.setCardBackgroundColor(getResources().getColor(R.color.incorrecto));
                numeroFallos++;
            }
            txt4.setTextColor(getResources().getColor(R.color.colorSplash));

        });

        //LISTENER BOTON SIGUIENTE
        siguiente.setOnClickListener(v -> {

            posicion++;

            if(sesion.obtenerTipoUsuario()==1) {
                detalleResultado.setResultado_id(resultado.getResultado_id());
                detalleResultado.setNombre_pregunta(pregunta);
                detalleResultado.setCantidad_fallos(numeroFallos);
                detalleResultadoViewModel.insertResultado(detalleResultado);
                if (posicion <= longitudPreguntas - 1) {
                    reiniciarCards();
                    setearOpciones(posicion);
                } else {
                    Intent intent = new Intent(this, FinJuego.class);
                    intent.putExtra("resultado",resultado);
                    startActivity(intent);
                    finish();
                }
            }else{
                System.out.println("NO ES PACIENTE");
            }

            /*
            if (posicion <= longitudPreguntas - 1) {
                if(sesion.obtenerTipoUsuario()==1){
                    System.out.println("Resultado id "+ resultado.getResultado_id());
                    detalleResultado.setResultado_id(resultado.getResultado_id());
                    detalleResultado.setNombre_pregunta(pregunta);
                    detalleResultado.setCantidad_fallos(numeroFallos);
                    detalleResultadoViewModel.insertResultado(detalleResultado);
                }
                reiniciarCards();
                setearOpciones(posicion);
            } else {
                Intent intent = new Intent(this, FinJuego.class);
                intent.putExtra("resultado",resultado);
                startActivity(intent);
                finish();
            }*/


        });


    }


    //funcion que setea cardviews con pictogramas, recibe la posicion en la lista de las preguntas
    //SETEA CANTIDAD DE RESPUESTAS BUENAS POR PREGUNTA
    private void setearOpciones(int posicion) {
        //seteo del nombre de la pregunta
        listadoPreguntas.observe(SeleccionaOpcion.this, preguntas -> {
            tituloPregunta.setText(preguntas.get(posicion).getTitulo_pregunta());
            pregunta = preguntas.get(posicion).getTitulo_pregunta();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ttsManager.initQueue(tituloPregunta.getText().toString());
                }
            },milisegundos);
            listaOpciones = opcionViewModel.getOcionesByIdPregunta(preguntas.get(posicion).getPregunta_id());

            listaOpciones.observe(SeleccionaOpcion.this, opciones -> {
                List<Opcion> nuevaListaOpcion = ordenarAleatoriamente(opciones);
                int size = (nuevaListaOpcion.size()) - 1;
                int count = contador;
                //seteado de las opciones
                while (count <= size) {
                    pictograma = pictogramaViewModel.getPictogramaById(nuevaListaOpcion.get(count).getPictograma_id());
                    //seteado de imagenes y texto del pictograma
                    int finalCount = count;
                    pictograma.observe(SeleccionaOpcion.this, pictograma1 -> {
                        switch (finalCount) {
                            case 0:
                                imagen1.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                txt1.setText(pictograma1.getPictograma_nombre());
                                bandera1 = opciones.get(0).isOpcion_respuesta();
                                opcion1.setVisibility(View.VISIBLE);
                                if (opciones.get(0).isOpcion_respuesta())
                                    numeroCorrectas++;
                                System.out.println("numeroCorrectas seteada op1" + numeroCorrectas);
                                break;
                            case 1:
                                imagen2.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                txt2.setText(pictograma1.getPictograma_nombre());
                                bandera2 = opciones.get(1).isOpcion_respuesta();
                                opcion2.setVisibility(View.VISIBLE);
                                if (opciones.get(1).isOpcion_respuesta())
                                    numeroCorrectas++;
                                System.out.println("numeroCorrectas seteada op2" + numeroCorrectas);
                                break;
                            case 2:
                                imagen3.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                txt3.setText(pictograma1.getPictograma_nombre());
                                bandera3 = opciones.get(2).isOpcion_respuesta();
                                opcion3.setVisibility(View.VISIBLE);
                                if (opciones.get(2).isOpcion_respuesta())
                                    numeroCorrectas++;
                                System.out.println("numeroCorrectas seteada op3 " + numeroCorrectas);
                                break;
                            case 3:
                                imagen4.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                txt4.setText(pictograma1.getPictograma_nombre());
                                bandera4 = opciones.get(3).isOpcion_respuesta();
                                opcion4.setVisibility(View.VISIBLE);
                                if (opciones.get(3).isOpcion_respuesta())
                                    numeroCorrectas++;
                                System.out.println("numeroCorrectas seteada op 4 " + numeroCorrectas);
                                break;
                        }
                    });
                    count++;
                }

            });
        });


    }


    //metodo que reinicia las vistas de cada cardview
    private void reiniciarCards() {
        numeroFallos=0;
        opcion1.setVisibility(View.INVISIBLE);
        opcion2.setVisibility(View.INVISIBLE);
        opcion3.setVisibility(View.INVISIBLE);
        opcion4.setVisibility(View.INVISIBLE);
        siguiente.setVisibility(View.INVISIBLE);
        opcion1.setCardBackgroundColor(getResources().getColor(R.color.colorSplash));
        txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
        opcion2.setCardBackgroundColor(getResources().getColor(R.color.colorSplash));
        txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
        opcion3.setCardBackgroundColor(getResources().getColor(R.color.colorSplash));
        txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
        opcion4.setCardBackgroundColor(getResources().getColor(R.color.colorSplash));
        txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
    }


    //funcion que devuelve una lista ordenada de forma aleatoria de opciones
    private List<Opcion> ordenarAleatoriamente(List<Opcion> opciones) {

        /* Función de barajamiento usando el algoritmo Fisher Yates
         *  Se recibe un arreglo de Preguntas (ordenado o no) y se aplica
         *  el algoritmo de Fisher - Yates
         *
         *  Se devuelve un arreglo de preguntas desordenado aleatoriamente
         */

        for (int i = opciones.size() - 1; i > 0; i--) {
            int indice = (int) Math.floor(Math.random() * (i + 1));
            Opcion tmp = opciones.get(i);
            opciones.set(i, opciones.get(indice));
            opciones.set(indice, tmp);
        }

        return opciones;

    }
}