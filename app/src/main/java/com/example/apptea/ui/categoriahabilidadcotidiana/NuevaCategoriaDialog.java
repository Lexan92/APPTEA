/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */


package com.example.apptea.ui.categoriahabilidadcotidiana;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptea.R;
import com.example.apptea.ui.configuracion.LocaleHelper;

import java.io.Serializable;

import roomsqlite.entidades.CategoriaHabCotidiana;

public class NuevaCategoriaDialog extends Activity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText nombreCategoria;
    private CategoriaHabCotidiana categoriaHabCotidiana = new CategoriaHabCotidiana();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cat_hab_cotidiana);
        nombreCategoria = findViewById(R.id.edit_word);


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty( nombreCategoria.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    //String word =  nombreCategoria.getText().toString();
                    //replyIntent.putExtra(EXTRA_REPLY, word);
                    categoriaHabCotidiana.setCat_hab_cotidiana_nombre(nombreCategoria.getText().toString());
                    categoriaHabCotidiana.setCat_hab_cotidiana_name(nombreCategoria.getText().toString());
                    categoriaHabCotidiana.setCat_predeterminado(false);
                    replyIntent.putExtra(EXTRA_REPLY, categoriaHabCotidiana);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
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
