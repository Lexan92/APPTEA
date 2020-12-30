package com.example.apptea.ui.DetalleCategoriaJuego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaViewModel;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaAdapterBusqueda;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.dao.CategoriaHabCotidianaDao;
import roomsqlite.dao.JuegoDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Pictograma;

public class DialogSeleccionImagenJuego extends AppCompatActivity implements PictogramaAdapter.OnPictogramaListener {

    private MaterialToolbar toolbar;
    RecyclerView recyclerView;
    PictogramaAdapterBusqueda adapter=null;
    private int idJuego = 0;


    PictogramaViewModel pictogramaViewModel;
    JuegoViewModel juegoViewModel;
    JuegoDAO juegoDAO = appDatabase.getDatabase(this).juegoDAO();

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
        pictogramaViewModel = new ViewModelProvider(DialogSeleccionImagenJuego.this).get(PictogramaViewModel.class);
        pictogramaViewModel.getAllPictogramas().observe(this, new Observer<List<Pictograma>>() {
            @Override
            public void onChanged(List<Pictograma> pictogramas) {
                adapter.setPictograma(pictogramas);
            }
        });

        Intent intent = getIntent();
        idJuego = intent.getIntExtra("juego",0);
        juegoViewModel = new ViewModelProvider(DialogSeleccionImagenJuego.this).get(JuegoViewModel.class);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main2,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
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

        Juego juego = juegoDAO.obtenerUnJuego(idJuego);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String p1 = getResources().getString(R.string.agregarImgJuego);
        String p2 = getResources().getString(R.string.agregarImg2);
        String atencion = getResources().getString(R.string.atencion);
        String aceptar = getResources().getString(R.string.aceptar);
        String cancelar = getResources().getString(R.string.cancelar);


        builder.setTitle(" "+atencion);
        builder.setMessage(p1+" "+ juego.getJuego_nombre() +"\n"+p2 +" "+ pictograma.getPictograma_nombre() + "?");
        builder.setIcon(android.R.drawable.ic_dialog_info);

        builder.setPositiveButton(aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                juego.setPictograma_id(pictograma.getPictograma_id());
                juegoViewModel.update(juego);
                adapter.notifyDataSetChanged();
                finish();
            }
        });

        builder.setNegativeButton(cancelar, new DialogInterface.OnClickListener() {
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