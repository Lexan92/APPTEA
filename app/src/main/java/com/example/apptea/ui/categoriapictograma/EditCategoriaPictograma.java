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
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptea.R;

import roomsqlite.entidades.CategoriaPictograma;

public class EditCategoriaPictograma extends Activity {

    public static final String EXTRA_ID_CAT_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_ID_CAT_UPDATE";
    public static final String EXTRA_NOMBRE_CAT_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_NOMBRE_CAT_UPDATE";
    public static final String EXTRA_CAT_HAB_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_CAT_HAB_UPDATE";
    public static final String EXTRA_CAT_PREDETERMINADO_UPDATE = "com.example.apptea.ui.categoriapictograma.EXTRA_CAT_HAB_UPDATE";

    EditText nombreCat;
    CategoriaPictograma categoriaPictograma = new CategoriaPictograma();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cat_pictograma);

        nombreCat = findViewById(R.id.edit_cat);

        Intent intent = getIntent();

        nombreCat.setText(intent.getStringExtra(EXTRA_NOMBRE_CAT_UPDATE));

        //PARA GUARDAR ACTUALIZACION Categoria de Pictograma
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nombreCat.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    categoriaPictograma.setCat_pictograma_id(intent.getIntExtra(EXTRA_ID_CAT_UPDATE, -1));
                    categoriaPictograma.setCat_pictograma_nombre(nombreCat.getText().toString());
                    categoriaPictograma.setPredeterminado(intent.getBooleanExtra(EXTRA_CAT_PREDETERMINADO_UPDATE,Boolean.parseBoolean(EXTRA_CAT_PREDETERMINADO_UPDATE)));
                    replyIntent.putExtra(EXTRA_CAT_HAB_UPDATE, categoriaPictograma);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}