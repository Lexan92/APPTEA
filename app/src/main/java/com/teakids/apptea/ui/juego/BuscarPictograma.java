/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.juego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.pictograma.PictogramaAdapter;
import com.teakids.apptea.ui.pictograma.PictogramaAdapterBusqueda;
import com.teakids.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.entidades.Pictograma;

public class BuscarPictograma extends AppCompatActivity implements PictogramaAdapter.OnPictogramaListener {

    private MaterialToolbar toolbar;
    RecyclerView recyclerView;
    PictogramaAdapterBusqueda adapter=null;


    PictogramaViewModel pictogramaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pictograma);
        toolbar = findViewById(R.id.topAppBarBusqueda);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.lista_pictograma_busqueda);
        adapter = new PictogramaAdapterBusqueda(this,this::onPictogramaClick);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapter);
        pictogramaViewModel = new ViewModelProvider(BuscarPictograma.this).get(PictogramaViewModel.class);
        pictogramaViewModel.getAllPictogramas().observe(this, new Observer<List<Pictograma>>() {
            @Override
            public void onChanged(List<Pictograma> pictogramas) {
                adapter.setPictograma(pictogramas);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main2,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onPictogramaClick(Pictograma posicion) {

        Intent intentRespuesta = new Intent();
        intentRespuesta.putExtra("id",posicion.getPictograma_id());
        setResult(Activity.RESULT_OK,intentRespuesta);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictogramaViewModel=null;
        adapter=null;
        recyclerView = null;
        Runtime.getRuntime().gc();
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

