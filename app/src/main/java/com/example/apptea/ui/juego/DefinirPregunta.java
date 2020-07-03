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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    public static final int resultado = 0;
    boolean isCheckedOpcionUno = false;
    boolean isCheckedOpcionDos = false;
    boolean isCheckedOpcionTres = false;
    boolean isCheckedOpcionCuatro = false;
    boolean agrego1=false,agrego2=false,agrego3=false,agrego4=false;
    JuegoViewModel juegoViewModel;
    PictogramaViewModel pictogramaViewModel;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    LiveData<Pictograma> pictogramaUno, pictogramaDos,pictogramaTres,pictogramaCuatro;
    EditText  tituloPregunta;
    ImageButton opcionBoton1,opcionBoton2,opcionBoton3,opcionBoton4;
    Button guardar;
    TextView txt1,txt2,txt3,txt4, nombreJuego;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final int CUATRO = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_pregunta);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        LiveData<Juego> juego = juegoViewModel.obtenerUltimoJuego();
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


        //Escucha del boton guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!tituloPregunta.getText().toString().isEmpty()){
                    preguntaNueva.setTitulo_pregunta(tituloPregunta.getText().toString());
                    juego.observe(DefinirPregunta.this, new Observer<Juego>() {
                        @Override
                        public void onChanged(Juego juego) {
                            preguntaNueva.setJuego_id(juego.getJuego_id());
                        }
                    });
                    //guardado de la pregunta
                    preguntaViewModel.insert(preguntaNueva);

                    //se obtiene el ID de la pregunta insertada
                    Pregunta pregunta1 = preguntaDAO.obtenerUltimaPregunta();

                    //declaracion de variables opciones

                    //seteado de valores para la opcion 1

                    if(agrego1){
                        final Opcion opcion1 = new Opcion();
                    opcion1.setOpcion_respuesta(isCheckedOpcionUno);
                    opcion1.setPregunta_id(pregunta1.getPregunta_id());
                    pictogramaUno.observe(DefinirPregunta.this, new Observer<Pictograma>() {
                        @Override
                        public void onChanged(Pictograma pictograma) {
                            opcion1.setPictograma_id(pictograma.getPictograma_id());
                        }
                    });

                    //insert de opcion 1
                    opcionViewModel.insert(opcion1);
                    }

                    //seteado de valores para la opcion 2

                    if (agrego2) {
                        final Opcion opcion2 = new Opcion();
                        opcion2.setPregunta_id(pregunta1.getPregunta_id());
                        opcion2.setOpcion_respuesta(isCheckedOpcionDos);
                        pictogramaUno.observe(DefinirPregunta.this, new Observer<Pictograma>() {
                            @Override
                            public void onChanged(Pictograma pictograma) {
                                opcion2.setPictograma_id(pictograma.getPictograma_id());
                            }
                        });
                        //insert opcion 2
                        opcionViewModel.insert(opcion2);
                    }

                    //seteado de valores para la opcion 3
                    if (agrego3) {
                        final Opcion opcion3 = new Opcion();
                        opcion3.setPregunta_id(pregunta1.getPregunta_id());
                        opcion3.setOpcion_respuesta(isCheckedOpcionTres);
                        pictogramaTres.observe(DefinirPregunta.this, new Observer<Pictograma>() {
                            @Override
                            public void onChanged(Pictograma pictograma) {
                                opcion3.setPictograma_id(pictograma.getPictograma_id());
                            }
                        });

                        //insert opcion 3
                        opcionViewModel.insert(opcion3);
                    }

                    //seteado de valores para la opcion 4
                    if (agrego4) {
                        final Opcion opcion4 = new Opcion();
                        opcion4.setPregunta_id(pregunta1.getPregunta_id());
                        opcion4.setOpcion_respuesta(isCheckedOpcionCuatro);
                        pictogramaCuatro.observe(DefinirPregunta.this, new Observer<Pictograma>() {
                            @Override
                            public void onChanged(Pictograma pictograma) {
                                opcion4.setPictograma_id(pictograma.getPictograma_id());
                            }
                        });
                        //insert opcion 4
                        opcionViewModel.insert(opcion4);
                    }

                    finish();
                }else {
                    Snackbar.make(findViewById(R.id.definir_pregunta_view) ,"Debe ingresar un titulo para la pregunta",Snackbar.LENGTH_LONG).show();
                }

            }

        });

        //actividades para buscar pictogramas
        opcionBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BuscarPictograma.class);
                startActivityForResult(intent, UNO);
            }
        });

        opcionBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuscarPictograma.class);
                startActivityForResult(intent,DOS);
            }
        });

        opcionBoton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BuscarPictograma.class);
                startActivityForResult(intent,TRES);
            }
        });

        opcionBoton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BuscarPictograma.class);
                startActivityForResult(intent,CUATRO);
            }
        });

        //observando el elemento del nombre del juego en pantalla
        juego.observe(this, new Observer<Juego>() {
            @Override
            public void onChanged(Juego juego) {
                nombreJuego.setText(juego.getJuego_nombre());
            }
        });

        //elementos de lottiAnimation, uno por cada checkbox
        LottieAnimationView checkedDone1 = findViewById(R.id.check_opcion_uno);
        LottieAnimationView checkedDone2 = findViewById(R.id.check_opcion_dos);
        LottieAnimationView checkedDone3 = findViewById(R.id.check_opcion_tres);
        LottieAnimationView checkedDone4 = findViewById(R.id.check_opcion_cuatro);

        //se definen escuchas por cada animacion
        checkedDone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedOpcionUno){

                    checkedDone1.setSpeed(-3);
                    checkedDone1.playAnimation();
                    isCheckedOpcionUno=false;
                }else {
                    checkedDone1.setSpeed(2);
                    checkedDone1.playAnimation();
                    isCheckedOpcionUno=true;
                }
            }
        });

        checkedDone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckedOpcionDos){
                    checkedDone2.setSpeed(-3);
                    checkedDone2.playAnimation();
                    isCheckedOpcionDos=false;
                } else {
                    checkedDone2.setSpeed(2);
                    checkedDone2.playAnimation();
                    isCheckedOpcionDos=true;
                }
            }
        });

        checkedDone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckedOpcionTres){
                    checkedDone3.setSpeed(-3);
                    checkedDone3.playAnimation();
                    isCheckedOpcionTres=false;
                } else {
                    checkedDone3.setSpeed(2);
                    checkedDone3.playAnimation();
                    isCheckedOpcionTres=true;
                }
            }

        });

        checkedDone4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckedOpcionCuatro){
                    checkedDone4.setSpeed(-3);
                    checkedDone4.playAnimation();
                    isCheckedOpcionCuatro=false;
                } else {
                    checkedDone4.setSpeed(2);
                    checkedDone4.playAnimation();
                    isCheckedOpcionCuatro=true;
                }
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == UNO){
            if(resultCode == Activity.RESULT_OK){
                pictogramaUno = pictogramaViewModel.getPictogramaById(data.getIntExtra("id",0));
                pictogramaUno.observe(this, new Observer<Pictograma>() {
                    @Override
                    public void onChanged(Pictograma pictograma) {
                        Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton1);
                        txt1.setText(pictograma.getPictograma_nombre());
                        agrego1=true;
                    }
                });
            }

        }

        if (requestCode == DOS){
            if(resultCode == Activity.RESULT_OK){
                pictogramaDos = pictogramaViewModel.getPictogramaById(data.getIntExtra("id",0));
                pictogramaDos.observe(this, new Observer<Pictograma>() {
                    @Override
                    public void onChanged(Pictograma pictograma) {
                        Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton2);
                        txt2.setText(pictograma.getPictograma_nombre());
                        agrego2=true;
                    }
                });
            }
        }

        if (requestCode == TRES){
            if(resultCode == Activity.RESULT_OK){
                pictogramaTres = pictogramaViewModel.getPictogramaById(data.getIntExtra("id",0));
                pictogramaTres.observe(this, new Observer<Pictograma>() {
                    @Override
                    public void onChanged(Pictograma pictograma) {
                        Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton3);
                        txt3.setText(pictograma.getPictograma_nombre());
                        agrego3=true;
                    }
                });
            }
        }

        if (requestCode == CUATRO){
            if(resultCode == Activity.RESULT_OK){
                pictogramaCuatro = pictogramaViewModel.getPictogramaById(data.getIntExtra("id",0));
                pictogramaCuatro.observe(this, new Observer<Pictograma>() {
                    @Override
                    public void onChanged(Pictograma pictograma) {
                        Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton4);
                        txt4.setText(pictograma.getPictograma_nombre());
                        agrego4=true;
                    }
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