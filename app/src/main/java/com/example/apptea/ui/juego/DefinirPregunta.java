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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.snackbar.Snackbar;

import roomsqlite.dao.PreguntaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;

public class DefinirPregunta extends AppCompatActivity {

    boolean isCheckedOpcionUno = false;
    boolean isCheckedOpcionDos = false;
    boolean isCheckedOpcionTres = false;
    boolean isCheckedOpcionCuatro = false;
    boolean agrego1 = false, agrego2 = false, agrego3 = false, agrego4 = false;
    JuegoViewModel juegoViewModel;
    PictogramaViewModel pictogramaViewModel;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    LiveData<Pictograma> pictogramaUno, pictogramaDos, pictogramaTres, pictogramaCuatro;
    LiveData<Juego> juego;
    Juego juegoNuevo = new Juego();
    EditText tituloPregunta;
    ImageButton opcionBoton1, opcionBoton2, opcionBoton3, opcionBoton4;
    Button guardar;
    TextView txt1, txt2, txt3, txt4, nombreJuego;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final int CUATRO = 4;
    LottieAnimationView checkedDone1;
    LottieAnimationView checkedDone2;
    LottieAnimationView checkedDone3;
    LottieAnimationView checkedDone4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_pregunta);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);

        //elementos de lottiAnimation, uno por cada checkbox
        checkedDone1 = findViewById(R.id.check_opcion_uno);
        checkedDone2 = findViewById(R.id.check_opcion_dos);
        checkedDone3 = findViewById(R.id.check_opcion_tres);
        checkedDone4 = findViewById(R.id.check_opcion_cuatro);
        checkedDone1.setVisibility(View.GONE);
        checkedDone2.setVisibility(View.GONE);
        checkedDone3.setVisibility(View.GONE);
        checkedDone4.setVisibility(View.GONE);

        guardar = findViewById(R.id.guardar_pregunta);
        nombreJuego = findViewById(R.id.editNombreJuego1);
        tituloPregunta = findViewById(R.id.nombre_pregunta);
        opcionBoton1 = findViewById(R.id.opcion_uno);
        opcionBoton2 = findViewById(R.id.opcion_dos);
        opcionBoton3 = findViewById(R.id.opcion_tres);
        opcionBoton4 = findViewById(R.id.opcion_cuatro);
        txt1 = findViewById(R.id.nombre_opcion_uno);
        txt2 = findViewById(R.id.nombre_opcion_dos);
        txt3 = findViewById(R.id.nombre_opcion_tres);
        txt4 = findViewById(R.id.nombre_opcion_cuatro);
        PreguntaDAO preguntaDAO = appDatabase.getDatabase(getApplicationContext()).preguntaDAO();
        Pregunta preguntaNueva = new Pregunta();
        validarObjeto();


        //Escucha del boton guardar
        guardar.setOnClickListener(v -> {

            if (!tituloPregunta.getText().toString().isEmpty()) {
                preguntaNueva.setTitulo_pregunta(tituloPregunta.getText().toString());
                if (getIntent() == null) {
                    juego.observe(DefinirPregunta.this, juego -> preguntaNueva.setJuego_id(juego.getJuego_id()));
                } else {
                    preguntaNueva.setJuego_id(juegoNuevo.getJuego_id());
                }
                //guardado de la pregunta
                preguntaViewModel.insert(preguntaNueva);

                //se obtiene el ID de la pregunta insertada
                Pregunta pregunta1 = preguntaDAO.obtenerUltimaPregunta();

                //declaracion de variables opciones

                //seteado de valores para la opcion 1

                if (agrego1) {
                    final Opcion opcion1 = new Opcion();
                    opcion1.setOpcion_respuesta(isCheckedOpcionUno);
                    opcion1.setPregunta_id(pregunta1.getPregunta_id());
                    pictogramaUno.observe(DefinirPregunta.this, pictograma -> opcion1.setPictograma_id(pictograma.getPictograma_id()));

                    //insert de opcion 1
                    opcionViewModel.insert(opcion1);
                }

                //seteado de valores para la opcion 2

                if (agrego2) {
                    final Opcion opcion2 = new Opcion();
                    opcion2.setPregunta_id(pregunta1.getPregunta_id());
                    opcion2.setOpcion_respuesta(isCheckedOpcionDos);
                    pictogramaDos.observe(DefinirPregunta.this, pictograma -> opcion2.setPictograma_id(pictograma.getPictograma_id()));
                    //insert opcion 2
                    opcionViewModel.insert(opcion2);
                }

                //seteado de valores para la opcion 3
                if (agrego3) {
                    final Opcion opcion3 = new Opcion();
                    opcion3.setPregunta_id(pregunta1.getPregunta_id());
                    opcion3.setOpcion_respuesta(isCheckedOpcionTres);
                    pictogramaTres.observe(DefinirPregunta.this, pictograma -> opcion3.setPictograma_id(pictograma.getPictograma_id()));

                    //insert opcion 3
                    opcionViewModel.insert(opcion3);
                }

                //seteado de valores para la opcion 4
                if (agrego4) {
                    final Opcion opcion4 = new Opcion();
                    opcion4.setPregunta_id(pregunta1.getPregunta_id());
                    opcion4.setOpcion_respuesta(isCheckedOpcionCuatro);
                    pictogramaCuatro.observe(DefinirPregunta.this, pictograma -> opcion4.setPictograma_id(pictograma.getPictograma_id()));
                    //insert opcion 4
                    opcionViewModel.insert(opcion4);
                }

                finish();
            } else {
                Snackbar.make(findViewById(R.id.definir_pregunta_view), "Debe ingresar un titulo para la pregunta", Snackbar.LENGTH_LONG).show();
            }

        });

        //actividades para buscar pictogramas
        opcionBoton1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
            startActivityForResult(intent, UNO);
        });

        opcionBoton2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
            startActivityForResult(intent, DOS);
        });

        opcionBoton3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
            startActivityForResult(intent, TRES);
        });

        opcionBoton4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
            startActivityForResult(intent, CUATRO);
        });


        //se definen escuchas por cada animacion
        checkedDone1.setOnClickListener(v -> {
            if (isCheckedOpcionUno) {

                checkedDone1.setSpeed(-3);
                checkedDone1.playAnimation();
                isCheckedOpcionUno = false;
            } else {
                checkedDone1.setSpeed(2);
                checkedDone1.playAnimation();
                isCheckedOpcionUno = true;
            }
        });

        checkedDone2.setOnClickListener(v -> {
            if (isCheckedOpcionDos) {
                checkedDone2.setSpeed(-3);
                checkedDone2.playAnimation();
                isCheckedOpcionDos = false;
            } else {
                checkedDone2.setSpeed(2);
                checkedDone2.playAnimation();
                isCheckedOpcionDos = true;
            }
        });

        checkedDone3.setOnClickListener(v -> {
            if (isCheckedOpcionTres) {
                checkedDone3.setSpeed(-3);
                checkedDone3.playAnimation();
                isCheckedOpcionTres = false;
            } else {
                checkedDone3.setSpeed(2);
                checkedDone3.playAnimation();
                isCheckedOpcionTres = true;
            }
        });

        checkedDone4.setOnClickListener(v -> {
            if (isCheckedOpcionCuatro) {
                checkedDone4.setSpeed(-3);
                checkedDone4.playAnimation();
                isCheckedOpcionCuatro = false;
            } else {
                checkedDone4.setSpeed(2);
                checkedDone4.playAnimation();
                isCheckedOpcionCuatro = true;
            }
        });
    }

    private void validarObjeto() {
        if (getIntent() == null) {
            juego = juegoViewModel.obtenerUltimoJuego();

            //observando el elemento del nombre del juego en pantalla
            juego.observe(this, juego -> nombreJuego.setText(juego.getJuego_nombre()));
        } else {

            juegoNuevo = (Juego) getIntent().getSerializableExtra("juegoNuevo");
            nombreJuego.setText(juegoNuevo.getJuego_nombre());
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UNO) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaUno = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaUno.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton1);
                    txt1.setText(pictograma.getPictograma_nombre());
                    checkedDone1.setVisibility(View.VISIBLE);
                    agrego1 = true;
                });
            }

        }

        if (requestCode == DOS) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaDos = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaDos.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton2);
                    txt2.setText(pictograma.getPictograma_nombre());
                    checkedDone2.setVisibility(View.VISIBLE);
                    agrego2 = true;
                });
            }
        }

        if (requestCode == TRES) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaTres = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaTres.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton3);
                    txt3.setText(pictograma.getPictograma_nombre());
                    checkedDone3.setVisibility(View.VISIBLE);
                    agrego3 = true;
                });
            }
        }

        if (requestCode == CUATRO) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaCuatro = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaCuatro.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton4);
                    txt4.setText(pictograma.getPictograma_nombre());
                    checkedDone4.setVisibility(View.VISIBLE);
                    agrego4 = true;
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}