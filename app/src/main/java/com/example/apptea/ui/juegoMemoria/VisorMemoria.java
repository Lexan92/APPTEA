package com.example.apptea.ui.juegoMemoria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.juego.BuscarPictograma;
import com.example.apptea.ui.juego.OpcionViewModel;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.atomic.AtomicReference;

import roomsqlite.dao.PreguntaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;

public class VisorMemoria extends AppCompatActivity {
    TextView nombreJuego, picto_uno, picto_dos, picto_tres;
    Button guardar, cancelar, agregar, borrar, editar;
    ImageButton anterior, siguiente;
    ImageView boton_uno, boton_dos, boton_tres;
    MaterialCardView card1, card2, card3;
    boolean ban1 = false, ban2 = false, ban3 = false;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    int id_picto_1, id_picto_2,id_picto_3;
    LiveData<Pictograma> pictogramaUno, pictogramaDos, pictogramaTres;
    PictogramaViewModel pictogramaViewModel;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_memoria);
        nombreJuego = findViewById(R.id.text_nombre_juego_memoria);
        picto_uno = findViewById(R.id.titulo_pictograma_uno);
        picto_dos = findViewById(R.id.titulo_pictograma_dos);
        picto_tres = findViewById(R.id.titulo_pictograma_tres);
        boton_uno = findViewById(R.id.opcion_uno);
        boton_dos = findViewById(R.id.opcion_dos);
        boton_tres = findViewById(R.id.opcion_tres);
        anterior = findViewById(R.id.img_btn_anterior);
        siguiente = findViewById(R.id.img_btn_siguiente);
        guardar = findViewById(R.id.btn_guardar_memoria);
        cancelar = findViewById(R.id.btn_cancelar_memoria);
        agregar = findViewById(R.id.btn_agrega_nivel);
        borrar = findViewById(R.id.btn_borra_nivel);
        editar = findViewById(R.id.editar_nivel);
        card1 = findViewById(R.id.card_1);
        card2 = findViewById(R.id.card_2);
        card3 = findViewById(R.id.card_3);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);


        PreguntaDAO preguntaDAO = appDatabase.getDatabase(getApplicationContext()).preguntaDao();
        Pregunta preguntaNueva = new Pregunta();
        AtomicReference<Boolean> clickEditar = new AtomicReference<>(false);

        //textos al inicio vacios
        picto_uno.setVisibility(View.INVISIBLE);
        picto_dos.setVisibility(View.INVISIBLE);
        picto_tres.setVisibility(View.INVISIBLE);
        //botones ocultos al inicio
        guardar.setVisibility(View.INVISIBLE);
        editar.setVisibility(View.INVISIBLE);
        agregar.setVisibility(View.INVISIBLE);
        borrar.setVisibility(View.INVISIBLE);
        siguiente.setVisibility(View.INVISIBLE);
        anterior.setVisibility(View.INVISIBLE);


        //Seteo nombre del juego
        Juego juego;
        juego = (Juego) getIntent().getSerializableExtra("juego");
        nombreJuego.setText(juego.getJuego_nombre());

        //Listener para los tres espacios de pictogramas
            card1.setOnClickListener(v -> {
                if(editar.getVisibility()!=View.VISIBLE){
                    Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                    startActivityForResult(intent, UNO);
                }

            });

            card2.setOnClickListener(v -> {
                if(editar.getVisibility()!=View.VISIBLE) {
                    Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                    startActivityForResult(intent, DOS);
                }
            });

            card3.setOnClickListener(v -> {
                if(editar.getVisibility()!=View.VISIBLE) {
                Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                startActivityForResult(intent, TRES);
                }
            });



        //listener boton cancelar
        cancelar.setOnClickListener(v -> {
            finish();
        });

        //listener boton guardar
        guardar.setOnClickListener(v -> {

            if(!clickEditar.get()){
                if (ban1 && ban2 && ban3) {

                    preguntaNueva.setJuego_id(juego.getJuego_id());
                    preguntaNueva.setTitulo_pregunta("null");
                    preguntaDAO.insertPregunta(preguntaNueva);
                    //se obtiene el ID de la pregunta insertada
                    Pregunta pregunta1 = preguntaDAO.obtenerUltimaPregunta();

                    //insertando opcion 1
                    final Opcion opcion1 = new Opcion();
                    opcion1.setPregunta_id(pregunta1.getPregunta_id());
                    opcion1.setPictograma_id(id_picto_1);
                    opcion1.setOpcion_respuesta(true);
                    opcionViewModel.insert(opcion1);
                    opcionViewModel.insert(opcion1);

                    //insertando opcion 2
                    final Opcion opcion2 = new Opcion();
                    opcion2.setPregunta_id(pregunta1.getPregunta_id());
                    opcion2.setPictograma_id(id_picto_2);
                    opcion2.setOpcion_respuesta(true);
                    opcionViewModel.insert(opcion2);
                    opcionViewModel.insert(opcion2);


                    //insertando opcion 3
                    final Opcion opcion3 = new Opcion();
                    opcion3.setPregunta_id(pregunta1.getPregunta_id());
                    opcion3.setPictograma_id(id_picto_3);
                    opcion3.setOpcion_respuesta(true);
                    opcionViewModel.insert(opcion3);
                    opcionViewModel.insert(opcion3);

                    guardar.setVisibility(View.INVISIBLE);
                    cancelar.setVisibility(View.INVISIBLE);
                    editar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Nivel Guardado",Toast.LENGTH_LONG).show();



                }
            }else if (clickEditar.get()){
                //Boton guardar activado por la opcion de editar nivel
                Toast.makeText(getApplicationContext(),"click guardar editar",Toast.LENGTH_LONG).show();




            }


        });


        //listener editar nivel
        editar.setOnClickListener(v -> {
            clickEditar.set(true);
            guardar.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.VISIBLE);
            editar.setVisibility(View.INVISIBLE);

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UNO) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaUno = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaUno.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(boton_uno);
                    picto_uno.setText(pictograma.getPictograma_nombre());
                    picto_uno.setVisibility(View.VISIBLE);
                    ban1 = true;
                    id_picto_1 = pictograma.getPictograma_id();

                    if (ban1 && ban2 && ban3) {
                        guardar.setVisibility(View.VISIBLE);
                        agregar.setVisibility(View.VISIBLE);
                    }

                });
            }
        }

        if (requestCode == DOS) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaDos = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaDos.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(boton_dos);
                    picto_dos.setText(pictograma.getPictograma_nombre());
                    picto_dos.setVisibility(View.VISIBLE);
                    ban2 = true;
                    id_picto_2=pictograma.getPictograma_id();

                    if (ban1 && ban2 && ban3) {
                        guardar.setVisibility(View.VISIBLE);
                        agregar.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        if (requestCode == TRES) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaTres = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaTres.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(boton_tres);
                    picto_tres.setText(pictograma.getPictograma_nombre());
                    picto_tres.setVisibility(View.VISIBLE);
                    ban3 = true;
                    id_picto_3=pictograma.getPictograma_id();

                    if (ban1 && ban2 && ban3) {
                        guardar.setVisibility(View.VISIBLE);
                        agregar.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

    }
}