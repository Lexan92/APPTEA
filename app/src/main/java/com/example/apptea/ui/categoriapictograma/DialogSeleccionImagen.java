package com.example.apptea.ui.categoriapictograma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.juego.BuscarPictograma;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaAdapterBusqueda;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.dao.CategoriaPictogramaDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

public class DialogSeleccionImagen extends AppCompatActivity implements PictogramaAdapter.OnPictogramaListener {

    private MaterialToolbar toolbar;
    RecyclerView recyclerView;
    PictogramaAdapterBusqueda adapter=null;
    private int idCategoriaPicto = 0;


    PictogramaViewModel pictogramaViewModel;
    CategoriaPictogramaViewModel categoriaPictogramaViewModel;
    CategoriaPictogramaDAO categoriaPictogramaDAO = appDatabase.getDatabase(this).categoriaPictogramaDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.temaSeleccionPicto);
        setContentView(R.layout.activity_buscar_pictograma);
        toolbar = findViewById(R.id.topAppBarBusqueda);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.lista_pictograma_busqueda);
        adapter = new PictogramaAdapterBusqueda(this,this::onPictogramaClick);


        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapter);
        pictogramaViewModel = new ViewModelProvider(DialogSeleccionImagen.this).get(PictogramaViewModel.class);
        pictogramaViewModel.getAllPictogramas().observe(this, new Observer<List<Pictograma>>() {
            @Override
            public void onChanged(List<Pictograma> pictogramas) {
                adapter.setPictograma(pictogramas);
            }
        });

        Intent intent = getIntent();
        idCategoriaPicto = intent.getIntExtra("categoria",0);
        categoriaPictogramaViewModel = new ViewModelProvider(DialogSeleccionImagen.this).get(CategoriaPictogramaViewModel.class);
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
    public void onPictogramaClick(Pictograma pictograma) {

        CategoriaPictograma categoriaPictograma = categoriaPictogramaDAO.obtenerUnaCategoriaPictograma(idCategoriaPicto);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" Alerta");
        builder.setMessage("¿Desea guardar como imagen principal de Categoria: " + categoriaPictograma.getCat_pictograma_nombre()+"\nel pictograma: " + pictograma.getPictograma_nombre() + "?");
        builder.setIcon(android.R.drawable.ic_dialog_info);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                categoriaPictograma.setPictograma_id(pictograma.getPictograma_id());
                categoriaPictogramaViewModel.update(categoriaPictograma);
                System.out.println("categoria"+ categoriaPictograma.getCat_pictograma_nombre());
                adapter.notifyDataSetChanged();
                finish();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictogramaViewModel=null;
        adapter=null;
        recyclerView = null;
        Runtime.getRuntime().gc();
    }


}
