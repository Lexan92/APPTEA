package com.teakids.apptea.ui.categoriapictograma;

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

import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.pictograma.PictogramaAdapter;
import com.teakids.apptea.ui.pictograma.PictogramaAdapterBusqueda;
import com.teakids.apptea.ui.pictograma.PictogramaViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
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
        String p1 = getResources().getString(R.string.agregarImg);
        String p2 = getResources().getString(R.string.agregarImg2);
        String atencion = getResources().getString(R.string.atencion);
        String aceptar = getResources().getString(R.string.aceptar);
        String cancelar = getResources().getString(R.string.cancelar);
        AdministarSesion idioma = new AdministarSesion(getApplicationContext());

        if(idioma.getIdioma()==1) {
            builder.setTitle(" " + atencion);
            builder.setMessage(p1 + " " + categoriaPictograma.getCat_pictograma_nombre() + "\n" + p2 + " " + pictograma.getPictograma_nombre() + "?");
            builder.setIcon(android.R.drawable.ic_dialog_info);
        }else{
            builder.setTitle(" " + atencion);
            builder.setMessage(p1 + " " + categoriaPictograma.getCat_pictograma_nombre() + "\n" + p2 + " " + pictograma.getPictograma_name() + "?");
            builder.setIcon(android.R.drawable.ic_dialog_info);
        }

        builder.setPositiveButton(aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                categoriaPictograma.setPictograma_id(pictograma.getPictograma_id());
                categoriaPictogramaViewModel.update(categoriaPictograma);
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
