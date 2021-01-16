package com.example.apptea.ui.juego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;

public class EditarPregunta extends AppCompatActivity {

    ImageButton opcionBoton1, opcionBoton2, opcionBoton3, opcionBoton4;

    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    PreguntaViewModel preguntaViewModel;
    Button guardar;
    TextView tituloPregunta, tituloJuego, contadorPreguntas, txt1, txt2, txt3, txt4;
    boolean agrego1 = false, agrego2 = false, agrego3 = false, agrego4 = false;
    AdministarSesion idioma ;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma, pictogramaUno, pictogramaDos, pictogramaTres, pictogramaCuatro;
    int picto_uno, picto_dos, picto_tres, picto_cuatro, opcion_uno = 0, opcion_dos = 0, opcion_tres = 0, opcion_cuatro = 0;
    Juego juego = new Juego();
    Pregunta pregunta = new Pregunta();
    LottieAnimationView checkedDone1;
    LottieAnimationView checkedDone2;
    LottieAnimationView checkedDone3;
    LottieAnimationView checkedDone4;

    boolean isCheckedOpcionUno = false;
    boolean isCheckedOpcionDos = false;
    boolean isCheckedOpcionTres = false;
    boolean isCheckedOpcionCuatro = false;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final int CUATRO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pregunta);
        guardar = findViewById(R.id.guardar_pregunta);
        tituloJuego = findViewById(R.id.editNombreJuego1);
        tituloPregunta = findViewById(R.id.nombre_pregunta);
        contadorPreguntas = findViewById(R.id.contador_preguntas);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        juego = (Juego) getIntent().getSerializableExtra("juego");
        pregunta = getIntent().getParcelableExtra("pregunta");
        opcionBoton1 = findViewById(R.id.opcion_uno);
        opcionBoton2 = findViewById(R.id.opcion_dos);
        opcionBoton3 = findViewById(R.id.opcion_tres);
        opcionBoton4 = findViewById(R.id.opcion_cuatro);
        txt1 = findViewById(R.id.nombre_opcion_uno);
        txt2 = findViewById(R.id.nombre_opcion_dos);
        txt3 = findViewById(R.id.nombre_opcion_tres);
        txt4 = findViewById(R.id.nombre_opcion_cuatro);
        checkedDone1 = findViewById(R.id.check_opcion_uno);
        checkedDone2 = findViewById(R.id.check_opcion_dos);
        checkedDone3 = findViewById(R.id.check_opcion_tres);
        checkedDone4 = findViewById(R.id.check_opcion_cuatro);
        idioma = new AdministarSesion(getApplicationContext());

        if(idioma.getIdioma()==1){
            tituloJuego.setText(juego.getJuego_nombre());
            tituloPregunta.setText(pregunta.getTitulo_pregunta());
        }else{
            tituloJuego.setText(juego.getName_game());
            tituloPregunta.setText(pregunta.getName_pregunta());}

        setearOpcionesEditar(pregunta.getPregunta_id());


        //Escucha del boton guardar
        guardar.setOnClickListener(v -> {

            if (!tituloPregunta.getText().toString().isEmpty()) {
                if(idioma.getIdioma()==1){
                    pregunta.setTitulo_pregunta(tituloPregunta.getText().toString());
                }else{
                    pregunta.setName_pregunta(tituloPregunta.getText().toString());}

                preguntaViewModel.update(pregunta);

                //seteado de valores para la opcion 1

                if (agrego1 && picto_uno == -1) {
                    final Opcion opcion1 = new Opcion();

                    opcion1.setOpcion_respuesta(isCheckedOpcionUno);
                    opcion1.setPregunta_id(pregunta.getPregunta_id());
                    pictogramaUno.observe(EditarPregunta.this, pictograma -> opcion1.setPictograma_id(pictograma.getPictograma_id()));

                    if (opcion_uno == 0) {
                        opcionViewModel.insert(opcion1);
                    } else {
                        //insert de opcion 1
                        opcion1.setOpcion_id(opcion_uno);
                        opcionViewModel.update(opcion1);
                    }

                } else if (agrego1 && picto_uno > 0) {
                    final Opcion opcion1 = new Opcion();
                    opcion1.setOpcion_id(opcion_uno);
                    opcion1.setOpcion_respuesta(isCheckedOpcionUno);
                    opcion1.setPregunta_id(pregunta.getPregunta_id());
                    opcion1.setPictograma_id(picto_uno);
                    //insert de opcion 1
                    opcionViewModel.update(opcion1);

                }

                //seteado de valores para la opcion 2

                if (agrego2 && picto_dos == -1) {
                    final Opcion opcion2 = new Opcion();

                    opcion2.setPregunta_id(pregunta.getPregunta_id());
                    opcion2.setOpcion_respuesta(isCheckedOpcionDos);
                    pictogramaDos.observe(this, pictograma -> opcion2.setPictograma_id(pictograma.getPictograma_id()));

                    if (opcion_dos == 0) {
                        opcionViewModel.insert(opcion2);
                    } else {
                        //insert de opcion 1
                        opcion2.setOpcion_id(opcion_dos);
                        opcionViewModel.update(opcion2);
                    }
                } else if (agrego2 && picto_dos > 0) {
                    final Opcion opcion2 = new Opcion();
                    opcion2.setOpcion_id(opcion_dos);
                    opcion2.setPregunta_id(pregunta.getPregunta_id());
                    opcion2.setOpcion_respuesta(isCheckedOpcionDos);
                    opcion2.setPictograma_id(picto_dos);
                    //insert opcion 2
                    opcionViewModel.update(opcion2);

                }

                //seteado de valores para la opcion 3
                if (agrego3 && picto_tres == -1) {
                    final Opcion opcion3 = new Opcion();

                    opcion3.setPregunta_id(pregunta.getPregunta_id());
                    opcion3.setOpcion_respuesta(isCheckedOpcionTres);
                    pictogramaTres.observe(EditarPregunta.this, pictograma -> opcion3.setPictograma_id(pictograma.getPictograma_id()));

                    if (opcion_tres == 0) {
                        opcionViewModel.insert(opcion3);
                    } else {
                        opcion3.setOpcion_id(opcion_tres);
                        opcionViewModel.update(opcion3);
                    }
                } else if (agrego3 && picto_tres > 0) {
                    final Opcion opcion3 = new Opcion();
                    opcion3.setOpcion_id(opcion_tres);
                    opcion3.setPregunta_id(pregunta.getPregunta_id());
                    opcion3.setOpcion_respuesta(isCheckedOpcionTres);
                    opcion3.setPictograma_id(picto_tres);

                    //insert opcion 3
                    opcionViewModel.update(opcion3);
                }

                //seteado de valores para la opcion 4
                if (agrego4 && picto_cuatro == -1) {
                    final Opcion opcion4 = new Opcion();

                    opcion4.setPregunta_id(pregunta.getPregunta_id());
                    opcion4.setOpcion_respuesta(isCheckedOpcionCuatro);
                    pictogramaCuatro.observe(EditarPregunta.this, pictograma -> opcion4.setPictograma_id(pictograma.getPictograma_id()));
                    if (opcion_cuatro == 0) {
                        opcionViewModel.insert(opcion4);
                    } else {
                        //insert de opcion 4
                        opcion4.setOpcion_id(opcion_dos);
                        opcion4.setOpcion_id(opcion_cuatro);
                        opcionViewModel.update(opcion4);
                    }
                } else if (agrego4 && picto_cuatro > 0) {
                    final Opcion opcion4 = new Opcion();
                    opcion4.setOpcion_id(opcion_cuatro);
                    opcion4.setPregunta_id(pregunta.getPregunta_id());
                    opcion4.setOpcion_respuesta(isCheckedOpcionCuatro);
                    opcion4.setPictograma_id(picto_cuatro);
                    //insert opcion 4
                    opcionViewModel.update(opcion4);
                }

                finish();
            } else {
                Snackbar.make(findViewById(R.id.definir_pregunta_view), getResources().getString(R.string.debeIngresarUnTitulo), Snackbar.LENGTH_LONG).show();
            }
            finish();
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


    private void setearOpcionesEditar(int id) {

        listaOpciones = opcionViewModel.getOcionesByIdPregunta(id);
        listaOpciones.observe(EditarPregunta.this, opciones -> {


            //seteado opciones si la pregunta tiene al menos una opcion asociada
            int size = (opciones.size()) - 1;
            int count = 0;
            while (count <= size) {
                pictograma = pictogramaViewModel.getPictogramaById(opciones.get(count).getPictograma_id());
                //seteado de imagen y texto del pictograma
                int finalCount = count;
                int finalID = opciones.get(count).getOpcion_id();
                pictograma.observe(EditarPregunta.this, pictograma -> {
                    switch (finalCount) {
                        case 0:
                            opcionBoton1.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            picto_uno = pictograma.getPictograma_id();
                            if(idioma.getIdioma()==1){
                                txt1.setText(pictograma.getPictograma_nombre());
                            }else{
                                txt1.setText(pictograma.getPictograma_name());}

                            agrego1 = true;
                            opcion_uno = finalID;
                            // seteado de checkbox
                            if (opciones.get(0).isOpcion_respuesta()) {
                                checkedDone1.setSpeed(4);
                                isCheckedOpcionUno = true;
                            } else {
                                checkedDone1.setSpeed(-4);
                                isCheckedOpcionUno = false;
                            }
                            checkedDone1.playAnimation();

                            break;

                        case 1:

                            opcionBoton2.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            picto_dos = pictograma.getPictograma_id();
                            if(idioma.getIdioma()==1){
                                txt2.setText(pictograma.getPictograma_nombre());
                            }else{
                                txt2.setText(pictograma.getPictograma_name());}

                            agrego2 = true;
                            opcion_dos = finalID;
                            // seteado de checkbox
                            if (opciones.get(1).isOpcion_respuesta()) {
                                checkedDone2.setSpeed(4);
                                isCheckedOpcionDos = true;
                            } else {
                                checkedDone2.setSpeed(-4);
                                isCheckedOpcionDos = false;
                            }
                            checkedDone2.playAnimation();
                            break;


                        case 2:
                            opcionBoton3.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            picto_tres = pictograma.getPictograma_id();
                            if(idioma.getIdioma()==1){
                                txt3.setText(pictograma.getPictograma_nombre());
                            }else{
                                txt3.setText(pictograma.getPictograma_name());}

                            agrego3 = true;
                            opcion_tres = finalID;
                            // seteado de checkbox
                            if (opciones.get(2).isOpcion_respuesta()) {
                                checkedDone3.setSpeed(4);
                                isCheckedOpcionTres = true;
                            } else {
                                checkedDone3.setSpeed(-4);
                                isCheckedOpcionTres = false;
                            }
                            checkedDone3.playAnimation();

                            break;

                        case 3:
                            opcionBoton4.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                            picto_cuatro = pictograma.getPictograma_id();
                            if(idioma.getIdioma()==1){
                                txt4.setText(pictograma.getPictograma_nombre());
                            }else{
                                txt4.setText(pictograma.getPictograma_name());}

                            agrego4 = true;
                            opcion_cuatro = finalID;
                            // seteado de checkbox
                            if (opciones.get(3).isOpcion_respuesta()) {
                                checkedDone4.setSpeed(4);
                                isCheckedOpcionCuatro = true;
                            } else {
                                checkedDone4.setSpeed(-4);
                                isCheckedOpcionCuatro = false;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UNO) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaUno = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaUno.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton1);
                    if(idioma.getIdioma()==1){
                        txt1.setText(pictograma.getPictograma_nombre());
                    }else{
                        txt1.setText(pictograma.getPictograma_name());}
                    agrego1 = true;
                    picto_uno = -1;
                });
            }

        }

        if (requestCode == DOS) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaDos = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaDos.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton2);
                    if(idioma.getIdioma()==1){
                        txt2.setText(pictograma.getPictograma_nombre());
                    }else{
                        txt2.setText(pictograma.getPictograma_name());}
                    agrego2 = true;
                    picto_dos = -1;
                });
            }
        }

        if (requestCode == TRES) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaTres = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaTres.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton3);
                    if(idioma.getIdioma()==1){
                        txt3.setText(pictograma.getPictograma_nombre());
                    }else{
                        txt3.setText(pictograma.getPictograma_name());}
                    agrego3 = true;
                    picto_tres = -1;
                });
            }
        }

        if (requestCode == CUATRO) {
            if (resultCode == Activity.RESULT_OK) {
                pictogramaCuatro = pictogramaViewModel.getPictogramaById(data.getIntExtra("id", 0));
                pictogramaCuatro.observe(this, pictograma -> {
                    Glide.with(getApplicationContext()).load(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen())).into(opcionBoton4);
                    if(idioma.getIdioma()==1){
                        txt4.setText(pictograma.getPictograma_nombre());
                    }else{
                        txt4.setText(pictograma.getPictograma_name());}
                    agrego4 = true;
                    picto_cuatro = -1;
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

}