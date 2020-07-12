package com.example.apptea.ui.habilidadCotidiana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.apptea.R;

public class NuevaHabilidadCotidianaDialog extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_hab_cotidiana);


/*
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
                    categoriaHabCotidiana.setCat_predeterminado(false);
                    replyIntent.putExtra(EXTRA_REPLY, categoriaHabCotidiana);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });*/
    }

}
