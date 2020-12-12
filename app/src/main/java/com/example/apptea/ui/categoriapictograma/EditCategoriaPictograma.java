/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.categoriapictograma;

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
import com.example.apptea.utilidades.AdministarSesion;

import roomsqlite.entidades.CategoriaPictograma;

public class EditCategoriaPictograma extends Activity {

    public static final String EXTRA_ID_CAT_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_ID_CAT_UPDATE";
    public static final String EXTRA_NOMBRE_CAT_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_NOMBRE_CAT_UPDATE";
    public static final String EXTRA_NAME_CAT_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_NAME_CAT_UPDATE";
    public static final String EXTRA_CAT_HAB_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_CAT_HAB_UPDATE";
    public static final String EXTRA_CAT_PREDETERMINADO_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_CAT_HAB_UPDATE";
    public static final String EXTRA_PICTOGRAMA_ID_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_PICTOGRAMA_ID_UPDATE";

    EditText nombreCat;
    CategoriaPictograma categoriaPictograma = new CategoriaPictograma();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cat_pictograma);

        nombreCat = findViewById(R.id.edit_cat);

        Intent intent = getIntent();
        AdministarSesion idioma = new AdministarSesion(getApplicationContext());

        if(idioma.getIdioma()==1){
            nombreCat.setText(intent.getStringExtra(EXTRA_NOMBRE_CAT_UPDATE));
        }else{
            nombreCat.setText(intent.getStringExtra(EXTRA_NAME_CAT_UPDATE));}



        //PARA GUARDAR ACTUALIZACION Categoria de Pictograma
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nombreCat.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    categoriaPictograma.setCat_pictograma_id(intent.getIntExtra(EXTRA_ID_CAT_UPDATE, -1));

                    if(idioma.getIdioma()==1){
                        categoriaPictograma.setCat_pictograma_nombre(nombreCat.getText().toString());
                        categoriaPictograma.setCat_pictograma_name(intent.getStringExtra(EXTRA_NAME_CAT_UPDATE));
                    }else{
                        categoriaPictograma.setCat_pictograma_name(nombreCat.getText().toString());
                        categoriaPictograma.setCat_pictograma_nombre(intent.getStringExtra(EXTRA_NOMBRE_CAT_UPDATE));
                    }

                    categoriaPictograma.setPredeterminado(intent.getBooleanExtra(EXTRA_CAT_PREDETERMINADO_UPDATE,Boolean.parseBoolean(EXTRA_CAT_PREDETERMINADO_UPDATE)));
                    categoriaPictograma.setPictograma_id(intent.getIntExtra(EXTRA_PICTOGRAMA_ID_UPDATE,-1));
                    replyIntent.putExtra(EXTRA_CAT_HAB_UPDATE, categoriaPictograma);
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