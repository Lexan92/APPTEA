/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.DetalleCategoriaJuego;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teakids.apptea.R;
import com.teakids.apptea.ui.categoriajuego.CategoriaViewModel;
import com.teakids.apptea.ui.juego.JuegoPrincipal;
import com.teakids.apptea.ui.juego.NuevoJuego;
import com.teakids.apptea.ui.juego.PreguntaViewModel;
import com.teakids.apptea.ui.juego.VisorPregunta;
import com.teakids.apptea.ui.juegoMemoria.VisorMemoria;
import com.teakids.apptea.ui.juegoSeleccion.SeleccionaOpcion;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Juego;

public class Detalle_Juego extends Fragment implements JuegoAdapter.OnJuegoListener {

    private JuegoViewModel juegoViewModel;
    RecyclerView recyclerView;
    JuegoAdapter adapter;
    CategoriaViewModel categoriaJuegoViewModel;
    PreguntaViewModel preguntaViewModel;
    LiveData<CategoriaJuego> categoriaJuegoLiveData;

    int key = 0;


    public Detalle_Juego() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        ocultarTeclado();
    }

    private void ocultarTeclado() {
        View vieww = getActivity().getCurrentFocus();
        if (vieww != null) {
            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle__juego, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lista_juegos);
        adapter = new JuegoAdapter(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        juegoViewModel = new ViewModelProvider(getActivity()).get(JuegoViewModel.class);
        preguntaViewModel = new ViewModelProvider(getActivity()).get(PreguntaViewModel.class);
        categoriaJuegoViewModel = new ViewModelProvider(getActivity()).get(CategoriaViewModel.class);
        AdministarSesion idioma = new AdministarSesion(getContext());
        Bundle objetoCategoriaJuego = getArguments();

        if (objetoCategoriaJuego != null) {
            key = objetoCategoriaJuego.getInt("objeto", -1);

            juegoViewModel.findJuegosByCategoriaId(key).observe(getActivity(), new Observer<List<Juego>>() {
                @Override
                public void onChanged(List<Juego> juegos) {

                            //si la llamada  proviene del menu lateral, se agregan todos los juegos al adaptador
                            adapter.setJuegos(juegos);

                    }

            });


        }

        //Definiendo nombre para el toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        categoriaJuegoLiveData = categoriaJuegoViewModel.findById(key);
        categoriaJuegoLiveData.observe(getActivity(), new Observer<CategoriaJuego>() {
            @Override
            public void onChanged(CategoriaJuego categoriaJuego) {
               if (idioma.getIdioma()==1) {
                   toolbar.setTitle(categoriaJuego.getCategoriaJuegoNombre());
               }else{
                   toolbar.setTitle(categoriaJuego.getCategoryNameGame());
               }
            }
        });


        //Boton de + para agregar un nuevo juego
        FloatingActionButton fab = view.findViewById(R.id.fab_nuevo_juego);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NuevoJuego.class);
                //envia ID de categoria de juego
                intent.putExtra("categoriaJuego", key);
                startActivity(intent);

            }
        });


        //METODO PARA ELIMINAR UN JUEGO
        adapter.setButtonClicked(new JuegoAdapter.ButtonClicked() {
            @Override
            public void deleteClickedCatHab(Juego juego) {

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle(getResources().getString(R.string.alerta));
                if(idioma.getIdioma()==1){
                    builder.setMessage(getResources().getString(R.string.estaSeguroEliminarJuego)+"\n" + juego.getJuego_nombre() + "?");
                }else{
                    builder.setMessage(getResources().getString(R.string.estaSeguroEliminarJuego)+"\n" + juego.getName_game() + "?");}

                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton(getResources().getString(R.string.eliminar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        juegoViewModel.delete(juego);
                        adapter.notifyDataSetChanged();

                    }
                });


                builder.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                builder.show();
            }

            public void itemLongClicked(Juego juego) {
                Intent intentSeleccion = new Intent(getActivity(), DialogSeleccionImagenJuego.class);
                intentSeleccion.putExtra("juego",juego.getJuego_id());
                startActivity(intentSeleccion);
            }

        });




    }

    //METODO PARA INGRESAR A LOS JUEGOS CREADOS
    @Override
    public void onJuegoClick(Juego juego) {

        Bundle bundle = getArguments();
        int numero = preguntaViewModel.numeroPreguntas(juego.getJuego_id());
        if (bundle != null) {
            if(key==1){
                //se verifica la cantidad de preguntas que tiene el juego seleccionado

                if (numero > 0) {
                    if (bundle.getBoolean("bandera")) {
                        Intent intent = new Intent(getActivity(), SeleccionaOpcion.class);
                        intent.putExtra("juego", juego);
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(getActivity(), VisorPregunta.class);
                        intent.putExtra("juego", juego);
                        startActivity(intent);
                    }


                } else if (numero == 0) {
                    Intent intent = new Intent(getActivity(), JuegoPrincipal.class);
                    intent.putExtra("juego", juego);
                    startActivity(intent);

                }
            }else if (key==2){


                if (bundle.getBoolean("bandera")) {


                } else {

                    boolean ban_listado = true;
                    Intent intent = new Intent(getActivity(), VisorMemoria.class);
                    intent.putExtra("juego", juego);
                    intent.putExtra("ban_listado", ban_listado);
                    startActivity(intent);
                }


            } else if (numero == 0) {

                boolean ban_listado = true;
                Intent intent = new Intent(getActivity(), VisorMemoria.class);
                intent.putExtra("juego", juego);
                intent.putExtra("ban_listado", ban_listado);
                startActivity(intent);

            }
            }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}