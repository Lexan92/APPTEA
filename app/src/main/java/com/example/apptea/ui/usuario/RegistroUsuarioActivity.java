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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.rol.RolViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.Rol;
import roomsqlite.entidades.Usuario;

public class RegistroUsuarioActivity extends AppCompatActivity {
    public static final String EXTRA_USUARIO = "com.example.apptea.EXTRA_USUARIO";

    public static final int REGISTRO_USUARIO_ACTIVITY_REQUEST_CODE = 1;

    UsuarioViewModel usuarioViewModel;
    private RolViewModel rolViewModel;
    TextInputEditText nombreUsuario;
    TextInputEditText apellidoUsuario;
    TextInputEditText correoUsuario;
    TextInputEditText contraUsuario;
    private Usuario usuario = new Usuario();
    //Boolean validar= false;
    List<Rol> rolesArray = new ArrayList<>();
    int rolId;

    String error="Campo Obligatorio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //obteniendo datos del layout
        nombreUsuario=  findViewById(R.id.nombreUsuario);
        apellidoUsuario =  findViewById(R.id.apellidoUsuario);
        correoUsuario=  findViewById(R.id.correoUsuario);
        contraUsuario = findViewById(R.id.contraUsuario);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        rolViewModel = new ViewModelProvider(this).get(RolViewModel.class);
        rolViewModel.getRolAll().observe(this, new Observer<List<Rol>>() {
            @Override
            public void onChanged(List<Rol> rols) {
                for(Rol roles:rols){
                    if(roles.isRol_is_persona_tea()==false){
                        rolId=roles.getRol_id();
                        System.out.println("rol arriba"+rolId);
                    }

                }
            }
        });

        //ASIGNANDO ROL
      /*  for( Rol rol : rolesArray){
            if(rol.isRol_is_persona_tea()== false){
                rolId=rol.getRol_id();
                System.out.println("rol arriba"+rolId);
            }
        }*/


        //para insertar
        final Button button = findViewById(R.id.guardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                if (validaciones()>0) {
                    System.out.println("está vacio");
                    Toast.makeText(RegistroUsuarioActivity.this,"Es necesario llenar los campos obligatorios",Toast.LENGTH_LONG).show();
                    /*setResult(RESULT_CANCELED, replyIntent)*/;
                } else {
                    System.out.println("no vacio");
                    usuario.setUsuario_nombre(nombreUsuario.getText().toString());
                    usuario.setUsuario_apellido(apellidoUsuario.getText().toString());
                    usuario.setCorreo(correoUsuario.getText().toString());
                    usuario.setContrasenia(contraUsuario.getText().toString());
                    usuario.setRol_id(rolId);
                    System.out.println("rol"+rolId);
                    System.out.println("obtuvo los valores");

                    usuarioViewModel.insert(usuario);
                    System.out.println("en teoria guardo...");
                    Intent acceso = new Intent(RegistroUsuarioActivity.this, MainActivity.class);
                    startActivity(acceso);
                    finish();
                }

            }
        });



    }

    public int validaciones(){
        int validar=0;

        if (TextUtils.isEmpty(nombreUsuario.getText())) {
                validar+=1;
                nombreUsuario.setError(error);
        }else{nombreUsuario.setError(null); }

        if(TextUtils.isEmpty(apellidoUsuario.getText())){
                validar+=1;;
                apellidoUsuario.setError(error);
        }else{apellidoUsuario.setError(null); }

        if(TextUtils.isEmpty(correoUsuario.getText())){
                validar+=1;
                correoUsuario.setError(error);
       }else{correoUsuario.setError(null); }


        if(TextUtils.isEmpty(contraUsuario.getText())){
                validar+=1;
                contraUsuario.setError(error);
       }else{contraUsuario.setError(null); }

        return validar;
    }

}



