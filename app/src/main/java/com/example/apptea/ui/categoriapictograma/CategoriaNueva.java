

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

public class CategoriaNueva extends Activity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText nombreCategoria;
    private CategoriaPictograma categoriaPictograma= new CategoriaPictograma();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_pictograma);
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
                    categoriaPictograma.setCat_pictograma_nombre(nombreCategoria.getText().toString());
                    categoriaPictograma.setCat_pictograma_name(nombreCategoria.getText().toString());
                    replyIntent.putExtra(EXTRA_REPLY, categoriaPictograma);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
