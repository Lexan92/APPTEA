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

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;

public class DefinirPregunta extends AppCompatActivity {

    boolean isCheckedOpcionUno = false;
    boolean isCheckedOpcionDos = false;
    boolean isCheckedOpcionTres = false;
    boolean isCheckedOpcionCuatro = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_pregunta);

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