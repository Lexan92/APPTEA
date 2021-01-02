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

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.example.apptea.ui.rol.RolViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    //String error="Campo Obligatorio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        AdministarSesion administarSesion = new AdministarSesion(getApplicationContext());

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
                 if(administarSesion.getIdioma() == -1){

                    String idioma = Locale.getDefault().getLanguage();
                    int español= 1, ingles= 2;
                    if(idioma.equals(new Locale ("es").getLanguage())|| idioma.equals(new Locale ("ES").getLanguage())){
                        administarSesion.configuracionIdioma(español);
                        System.out.println("Idioma es español " + idioma);
                    }
                    else if(idioma.equals(new Locale ("en").getLanguage())|| idioma.equals(new Locale ("EN").getLanguage())){
                        administarSesion.configuracionIdioma(ingles);
                        System.out.println("Idioma  es ingles " + idioma);
                    }
                    else if(!idioma.equals(new Locale ("en").getLanguage())|| !idioma.equals(new Locale ("EN").getLanguage())
                            ||!idioma.equals(new Locale ("es").getLanguage())|| !idioma.equals(new Locale("ES").getLanguage())){
                        administarSesion.configuracionIdioma(español);
                        System.out.println("Idioma es otro " + idioma);
                    }

                }

                if (validaciones()>0) {
                    System.out.println(getResources().getString(R.string.vacio));
                    Toast.makeText(RegistroUsuarioActivity.this,getResources().getString(R.string.esNecesarioCampoRequerido),Toast.LENGTH_LONG).show();
                    /*setResult(RESULT_CANCELED, replyIntent)*/;
                } else {
                    System.out.println(getResources().getString(R.string.vacio));
                    usuario.setUsuario_nombre(nombreUsuario.getText().toString());
                    usuario.setUsuario_apellido(apellidoUsuario.getText().toString());
                    usuario.setCorreo(correoUsuario.getText().toString());
                    usuario.setContrasenia(contraUsuario.getText().toString());
                    usuario.setRol_id(rolId);
                    System.out.println("rol"+rolId);
                    System.out.println("obtuvo los valores");

                    usuarioViewModel.insert(usuario);
                    System.out.println("en teoria guardo...");
                    Intent acceso = new Intent(RegistroUsuarioActivity.this, ListadoInicioSesion.class);
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
                nombreUsuario.setError(getResources().getString(R.string.esNecesarioCampoRequerido));
        }else{nombreUsuario.setError(null); }

        if(TextUtils.isEmpty(apellidoUsuario.getText())){
                validar+=1;;
                apellidoUsuario.setError(getResources().getString(R.string.esNecesarioCampoRequerido));
        }else{apellidoUsuario.setError(null); }

        if(TextUtils.isEmpty(correoUsuario.getText())){
                validar+=1;
                correoUsuario.setError(getResources().getString(R.string.esNecesarioCampoRequerido));
       }else{correoUsuario.setError(null); }


        if(TextUtils.isEmpty(contraUsuario.getText())){
                validar+=1;
                contraUsuario.setError(getResources().getString(R.string.esNecesarioCampoRequerido));
       }else{contraUsuario.setError(null); }

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



