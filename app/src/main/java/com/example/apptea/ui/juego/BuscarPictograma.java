/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.juego;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.entidades.Pictograma;

public class BuscarPictograma extends AppCompatActivity implements PictogramaAdapter.OnPictogramaListener {

    private MaterialToolbar toolbar;
    RecyclerView recyclerView;
    PictogramaAdapter adapter=null;


    PictogramaViewModel pictogramaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pictograma);
        toolbar = findViewById(R.id.topAppBarBusqueda);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.lista_pictograma_busqueda);
         adapter = new PictogramaAdapter(this,this);
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
        getMenuInflater().inflate(R.menu.main,menu);
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
        Log.d("PIC","D: "+posicion.getPictograma_id());
        Intent intentRespuesta = new Intent();
        intentRespuesta.putExtra("id",posicion.getPictograma_id());
        setResult(Activity.RESULT_OK,intentRespuesta);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictogramaViewModel=null;
        Runtime.getRuntime().gc();
    }
}

