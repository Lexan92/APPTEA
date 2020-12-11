/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.usuario;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.apptea.R;
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.google.android.material.textfield.TextInputEditText;


import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Usuario;

public class EditContrasenia extends AppCompatActivity {

   EditText contra1;
   EditText contra2;
   //String error="Campo Obligatorio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.editar_contrasenia);

        UsuarioViewModel usuarioViewModel;
        contra1 = findViewById(R.id.editContrasenia1);
        contra2 =  findViewById(R.id.editContrasenia2);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        //PARA mostrar la actividad de editar contraseña
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (validaciones() > 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.esNecesarioCampoRequerido), Toast.LENGTH_LONG).show();
                } else {
                    if (contra1.getText().toString().equals(contra2.getText().toString())) {

                        //Se obtiene el usuario guardado se obtiene la primera fila.
                        UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getApplicationContext()).usuarioDao();
                        Usuario usuario = usuarioDao.obtenerUsuario();

                        usuario.setContrasenia(contra1.getText().toString());
                        usuarioViewModel.update(usuario);

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.seActualizoContrase), Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.debenSerIguales), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }

    //VALIDACIONES
    public int validaciones(){
        int validar=0;

        if (TextUtils.isEmpty(contra1.getText())) {
            validar+=1;
            contra1.setError(getResources().getString(R.string.campoRequerido));
        }else{contra1.setError(null); }

        if(TextUtils.isEmpty(contra2.getText())){
            validar+=1;;
            contra2.setError(getResources().getString(R.string.campoRequerido));
        }else{contra2.setError(null); }

        return validar;
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