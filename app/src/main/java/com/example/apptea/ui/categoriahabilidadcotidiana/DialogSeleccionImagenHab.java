package com.example.apptea.ui.categoriahabilidadcotidiana;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.apptea.R;
import com.example.apptea.ui.categoriapictograma.CategoriaPictogramaViewModel;
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaAdapterBusqueda;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.dao.CategoriaHabCotidianaDao;
import roomsqlite.dao.CategoriaPictogramaDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

public class DialogSeleccionImagenHab extends AppCompatActivity implements PictogramaAdapter.OnPictogramaListener {

    private MaterialToolbar toolbar;
    RecyclerView recyclerView;
    PictogramaAdapterBusqueda adapter=null;
    private int idCategoriaHab = 0;



    PictogramaViewModel pictogramaViewModel;
    CategoriaHabCotidianaViewModel categoriaHabCotidianaViewModel;
    CategoriaHabCotidianaDao categoriaHabDAO = appDatabase.getDatabase(this).categoriaHabCotidianaDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.temaSeleccionPicto);
        setContentView(R.layout.activity_buscar_pictograma);
        toolbar = findViewById(R.id.topAppBarBusqueda);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.lista_pictograma_busqueda);
        adapter = new PictogramaAdapterBusqueda(this,this::onPictogramaClick);
        AdministarSesion idioma = new AdministarSesion(getApplicationContext());

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapter);
        pictogramaViewModel = new ViewModelProvider(DialogSeleccionImagenHab.this).get(PictogramaViewModel.class);
        pictogramaViewModel.getAllPictogramas().observe(this, new Observer<List<Pictograma>>() {
            @Override
            public void onChanged(List<Pictograma> pictogramas) {
                adapter.setPictograma(pictogramas);
            }
        });

        Intent intent = getIntent();
        idCategoriaHab = intent.getIntExtra("categoria",0);
        categoriaHabCotidianaViewModel = new ViewModelProvider(DialogSeleccionImagenHab.this).get(CategoriaHabCotidianaViewModel.class);
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

        CategoriaHabCotidiana categoriaHabCotidiana = categoriaHabDAO.obtenerUnaCategoriaHab(idCategoriaHab);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String p1 = getResources().getString(R.string.agregarImg);
        String p2 = getResources().getString(R.string.agregarImg2);
        String atencion = getResources().getString(R.string.atencion);
        String aceptar = getResources().getString(R.string.aceptar);
        String cancelar = getResources().getString(R.string.cancelar);
        AdministarSesion idioma = new AdministarSesion(getApplicationContext());

        if(idioma.getIdioma()==1) {
            builder.setTitle(" " + atencion);
            builder.setMessage(p1 + " " + categoriaHabCotidiana.getCat_hab_cotidiana_nombre() + "\n" + p2 + " " + pictograma.getPictograma_nombre() + "?");
            builder.setIcon(android.R.drawable.ic_dialog_info);
        }else{
            builder.setTitle(" " + atencion);
            builder.setMessage(p1 + " " + categoriaHabCotidiana.getCat_hab_cotidiana_nombre() + "\n" + p2 + " " + pictograma.getPictograma_name() + "?");
            builder.setIcon(android.R.drawable.ic_dialog_info);
        }

        builder.setPositiveButton(aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                categoriaHabCotidiana.setPictograma_id(pictograma.getPictograma_id());
                categoriaHabCotidianaViewModel.update(categoriaHabCotidiana);
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
