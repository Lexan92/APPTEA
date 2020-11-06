package com.example.apptea.ui.inicioSesion;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.personaTea.PersonaListadoInicioAdapter;
import com.example.apptea.ui.personaTea.PersonaTeaAdapter;
import com.example.apptea.ui.personaTea.PersonaTeaViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.UtilidadFecha;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import roomsqlite.dao.SesionDao;
import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Sesion;
import roomsqlite.entidades.Usuario;

public class ListadoInicioSesion extends AppCompatActivity {

    private PersonaTeaViewModel personaTeaViewModel;
    RecyclerView recyclerView;
    Button sesionAdmin;

    @Override
    protected void onStart() {
        super.onStart();

        Date date = UtilidadFecha.obtenerFechaHoraActual();

        Log.d("LEXAN", "HORA: " + date.toString());

        Log.d("LEXAN", "HORA con formato: " + UtilidadFecha.obtenerHora(date));
        Log.d("LEXAN", "Fecha con formato: " + UtilidadFecha.obtenerFecha(date));


    }

    @Override
    protected void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String textSesion= getResources().getString(R.string.txt16);
        setContentView(R.layout.activity_listado_inicio_sesion);
        AdministarSesion administarSesion = new AdministarSesion(ListadoInicioSesion.this);
        recyclerView = findViewById(R.id.list_inicio);
        final PersonaListadoInicioAdapter adapter = new PersonaListadoInicioAdapter();
        sesionAdmin = findViewById(R.id.btn_inicio_sesion);

        //Se obtiene el usuario guardado se obtiene la primera fila.
        UsuarioDao usuarioDao = appDatabase.getDatabase(getApplicationContext()).usuarioDao();
        Usuario usuario = usuarioDao.obtenerUsuario();
        sesionAdmin.setText(textSesion+ usuario.getUsuario_nombre());

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setAdapter(adapter);

        personaTeaViewModel = new ViewModelProvider(ListadoInicioSesion.this).get(PersonaTeaViewModel.class);
        personaTeaViewModel.getPersonaTeaAll().observe(ListadoInicioSesion.this, new Observer<List<PersonaTea>>() {
            @Override
            public void onChanged(List<PersonaTea> personaTeas) {
                adapter.setPersonas(personaTeas);
            }
        });


        sesionAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoInicioSesion.this, MainActivity.class);
                intent.putExtra("bandera", true);
                administarSesion.setearTipoUsuario(0);
                startActivity(intent);
                finish();
            }
        });


        adapter.setButtonClikedPersona(new PersonaListadoInicioAdapter.ButtonClikedPersona() {
            @Override
            public void itemPersona(PersonaTea personaTea, View v) {
                Intent intent = new Intent(ListadoInicioSesion.this, MainActivity.class);
                administarSesion.setearTipoUsuario(1);
                administarSesion.guardarSesionTEA(personaTea);


                //creacion de sesion
                Sesion sesion = new Sesion();
                sesion.setPersona_id(personaTea.getPersona_id());
                sesion.setInicio_sesion(UtilidadFecha.obtenerFechaHoraActual());
                sesion.setFin_sesion(UtilidadFecha.obtenerFechaHoraActual());
                SesionDao sesionDao = appDatabase.getDatabase(getApplicationContext()).sesionDao();
                sesionDao.insertarSesion(sesion);

                Sesion sesion1 = sesionDao.obtenerUltimaSesion();
                administarSesion.guardarIDSesion(sesion1.getSesion_id());

                intent.putExtra("bandera", false);
                startActivity(intent);
                finish();

            }
        });

        //manejo de boton fisico de retroceso
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //al presionar se termina la aplicacion
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(ListadoInicioSesion.this, onBackPressedCallback);
    }


}