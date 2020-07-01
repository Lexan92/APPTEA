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
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import com.example.apptea.R;
import com.google.android.material.textfield.TextInputEditText;


import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Usuario;

public class EditContrasenia extends AppCompatActivity {

   EditText contra1;
   EditText contra2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.editar_contrasenia);

        contra1 = findViewById(R.id.editContrasenia1);
        contra2 =  findViewById(R.id.editContrasenia2);


        //PARA mostrar la actividad de editar contraseña
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (contra1.getText().toString().equals(contra2.getText().toString())) {

                    //Se obtiene el usuario guardado se obtiene la primera fila.
                    UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getApplicationContext()).usuarioDao();
                    Usuario usuario = usuarioDao.obtenerUsuario();

                    usuario.setContrasenia(contra1.getText().toString());

                    Toast.makeText(getApplicationContext(), "Se actualizo la contraseña con exito", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas deben de ser iguales", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}