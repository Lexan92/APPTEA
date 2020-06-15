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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.pais.PaisViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.config.constantes;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.Usuario;

import static java.security.AccessController.getContext;

public class RegistroUsuarioActivity extends AppCompatActivity {
    public static final String EXTRA_USUARIO = "com.example.apptea.EXTRA_USUARIO";

    public static final int REGISTRO_USUARIO_ACTIVITY_REQUEST_CODE = 1;

    private Spinner spinnerPais;
    int verificacion;
    UsuarioViewModel usuarioViewModel;
    //Se declaran el ViewModel de Pais
    private PaisViewModel paisViewModel;
    //Se declara el List<Pais>
    List<Pais> paisesArray = new ArrayList<>();
    TextInputEditText nombreUsuario;
    TextInputEditText apellidoUsuario;
    TextInputEditText correoUsuario;
    TextInputEditText telefonoUsuario;
    //TextInputEditText direccionUsuario;
    TextInputEditText contraUsuario;
    private Usuario usuario = new Usuario();
    Boolean validar= false;

    String error="Campo Obligatorio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //obteniendo datos del layout
        nombreUsuario=  findViewById(R.id.nombreUsuario);
        apellidoUsuario =  findViewById(R.id.apellidoUsuario);
        correoUsuario=  findViewById(R.id.correoUsuario);
        telefonoUsuario =  findViewById(R.id.telefonoUsuario);
        spinnerPais = (Spinner) findViewById(R.id.spinnerPais);
       // direccionUsuario =  findViewById(R.id.direccionUsuario);
        contraUsuario = findViewById(R.id.contraUsuario);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        //Se crea el adaptador, referenciando el List<Pais>, que es paisesArray
        ArrayAdapter<Pais> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paisesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapter);
        paisViewModel = new ViewModelProvider(this).get(PaisViewModel.class);
        paisViewModel.getPaisAll().observe(this, new Observer<List<Pais>>() {
            @Override
            public void onChanged(List<Pais> paises) {
                adapter.clear();
                adapter.addAll(paises);
                adapter.notifyDataSetChanged();
            }
        });

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
                    usuario.setTelefono(Integer.parseInt(telefonoUsuario.getText().toString()));
                    usuario.setCorreo(correoUsuario.getText().toString());
                    usuario.setPais_id(((Pais) spinnerPais.getSelectedItem()).getPais_id());
                    //usuario.setDireccion(direccionUsuario.getText().toString());
                    usuario.setContrasenia(contraUsuario.getText().toString());
                    usuario.setCodigo_verificacion((int)Math.round(Math.floor(Math.random()*(9999-1000+1)+1000)));

                    System.out.println("obtuvo los valores");

                    usuarioViewModel.insert(usuario);
                    System.out.println("en teoria guardo...");
                    Intent acceso = new Intent(RegistroUsuarioActivity.this, MainActivity.class);
                    startActivity(acceso);
                }
                //finish();
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

        if(TextUtils.isEmpty(telefonoUsuario.getText())){
                validar+=1;
                telefonoUsuario.setError(error);
       }else{telefonoUsuario.setError(null); }

        if(TextUtils.isEmpty(contraUsuario.getText())){
                validar+=1;
                contraUsuario.setError(error);
       }else{contraUsuario.setError(null); }

        return validar;
    }

}



