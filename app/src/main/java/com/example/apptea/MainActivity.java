package com.example.apptea;


import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.navigation.NavigationView;

import roomsqlite.dao.PersonaTeaDao;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.PersonaTea;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gestion_habilidad, R.id.nav_gestion_juego, R.id.nav_gestion_pictograma, R.id.nav_mi_perfil, R.id.nav_gestion_juego,
                R.id.nav_menu_persona_tea, R.id.menuPrincipalFragment, R.id.detalle_Juego,R.id.cerrarSesionUsuario,R.id.nav_menu_preguntas_frecuentes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //se definen opciones segun tipo de usuario
        if (getIntent().hasExtra("bandera")) {
            Menu menu = navigationView.getMenu();

            boolean bandera = getIntent().getBooleanExtra("bandera", false);

            if (!bandera) {


                menu.removeItem(R.id.nav_mi_perfil);
                menu.removeItem(R.id.nav_gestion_pictograma);
                menu.removeItem(R.id.nav_gestion_habilidad);
                menu.removeItem(R.id.nav_gestion_juego);
                menu.removeItem(R.id.nav_menu_persona_tea);
                menu.removeItem(R.id.nav_menu_configuracion);
                menu.removeItem(R.id.nav_menu_preguntas_frecuentes);

                //llenado de imagen y titulo de usuario de sesion
                View view = navigationView.getHeaderView(0);
                ImageView imageView = view.findViewById(R.id.imageView);
                TextView textView = view.findViewById(R.id.usuario_nav_header);
                AdministarSesion administarSesion = new AdministarSesion(getApplicationContext());
                int idPersona = administarSesion.obtenerIdPersonaTea();
                PersonaTeaDao personaTeaDao = appDatabase.getDatabase(getApplicationContext()).personaTeaDao();
                PersonaTea personaTea;
                personaTea = personaTeaDao.obtenerPersonaPorId(idPersona);

                if (personaTea.getPersona_foto() == null) {
                    if (personaTea.getPersona_sexo().equals("Femenino")) {

                        Glide.with(MainActivity.this)
                                .load(R.drawable.ic_linda)
                                .thumbnail(0.5f)
                                .into(imageView);
                    } else {
                        Glide.with(MainActivity.this)
                                .load(R.drawable.ic_smile)
                                .thumbnail(0.5f)
                                .into(imageView);
                    }


                } else {
                    Glide.with(MainActivity.this)
                            .load(ImageConverter.convertirByteArrayAImagen(personaTea.getPersona_foto()))
                            .thumbnail(0.5f)
                            .into(imageView);
                }
                textView.setText("USUARIO: ".concat(personaTea.getPersona_nombre()).concat(" ").concat(personaTea.getPersona_apellido()));
                navController.navigate(R.id.action_accesoPinInicio_to_nav_home);
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
