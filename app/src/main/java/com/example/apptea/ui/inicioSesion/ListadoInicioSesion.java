package com.example.apptea.ui.inicioSesion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.personaTea.PersonaListadoInicioAdapter;
import com.example.apptea.ui.personaTea.PersonaTeaAdapter;
import com.example.apptea.ui.personaTea.PersonaTeaViewModel;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.List;

import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Usuario;

public class ListadoInicioSesion extends AppCompatActivity {

    private PersonaTeaViewModel personaTeaViewModel;
    RecyclerView recyclerView;
    Button sesionAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_inicio_sesion);

        recyclerView = findViewById(R.id.list_inicio);
        final PersonaListadoInicioAdapter adapter = new PersonaListadoInicioAdapter();
        sesionAdmin = findViewById(R.id.btn_inicio_sesion);

        //Se obtiene el usuario guardado se obtiene la primera fila.
        UsuarioDao usuarioDao = appDatabase.getDatabase(getApplicationContext()).usuarioDao();
        Usuario usuario = usuarioDao.obtenerUsuario();
        sesionAdmin.setText("Iniciar como: " + usuario.getUsuario_nombre());

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
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
                startActivity(intent);
            }
        });


        adapter.setButtonClikedPersona(new PersonaListadoInicioAdapter.ButtonClikedPersona() {
            @Override
            public void itemPersona(PersonaTea personaTea, View v) {
                Intent intent = new Intent(ListadoInicioSesion.this, MainActivity.class);
                AdministarSesion administarSesion = new AdministarSesion(ListadoInicioSesion.this);
                administarSesion.guardarSesionTEA(personaTea);
                intent.putExtra("bandera", false);
                startActivity(intent);

            }
        });
    }


}