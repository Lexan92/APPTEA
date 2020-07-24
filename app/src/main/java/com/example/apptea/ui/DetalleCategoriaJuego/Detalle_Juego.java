/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaViewModel;
import com.example.apptea.ui.juego.JuegoPrincipal;
import com.example.apptea.ui.juego.NuevoJuego;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.juego.VisorPregunta;
import com.example.apptea.ui.juegoSeleccion.SeleccionaOpcion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Juego;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detalle_Juego} factory method to
 * create an instance of this fragment.
 */
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

        Bundle objetoCategoriaJuego = getArguments();

        if (objetoCategoriaJuego != null) {
//            categoriaJuego = (CategoriaJuego) objetoCategoriaJuego.getSerializable("objeto");
            key = objetoCategoriaJuego.getInt("objeto", -1);
            juegoViewModel.findJuegosByCategoriaId(key).observe(getActivity(), new Observer<List<Juego>>() {
                @Override
                public void onChanged(List<Juego> juegos) {
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
                toolbar.setTitle(categoriaJuego.getCategoriaJuegoNombre());
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
                builder.setTitle("Alerta");
                builder.setMessage("¿Está seguro de eliminar el Juego :\n" + juego.getJuego_nombre() + "?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        juegoViewModel.delete(juego);
                        adapter.notifyDataSetChanged();

                    }
                });


                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                builder.show();
            }


            @Override
            public void updateClickedCatHab(Juego juego) {
                //PENDIENTE
            }
        });


    }

    //METODO PARA INGRESAR A LOS JUEGOS CREADOS
    @Override
    public void onJuegoClick(Juego juego) {

        Bundle bundle = getArguments();
        if (bundle != null) {

            //se verifica la cantidad de preguntas que tiene el juego seleccionado
            int numero = preguntaViewModel.numeroPreguntas(juego.getJuego_id());
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
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}