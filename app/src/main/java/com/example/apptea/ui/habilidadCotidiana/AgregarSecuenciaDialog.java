package com.example.apptea.ui.habilidadCotidiana;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.apptea.R;


public class AgregarSecuenciaDialog extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialComponents_Light_Dialog_MinWidth);
        setContentView(R.layout.confimar_agregar_hab_dialog);

        final TextView texto;
        texto = findViewById(R.id.tituloHab);
        Fragment secuenciaFragment;
        Intent intent = getIntent();
        int habilidad = intent.getIntExtra("hab_cotidiana_id",0);


       // secuenciaFragment = new SecuenciaFragment();

        //PARA mostrar la actividad de editar contraseña
        final Button buttonSi = findViewById(R.id.button_si);
        buttonSi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecuenciaFragment.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.recyclerview_hab_cotidiana,secuenciaFragment).commit();
                finish();
            }

        });

        //PARA mostrar la actividad de editar contraseña
        final Button buttonMasTarde = findViewById(R.id.button_mas_tarde);
        buttonMasTarde.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();
            }

        });


    }


}
