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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;

import roomsqlite.entidades.Juego;

public class DefinirPregunta extends AppCompatActivity {

    boolean isCheckedOpcionUno = false;
    boolean isCheckedOpcionDos = false;
    boolean isCheckedOpcionTres = false;
    boolean isCheckedOpcionCuatro = false;
    JuegoViewModel juegoViewModel;
    EditText nombreJuego;
    ImageButton opcion1,opcion2,opcion3,opcion4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_pregunta);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        LiveData<Juego> juego = juegoViewModel.obtenerUltimoJuego();
        Juego juegoNuevo = new Juego();

        nombreJuego = findViewById(R.id.editNombreJuego1);
        opcion1 = findViewById(R.id.opcion_uno);

        opcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // opcion1.setImageResource(R.drawable.letra_a);
               //Glide.with(v).load(R.drawable.letra_a).into(opcion1);
                Intent intent = new Intent(getApplicationContext(),BuscarPictograma.class);
                startActivity(intent);
            }
        });

        juego.observe(this, new Observer<Juego>() {
            @Override
            public void onChanged(Juego juego) {
                nombreJuego.setText(juego.getJuego_nombre());

                juegoNuevo.setJuego_id(juego.getJuego_id());
                juegoNuevo.setCategoria_juego_id(juego.getCategoria_juego_id());
                juegoNuevo.setJuego_nombre(juego.getJuego_nombre());
                juegoNuevo.setJuego_predeterminado(juego.isJuego_predeterminado());
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
}