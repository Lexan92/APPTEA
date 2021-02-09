package com.teakids.apptea.ui.juegoMemoria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.juego.BuscarPictograma;
import com.teakids.apptea.ui.juego.OpcionViewModel;
import com.teakids.apptea.ui.juego.PreguntaViewModel;
import com.teakids.apptea.ui.pictograma.PictogramaViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import roomsqlite.dao.PreguntaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;

public class VisorMemoria extends AppCompatActivity {
    TextView nombreJuego, picto_uno, picto_dos, picto_tres;
    Button guardar, cancelar, agregar, borrar, editar, salir;
    ImageButton anterior, siguiente;
    ImageView boton_uno, boton_dos, boton_tres;
    MaterialCardView card1, card2, card3;
    boolean ban1 = false, ban2 = false, ban3 = false;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public int count = 0;
    int id_picto_1, id_picto_2, id_picto_3;
    LiveData<Pictograma> pictogramaUno, pictogramaDos, pictogramaTres;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma;
    PictogramaViewModel pictogramaViewModel;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    Opcion opcion1, opcion2, opcion3;
    boolean ban_editar = false;
    boolean ban_agrega_nivel = false;
    public int cantidad_pregunta=0;
    public int id_pregunta=0;
    AdministarSesion idioma ;

    //posicion del nivel
    int posicion;

    int cuenta = 0;
    Juego juego;


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
        salir = findViewById(R.id.btn_salir);
        editar = findViewById(R.id.editar_nivel);
        card1 = findViewById(R.id.card_1);
        card2 = findViewById(R.id.card_2);
        card3 = findViewById(R.id.card_3);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcion1 = new Opcion();
        opcion2 = new Opcion();
        opcion3 = new Opcion();
        idioma = new AdministarSesion(getApplicationContext());

        PreguntaDAO preguntaDAO = appDatabase.getDatabase(getApplicationContext()).preguntaDao();
        Pregunta preguntaNueva = new Pregunta();


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
        cancelar.setVisibility(View.INVISIBLE);
        salir.setVisibility(View.INVISIBLE);

        //Seteo nombre del juego

        juego = (Juego) getIntent().getSerializableExtra("juego");
        if(idioma.getIdioma()==1){
            nombreJuego.setText(juego.getJuego_nombre());
        }else{
            nombreJuego.setText(juego.getName_game());}


        llenadoOpciones();


        //Listener para los tres espacios de pictogramas
        card1.setOnClickListener(v -> {
            if (editar.getVisibility() != View.VISIBLE || ban_editar) {
                Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                startActivityForResult(intent, UNO);
            }

        });

        card2.setOnClickListener(v -> {
            if (editar.getVisibility() != View.VISIBLE || ban_editar) {
                Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                startActivityForResult(intent, DOS);
            }
        });

        card3.setOnClickListener(v -> {
            if (editar.getVisibility() != View.VISIBLE || ban_editar) {
                Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                startActivityForResult(intent, TRES);
            }
        });


        //listener boton cancelar
        cancelar.setOnClickListener(v -> {


            Intent intent = new Intent(VisorMemoria.this, VisorMemoria.class);
            intent.putExtra("juego", juego);
            intent.putExtra("ban_listado", true);
            startActivity(intent);
            finish();

        });

        //listener boton guardar
        guardar.setOnClickListener(v -> {

            if (editar.getVisibility() == View.INVISIBLE) {
                if (ban1 && ban2 && ban3) {

                    preguntaNueva.setJuego_id(juego.getJuego_id());
                    preguntaNueva.setTitulo_pregunta("null");
                    preguntaNueva.setName_pregunta("null");
                    preguntaDAO.insertPregunta(preguntaNueva);
                    //se obtiene el ID de la pregunta insertada
                    Pregunta pregunta1 = preguntaDAO.obtenerUltimaPregunta();


                    //insertando opcion 1
                    opcion1.setPregunta_id(pregunta1.getPregunta_id());
                    opcion1.setPictograma_id(id_picto_1);
                    opcion1.setOpcion_respuesta(true);
                    opcionViewModel.insert(opcion1);
                    opcionViewModel.insert(opcion1);

                    //insertando opcion 2

                    opcion2.setPregunta_id(pregunta1.getPregunta_id());
                    opcion2.setPictograma_id(id_picto_2);
                    opcion2.setOpcion_respuesta(true);
                    opcionViewModel.insert(opcion2);
                    opcionViewModel.insert(opcion2);


                    //insertando opcion 3

                    opcion3.setPregunta_id(pregunta1.getPregunta_id());
                    opcion3.setPictograma_id(id_picto_3);
                    opcion3.setOpcion_respuesta(true);
                    opcionViewModel.insert(opcion3);
                    opcionViewModel.insert(opcion3);

                    guardar.setVisibility(View.INVISIBLE);
                    cancelar.setVisibility(View.INVISIBLE);
                    editar.setVisibility(View.VISIBLE);
                    agregar.setVisibility(View.VISIBLE);
                    borrar.setVisibility(View.VISIBLE);
                    salir.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.nivelGuardado), Toast.LENGTH_LONG).show();


                }
            } else if (ban_editar) {


                    //Boton guardar activado por la opcion de editar nivel
                    listaOpciones.observe(VisorMemoria.this, opcions -> {


                        if(ban1){
                            opcions.get(0).setPictograma_id(id_picto_1);
                            opcions.get(1).setPictograma_id(id_picto_1);
                            opcionViewModel.update(opcions.get(0));
                            opcionViewModel.update(opcions.get(1));

                        }

                        if(ban2){
                            opcions.get(2).setPictograma_id(id_picto_2);
                            opcions.get(3).setPictograma_id(id_picto_2);
                            opcionViewModel.update(opcions.get(2));
                            opcionViewModel.update(opcions.get(3));


                        }

                        if (ban3){
                            opcions.get(4).setPictograma_id(id_picto_3);
                            opcions.get(5).setPictograma_id(id_picto_3);
                            opcionViewModel.update(opcions.get(4));
                            opcionViewModel.update(opcions.get(5));

                        }




                    });


                listaOpciones.removeObservers(VisorMemoria.this);
                guardar.setVisibility(View.INVISIBLE);
                cancelar.setVisibility(View.INVISIBLE);
                editar.setVisibility(View.VISIBLE);
                siguiente.setVisibility(View.VISIBLE);
                anterior.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nivelActualizado), Toast.LENGTH_SHORT).show();
            }
        });


        //listener editar nivel
        editar.setOnClickListener(v -> {
            guardar.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.VISIBLE);
            siguiente.setVisibility(View.INVISIBLE);
            anterior.setVisibility(View.INVISIBLE);
            ban_editar = true;
            ban_agrega_nivel = true;


        });


        //listener borrar nivel
        borrar.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(VisorMemoria.this);

            builder.setTitle(getResources().getString(R.string.eliminarNivel));
            builder.setMessage(getResources().getString(R.string.elNivelSeraElimi));
            builder.setNegativeButton(getResources().getString(R.string.cancelar), (dialog, which) -> {
            });
            builder.setPositiveButton(getResources().getString(R.string.aceptar), (dialog, which) -> {
                listadoPreguntas.observe(VisorMemoria.this, preguntas -> {
                    if (preguntas.size() != 0) {
                        Pregunta preguntaBorrar = preguntas.get(posicion);
                        Log.d("TAG","PREGUNTA id:".concat(Integer.toString(preguntaBorrar.getPregunta_id())).concat(" POSICION: ").concat(Integer.toString(posicion)));
                        borrarPregunta(preguntaBorrar);
                        finish();

                    }

                });
                listadoPreguntas.removeObservers(VisorMemoria.this);
            });

            builder.show();
        });

        //listener agregar nivel
        agregar.setOnClickListener(v -> {
            ban1=false;
            ban2=false;
            ban3=false;
            ban_agrega_nivel = true;
            reiniciarvistaOpciones();
            editar.setVisibility(View.INVISIBLE);
            borrar.setVisibility(View.INVISIBLE);
            anterior.setVisibility(View.INVISIBLE);
            siguiente.setVisibility(View.INVISIBLE);


        });

        //listener nivel siguiente
        siguiente.setOnClickListener(v -> {
            anterior.setVisibility(View.VISIBLE);

            posicion++;
            listadoPreguntas.observe(VisorMemoria.this, preguntas -> {
                int sizePreguntas = preguntas.size();
                if (posicion <= sizePreguntas - 1) {
                    setearOpciones(preguntas.get(posicion).getPregunta_id());
                } else {
                    siguiente.setVisibility(View.INVISIBLE);
                    posicion--;
                }
            });


        });


        //listener nivel anterior
        anterior.setOnClickListener(v -> {
            siguiente.setVisibility(View.VISIBLE);
            posicion--;
            listadoPreguntas.observe(VisorMemoria.this, preguntas -> {
                if (0 <= posicion) {
                    setearOpciones(preguntas.get(posicion).getPregunta_id());
                } else {
                    anterior.setVisibility(View.INVISIBLE);
                    posicion++;
                }
            });


        });

        salir.setOnClickListener(v -> {
            finish();
        });

    }

    private  void llenadoOpciones(){
        //setear opciones
        boolean ban = getIntent().getBooleanExtra("ban_listado", false);
        if (ban) {
            agregar.setVisibility(View.VISIBLE);
            editar.setVisibility(View.VISIBLE);
            borrar.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.INVISIBLE);
            posicion = 0;
            listadoPreguntas = preguntaViewModel.getPreguntasByIdJuego(juego.getJuego_id());
            listadoPreguntas.observe(VisorMemoria.this, preguntas -> {
                cantidad_pregunta = preguntas.size();
                switch (cantidad_pregunta) {
                    case 0:
                        editar.setVisibility(View.INVISIBLE);
                        agregar.setVisibility(View.INVISIBLE);
                        borrar.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        setearOpciones(preguntas.get(posicion).getPregunta_id());
                        agregar.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setearOpciones(preguntas.get(posicion).getPregunta_id());
                        agregar.setVisibility(View.VISIBLE);
                        siguiente.setVisibility(View.VISIBLE);
                        anterior.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        setearOpciones(preguntas.get(posicion).getPregunta_id());
                        agregar.setVisibility(View.INVISIBLE);
                        siguiente.setVisibility(View.VISIBLE);
                        anterior.setVisibility(View.VISIBLE);
                        break;

            }
            });


        }
    }

    private void borrarPregunta(Pregunta pregunta_borrar) {

        listadoPreguntas.removeObservers(VisorMemoria.this);
        listaOpciones.removeObservers(VisorMemoria.this);
        pictograma.removeObservers(VisorMemoria.this);

        preguntaViewModel.delete(pregunta_borrar);
        Intent intent = new Intent(VisorMemoria.this, VisorMemoria.class);
        intent.putExtra("juego", juego);
        intent.putExtra("ban_listado", true);
        startActivity(intent);
        finish();
    }

    private void setearOpciones(int pregunta_id) {


        listaOpciones = opcionViewModel.getOcionesByIdPregunta(pregunta_id);
        listaOpciones.observe(VisorMemoria.this, opciones -> {
            if (opciones.size() != 0) {
                int contador = count;
                while (contador <= 5) {
                    pictograma = pictogramaViewModel.getPictogramaById(opciones.get(contador).getPictograma_id());
                    int finalContador = contador;
                    pictograma.observe(VisorMemoria.this, pictograma -> {
                        int cuenta = finalContador;
                        switch (cuenta) {
                            case 0:
                                picto_uno.setVisibility(View.VISIBLE);
                                id_picto_1 = pictograma.getPictograma_id();
                                boton_uno.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    picto_uno.setText(pictograma.getPictograma_nombre());
                                }else{
                                    picto_uno.setText(pictograma.getPictograma_name());}

                                break;

                            case 2:
                                picto_dos.setVisibility(View.VISIBLE);
                                id_picto_2 = pictograma.getPictograma_id();
                                boton_dos.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    picto_dos.setText(pictograma.getPictograma_nombre());
                                }else{
                                    picto_dos.setText(pictograma.getPictograma_name());}

                                break;

                            case 4:
                                picto_tres.setVisibility(View.VISIBLE);
                                id_picto_3 = pictograma.getPictograma_id();
                                boton_tres.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    picto_tres.setText(pictograma.getPictograma_nombre());
                                }else{
                                    picto_tres.setText(pictograma.getPictograma_name());}

                        }


                    });
                    contador = contador + 2;

                }
            } else {
                llenadoOpciones();

            }

        });



    }


    private void reiniciarvistaOpciones() {
        boton_uno.setImageResource(R.drawable.ic_agregar);
        boton_dos.setImageResource(R.drawable.ic_agregar);
        boton_tres.setImageResource(R.drawable.ic_agregar);
        picto_uno.setVisibility(View.INVISIBLE);
        picto_dos.setVisibility(View.INVISIBLE);
        picto_tres.setVisibility(View.INVISIBLE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UNO) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaUno = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaUno.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(boton_uno);
                    if(idioma.getIdioma()==1){
                        picto_uno.setText(pictograma.getPictograma_nombre());
                    }else{
                        picto_uno.setText(pictograma.getPictograma_name());}

                    picto_uno.setVisibility(View.VISIBLE);
                    ban1 = true;
                    id_picto_1 = pictograma.getPictograma_id();

                    if (ban1 && ban2 && ban3) {
                        guardar.setVisibility(View.VISIBLE);
                        cancelar.setVisibility(View.VISIBLE);
                    }

                });
            }
        }

        if (requestCode == DOS) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaDos = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaDos.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(boton_dos);
                    if(idioma.getIdioma()==1){
                        picto_dos.setText(pictograma.getPictograma_nombre());
                    }else{
                        picto_dos.setText(pictograma.getPictograma_name());}

                    picto_dos.setVisibility(View.VISIBLE);
                    ban2 = true;
                    id_picto_2 = pictograma.getPictograma_id();

                    if (ban1 && ban2 && ban3) {
                        guardar.setVisibility(View.VISIBLE);
                        cancelar.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        if (requestCode == TRES) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaTres = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaTres.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(boton_tres);
                    if(idioma.getIdioma()==1){
                        picto_tres.setText(pictograma.getPictograma_nombre());
                    }else{
                        picto_tres.setText(pictograma.getPictograma_name());}

                    picto_tres.setVisibility(View.VISIBLE);
                    ban3 = true;
                    id_picto_3 = pictograma.getPictograma_id();

                    if (ban1 && ban2 && ban3) {
                        guardar.setVisibility(View.VISIBLE);
                        cancelar.setVisibility(View.VISIBLE);

                    }
                });
            }
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }
}