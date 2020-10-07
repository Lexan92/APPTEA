package com.example.apptea.ui.DetalleCategoriaJuego;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.apptea.R;
import com.example.apptea.ui.categoriajuego.CategoriaViewModel;
import com.example.apptea.ui.juego.JuegoPrincipal;
import com.example.apptea.ui.juego.NuevoJuego;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.juego.VisorPregunta;
import com.example.apptea.ui.juegoSeleccion.SeleccionaOpcion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Juego;

public class DetalleJuegoPaciente extends Fragment implements JuegoAdapterPaciente.OnJuegoListener {

    RecyclerView recyclerView;
    JuegoAdapterPaciente adapter;
    CategoriaViewModel categoriaJuegoViewModel;
    PreguntaViewModel preguntaViewModel;
    JuegoViewModel juegoViewModel;
    LiveData<CategoriaJuego> categoriaJuegoLiveData;
    List<Juego> juegosConPregunta = new ArrayList<>();
    int key = 0;

    public DetalleJuegoPaciente() {
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
        return inflater.inflate(R.layout.fragment_detalle__juego__paciente, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lista_juegos);
        adapter = new JuegoAdapterPaciente(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        juegoViewModel = new ViewModelProvider(getActivity()).get(JuegoViewModel.class);
        preguntaViewModel = new ViewModelProvider(getActivity()).get(PreguntaViewModel.class);
        categoriaJuegoViewModel = new ViewModelProvider(getActivity()).get(CategoriaViewModel.class);

        Bundle objetoCategoriaJuego = getArguments();

        if (objetoCategoriaJuego != null) {
            key = objetoCategoriaJuego.getInt("objeto", -1);
            juegoViewModel.findJuegosByCategoriaId(key).observe(getActivity(), new Observer<List<Juego>>() {
                @Override
                public void onChanged(List<Juego> juegos) {

                    //se verifica si la llamada proviene del menu principal
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        if (bundle.getBoolean("bandera")) {
                            //se recorre los juegos colocando en el adapter aquellos que si tengan preguntas
                            for (int i = 0; i <= juegos.size() - 1; i++) {
                                int numero = preguntaViewModel.numeroPreguntas(juegos.get(i).getJuego_id());
                                if (numero > 0)
                                    juegosConPregunta.add(juegos.get(i));
                            }
                            adapter.setJuegos(juegosConPregunta);
                        } else {
                            //si la llamada  proviene del menu lateral, se agregan todos los juegos al adaptador
                            adapter.setJuegos(juegos);
                        }
                    }
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
        Bundle bundle = getArguments();
        if (bundle.getBoolean("bandera")) {
            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NuevoJuego.class);
                //envia ID de categoria de juego
                intent.putExtra("categoriaJuego", key);
                startActivity(intent);

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
}