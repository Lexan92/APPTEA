package com.example.apptea.ui.habilidadCotidiana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;

import com.example.apptea.R;

public class AgregarSecuenciaDialog extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confimar_agregar_hab_dialog);

        final TextView texto;
        texto = findViewById(R.id.tituloHab);

        Intent intent = getIntent();
        int habilidad = intent.getIntExtra("hab_cotidiana_id",0);





    }


}
