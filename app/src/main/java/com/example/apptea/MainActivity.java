package com.example.apptea;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

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
