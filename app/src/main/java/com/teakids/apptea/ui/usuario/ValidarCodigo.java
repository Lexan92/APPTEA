/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.usuario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;

public class ValidarCodigo extends Activity {

        EditText codigo;
        byte intentos=3;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.validar_codigo_dialog);

            codigo = findViewById(R.id.edit_codigo);

            Intent intent = getIntent();
            int codigovalidar = intent.getIntExtra("codigo",0);


          //PARA mostrar la actividad de editar contraseña
            final Button button = findViewById(R.id.button_validar);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent replyIntent = new Intent();

                    if (TextUtils.isEmpty(codigo.getText())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                            int cod = Integer.parseInt(codigo.getText().toString());
                            if (codigovalidar == cod) {
                                //Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();

                                //Navigation.findNavController(view).navigate(R.id.editar_contraseña,bundleBanderaToolbar);
                                Intent intent2 = new Intent(getApplicationContext(), EditContrasenia.class);
                                startActivity(intent2);
                                finish();
                            } else {
                              Toast.makeText(getApplicationContext(), getResources().getString(R.string.codigoIncorrecto), Toast.LENGTH_LONG).show();
                            }
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
