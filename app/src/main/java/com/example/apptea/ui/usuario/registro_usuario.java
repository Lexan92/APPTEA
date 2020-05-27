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
import androidx.room.Room;

import android.app.Application;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.apptea.R;
import com.example.apptea.ui.pais.PaisViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.config.constantes;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Pais;

import static java.security.AccessController.getContext;

public class registro_usuario extends AppCompatActivity {

    private Spinner spinnerPais;
    appDatabase db;

   /* private LiveData<List<Pais>> paisAll;
    List<Pais> paises = new ArrayList<>();
    private PaisViewModel paisViewModel;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //obteniendo datos del layout
        final TextInputEditText nombreUsuario=  findViewById(R.id.nombreUsuario);
        final TextInputEditText apellidoUsuario =  findViewById(R.id.apellidoUsuario);
        final TextInputEditText correoUsuario=  findViewById(R.id.correoUsuario);
        final TextInputEditText telefonoUsuario =  findViewById(R.id.telefonoUsuario);
        spinnerPais = (Spinner) findViewById(R.id.spinnerPais);
        final TextInputEditText direccionUsuario =  findViewById(R.id.direccionUsuario);
        final TextInputEditText contraUsuario = findViewById(R.id.contraUsuario);

        /*paisAll= paisViewModel.getPaisAll();
        paises =paisAll.getValue();*/

        ArrayList<String> paises = new ArrayList<String>();
        paises.add("El Salvador");
        paises.add("Guatemala");
        paises.add("Honduras");
        paises.add("Nicaragua");
        paises.add("Costa Rica");
        paises.add("Panama");

        ArrayAdapter<CharSequence> adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,paises);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapter);



    }

   /* public void setList(){
        ArrayAdapter<Pais> adapter = new ArrayAdapter<Pais>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, paises);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapter);
    }*/



}
