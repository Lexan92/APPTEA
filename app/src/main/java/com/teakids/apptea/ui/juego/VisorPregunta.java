/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.juego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.pictograma.PictogramaViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;

public class VisorPregunta extends AppCompatActivity {
    ImageButton opcionBoton1, opcionBoton2, opcionBoton3, opcionBoton4, editarPregunta, borrarPregunta, preguntaSiguiente, preguntaAnterior;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    Button nuevapregunta;
    TextView tituloPregunta, tituloJuego, contadorPreguntas, txt1, txt2, txt3, txt4, aviso;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma;
    Juego juego = new Juego();
    LottieAnimationView checkedDone1;
    LottieAnimationView checkedDone2;
    LottieAnimationView checkedDone3;
    LottieAnimationView checkedDone4;
    Pregunta preguntaEditar = new Pregunta();
    AdministarSesion idioma ;

    //posicion de la pregunta
    int posicion;

    public int contador = 0;
    private boolean banderaPreguntas;
    private int SEGUNDA_ACTIVIDAD = 0;


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
        checkedDone1.setVisibility(View.GONE);
        checkedDone2.setVisibility(View.GONE);
        checkedDone3.setVisibility(View.GONE);
        checkedDone4.setVisibility(View.GONE);
        aviso = findViewById(R.id.aviso_opcion_vacio);
        aviso.setVisibility(View.GONE);
        idioma = new AdministarSesion(getApplicationContext());

        listadoPreguntas = preguntaViewModel.getPreguntasByIdJuego(juego.getJuego_id());

        //verifica si el juego posee preguntas
        listadoPreguntas.observe(VisorPregunta.this, preguntas -> {

            if (preguntas.size() != 0) {
                posicion = 0;
                if(idioma.getIdioma()==1){
                    tituloPregunta.setText(preguntas.get(0).getTitulo_pregunta());
                    tituloJuego.setText(juego.getJuego_nombre());
                }else{
                    tituloPregunta.setText(preguntas.get(0).getName_pregunta());
                    tituloJuego.setText(juego.getName_game());}


                setearOpciones(preguntas.get(0).getPregunta_id());
                contadorPreguntas.setText((posicion + 1) + " / " + preguntas.size());
            } else {
                Intent intent = new Intent(this, JuegoPrincipal.class);
                intent.putExtra("juego", juego);
                startActivity(intent);
                finish();
            }

        });



        //NUEVA PREGUNTA
        nuevapregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listadoPreguntas.observe(VisorPregunta.this, preguntas -> {
                    if ((preguntas.size()) + 1 <= 10) {
                        banderaPreguntas = true;
                    } else {
                        banderaPreguntas =false;
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.maximo10Preguntas),
                                Toast.LENGTH_LONG).show();
                    }
                });

                listadoPreguntas.removeObservers(VisorPregunta.this);

                if(banderaPreguntas){
                    Intent intent = new Intent(getApplicationContext(), DefinirPregunta.class);
                    intent.putExtra("juegoNuevo", juego);
                    startActivityForResult(intent,SEGUNDA_ACTIVIDAD);

                }

            }
        });


        //EDITAR PREGUNTA

        editarPregunta.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarPregunta.class);
            intent.putExtra("juego", juego);
            listadoPreguntas.observe(VisorPregunta.this, new Observer<List<Pregunta>>() {
                @Override
                public void onChanged(List<Pregunta> preguntas) {
                    preguntaEditar = preguntas.get(posicion);
                }
            });
            intent.putExtra("pregunta", preguntaEditar);
            startActivity(intent);

        });

        //BORRAR PREGUNTA

        borrarPregunta.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(VisorPregunta.this);

            builder.setTitle(getResources().getString(R.string.eliminarPregunta));
            builder.setMessage(getResources().getString(R.string.laPReguntaSeraElimi));
            builder.setNegativeButton(getResources().getString(R.string.cancelar), (dialog, which) -> {
            });
            builder.setPositiveButton(getResources().getString(R.string.aceptar), (dialog, which) -> {
                listadoPreguntas.observe(VisorPregunta.this, preguntas -> {
                    if (preguntas.size() != 0) {
                        Pregunta preguntaBorrar = preguntas.get(posicion);
                        borrarPregunta(preguntaBorrar);
                        finish();
                    }

                });
                listadoPreguntas.removeObservers(VisorPregunta.this);
            });

            builder.show();

        });

        //PREGUNTA SIGUIENTE

        preguntaSiguiente.setOnClickListener(v -> {
            posicion++;
            listadoPreguntas.observe(VisorPregunta.this, preguntas -> {
                int sizePreguntas = preguntas.size();
                if (posicion <= sizePreguntas - 1) {
                    if(idioma.getIdioma()==1){
                        tituloPregunta.setText(preguntas.get(posicion).getTitulo_pregunta());
                        tituloJuego.setText(juego.getJuego_nombre());
                    }else{
                        tituloPregunta.setText(preguntas.get(posicion).getName_pregunta());
                        tituloJuego.setText(juego.getName_game());}

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
                    if(idioma.getIdioma()==1){
                        tituloPregunta.setText(preguntas.get(posicion).getTitulo_pregunta());
                        tituloJuego.setText(juego.getJuego_nombre());
                    }else{
                        tituloPregunta.setText(preguntas.get(posicion).getName_pregunta());
                        tituloJuego.setText(juego.getName_game());}

                    setearOpciones(preguntas.get(posicion).getPregunta_id());
                    contadorPreguntas.setText((posicion + 1) + " / " + preguntas.size());
                } else {
                    posicion++;
                }
            });

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEGUNDA_ACTIVIDAD){
            if(resultCode == Activity.RESULT_OK){
                listadoPreguntas.observe(VisorPregunta.this, preguntas -> {
                    contadorPreguntas.setText((posicion + 1) + " / " + preguntas.size());
                });

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.guardadoCorrectamente),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void borrarPregunta(Pregunta pregunta) {
        preguntaViewModel.delete(pregunta);
        Intent intent = new Intent(VisorPregunta.this, VisorPregunta.class);
        intent.putExtra("juego", juego);
        startActivity(intent);
        finish();
    }


    private void setearOpciones(int id) {
        reiniciarVistaopciones();
        listaOpciones = opcionViewModel.getOcionesByIdPregunta(id);
        listaOpciones.observe(VisorPregunta.this, opciones -> {
            if (opciones.size() != 0) {
                aviso.setVisibility(View.GONE);
                //seteado opciones si la pregunta tiene al menos una opcion asociada
                int size = (opciones.size()) - 1;
                int count = contador;
                while (count <= size) {
                    pictograma = pictogramaViewModel.getPictogramaById(opciones.get(count).getPictograma_id());
                    //seteado de imagen y texto del pictograma
                    int finalCount = count;
                    pictograma.observe(VisorPregunta.this, pictograma -> {
                        switch (finalCount) {
                            case 0:
                                opcionBoton1.setVisibility(View.VISIBLE);
                                txt1.setVisibility(View.VISIBLE);
                                checkedDone1.setVisibility(View.VISIBLE);
                                opcionBoton1.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    txt1.setText(pictograma.getPictograma_nombre());
                                }else{
                                    txt1.setText(pictograma.getPictograma_name());}

                                // seteado de checkbox
                                if (opciones.get(0).isOpcion_respuesta()) {
                                    checkedDone1.setMinAndMaxProgress(1.0f, 1.0f);
                                } else {
                                    checkedDone1.setMinAndMaxProgress(0.0f, 0.0f);
                                }
                                checkedDone1.playAnimation();
                                break;

                            case 1:
                                opcionBoton2.setVisibility(View.VISIBLE);
                                txt2.setVisibility(View.VISIBLE);
                                checkedDone2.setVisibility(View.VISIBLE);
                                opcionBoton2.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    txt2.setText(pictograma.getPictograma_nombre());
                                }else{
                                    txt2.setText(pictograma.getPictograma_name());}

                                // seteado de checkbox
                                if (opciones.get(1).isOpcion_respuesta()) {
                                    checkedDone2.setMinAndMaxProgress(1.0f, 1.0f);
                                } else {
                                    checkedDone2.setMinAndMaxProgress(0.0f, 0.0f);
                                }
                                checkedDone2.playAnimation();
                                break;


                            case 2:
                                opcionBoton3.setVisibility(View.VISIBLE);
                                txt3.setVisibility(View.VISIBLE);
                                checkedDone3.setVisibility(View.VISIBLE);
                                opcionBoton3.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    txt3.setText(pictograma.getPictograma_nombre());
                                }else{
                                    txt3.setText(pictograma.getPictograma_name());}

                                // seteado de checkbox
                                if (opciones.get(2).isOpcion_respuesta()) {
                                    checkedDone3.setMinAndMaxProgress(1.0f, 1.0f);
                                } else {
                                    checkedDone3.setMinAndMaxProgress(0.0f, 0.0f);
                                }
                                checkedDone3.playAnimation();
                                break;

                            case 3:
                                opcionBoton4.setVisibility(View.VISIBLE);
                                txt4.setVisibility(View.VISIBLE);
                                checkedDone4.setVisibility(View.VISIBLE);
                                opcionBoton4.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma.getPictograma_imagen()));
                                if(idioma.getIdioma()==1){
                                    txt4.setText(pictograma.getPictograma_nombre());
                                }else{
                                    txt4.setText(pictograma.getPictograma_name());}

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
            } else {

                aviso.setText(getResources().getString(R.string.noTieneOpciones));
                aviso.setVisibility(View.VISIBLE);
            }

        });
    }

    private void reiniciarVistaopciones() {
        //seteado de preguntas en el caso que no tenga opciones agregadas
        //seteado opcion 1

        opcionBoton1.setVisibility(View.INVISIBLE);
        txt1.setVisibility(View.INVISIBLE);
        checkedDone1.setVisibility(View.INVISIBLE);

        opcionBoton2.setVisibility(View.INVISIBLE);
        txt2.setVisibility(View.INVISIBLE);
        checkedDone2.setVisibility(View.INVISIBLE);

        opcionBoton3.setVisibility(View.INVISIBLE);
        txt3.setVisibility(View.INVISIBLE);
        checkedDone3.setVisibility(View.INVISIBLE);

        opcionBoton4.setVisibility(View.INVISIBLE);
        txt4.setVisibility(View.INVISIBLE);
        checkedDone4.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();

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

