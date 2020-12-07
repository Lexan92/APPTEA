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
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;

import roomsqlite.entidades.CategoriaHabCotidiana;

public class EditCategoriaHab extends Activity {

    public static final String EXTRA_ID_CAT_UPDATE = "com.example.apptea.ui.categoriahabilidadcotidiana.EXTRA_ID_CAT_UPDATE";
    public static final String EXTRA_NOMBRE_CAT_UPDATE = "com.example.apptea.ui.categoriahabilidadcotidiana.EXTRA_NOMBRE_CAT_UPDATE";
    public static final String EXTRA_NAME_CAT_UPDATE = "com.example.apptea.ui.categoriahabilidadcotidiana.EXTRA_NAME_CAT_UPDATE";
    public static final String EXTRA_CAT_HAB_UPDATE = "com.example.apptea.ui.categoriahabilidadcotidiana.EXTRA_CAT_HAB_UPDATE";
    public static final String EXTRA_CAT_PREDETERMINADO_UPDATE = "com.example.apptea.ui.categoriahabilidadcotidiana.EXTRA_CAT_HAB_UPDATE";
    public static final String EXTRA_PICTOGRAMA_ID_UPDATE = "com.example.apptea.ui.categoriahabilidadcotidiana.EXTRA_PICTOGRAMA_ID_UPDATE";


    EditText nombreCat;
    CategoriaHabCotidiana categoriaHabCotidiana = new CategoriaHabCotidiana();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cat_habilidad);

        nombreCat = findViewById(R.id.edit_cat);

        Intent intent = getIntent();
        AdministarSesion idioma = new AdministarSesion(getApplicationContext());

        if(idioma.getIdioma()==1){
            nombreCat.setText(intent.getStringExtra(EXTRA_NOMBRE_CAT_UPDATE));
        }else{
            nombreCat.setText(intent.getStringExtra(EXTRA_NAME_CAT_UPDATE));}



        //PARA GUARDAR ACTUALIZACION Categoria de habilidades cotidiandas
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nombreCat.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    categoriaHabCotidiana.setCat_hab_cotidiana_id(intent.getIntExtra(EXTRA_ID_CAT_UPDATE, -1));

                    if(idioma.getIdioma()==1){
                        categoriaHabCotidiana.setCat_hab_cotidiana_nombre(nombreCat.getText().toString());
                        categoriaHabCotidiana.setCat_hab_cotidiana_name(intent.getStringExtra(EXTRA_NAME_CAT_UPDATE));
                    }else{
                        categoriaHabCotidiana.setCat_hab_cotidiana_name(nombreCat.getText().toString());
                        categoriaHabCotidiana.setCat_hab_cotidiana_nombre(intent.getStringExtra(EXTRA_NOMBRE_CAT_UPDATE));
                        }

                    categoriaHabCotidiana.setCat_predeterminado(intent.getBooleanExtra(EXTRA_CAT_PREDETERMINADO_UPDATE,Boolean.parseBoolean(EXTRA_CAT_PREDETERMINADO_UPDATE)));
                    categoriaHabCotidiana.setPictograma_id(intent.getIntExtra(EXTRA_PICTOGRAMA_ID_UPDATE,-1));
                    replyIntent.putExtra(EXTRA_CAT_HAB_UPDATE, categoriaHabCotidiana);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}