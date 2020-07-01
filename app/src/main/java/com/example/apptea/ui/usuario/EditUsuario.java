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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.apptea.R;
import com.example.apptea.ui.pais.PaisViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import roomsqlite.dao.PaisDao;

import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.Usuario;

public class EditUsuario extends AppCompatActivity {
    public static final String EXTRA_ID_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_ID_USUARIO_UPDATE";
    public static final String EXTRA_NOMBRE_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_NOMBRE_USUARIO_UPDATE";
    public static final String EXTRA_APELLIDO_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_APELLIDO_USUARIO_UPDATE";
    public static final String EXTRA_PAIS_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_PAIS_USUARIO_UPDATE";
    public static final String EXTRA_TELEFONO_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_TELEFONO_USUARIO_UPDATE";
    public static final String EXTRA_CORREO_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_CORREO_USUARIO_UPDATE";
    public static final String EXTRA_CONTRASEÑA_UPDATE = "com.example.apptea.ui.usuario.EXTRA_CONTRASEÑA_UPDATE";
    public static final String EXTRA_USUARIO_UPDATE = "com.example.apptea.ui.usuario.EXTRA_USUARIO_UPDATE";


    EditText editNombre,editApellido,editCorreo;

    private Usuario usuario = new Usuario();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_usuario);

        editNombre = (EditText)findViewById(R.id.editNombreUsuario);
        editApellido = (EditText)findViewById(R.id.editApellido);
        editCorreo = (EditText)findViewById(R.id.editCorreo);


        Intent intent = getIntent();

        editNombre.setText(intent.getStringExtra(EXTRA_NOMBRE_USUARIO_UPDATE));
        editApellido.setText(intent.getStringExtra(EXTRA_APELLIDO_USUARIO_UPDATE));
        editCorreo.setText(intent.getStringExtra(EXTRA_CORREO_USUARIO_UPDATE));

        //PARA GUARDAR ACTUALIZACION Categoria de habilidades cotidiandas
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editNombre.getText()) || TextUtils.isEmpty(editApellido.getText()) || TextUtils.isEmpty(editCorreo.getText()) ) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    usuario.setUsuario_id(intent.getIntExtra(EXTRA_ID_USUARIO_UPDATE, -1));
                    usuario.setUsuario_nombre(editNombre.getText().toString());
                    usuario.setUsuario_apellido(editApellido.getText().toString());
                    usuario.setCorreo(editCorreo.getText().toString());
                    usuario.setPais_id(intent.getIntExtra(EXTRA_PAIS_USUARIO_UPDATE, 1));
                    usuario.setTelefono(intent.getIntExtra(EXTRA_TELEFONO_USUARIO_UPDATE,1));
                    usuario.setContrasenia(intent.getStringExtra(EXTRA_CONTRASEÑA_UPDATE));
                    replyIntent.putExtra(EXTRA_USUARIO_UPDATE, usuario);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });






    }


}
