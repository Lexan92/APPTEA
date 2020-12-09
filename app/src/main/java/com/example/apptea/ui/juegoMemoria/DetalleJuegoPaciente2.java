package com.example.apptea.ui.juegoMemoria;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoAdapterPaciente;
import com.example.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;
import com.example.apptea.ui.categoriajuego.CategoriaViewModel;
import com.example.apptea.ui.juego.JuegoPrincipal;
import com.example.apptea.ui.juego.NuevoJuego;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.juego.VisorPregunta;
import com.example.apptea.ui.juegoSeleccion.ResultadoViewModel;
import com.example.apptea.ui.juegoSeleccion.SeleccionaOpcion;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.UtilidadFecha;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roomsqlite.dao.DetalleSesionDao;
import roomsqlite.dao.ResultadoDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.DetalleSesion;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Resultado;


public class DetalleJuegoPaciente2 extends Fragment implements JuegoAdapterPaciente2.OnJuegoListener {

    RecyclerView recyclerView;
    JuegoAdapterPaciente2 adapter;
    CategoriaViewModel categoriaJuegoViewModel;
    PreguntaViewModel preguntaViewModel;
    JuegoViewModel juegoViewModel;
    LiveData<CategoriaJuego> categoriaJuegoLiveData;
    List<Juego> juegosConPregunta = new ArrayList<>();
    int key = 0;
    ResultadoViewModel resultadoViewModel;
    ResultadoDao resultadoDao;


    public DetalleJuegoPaciente2() {
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
        return inflater.inflate(R.layout.fragment_detalle_juego_paciente2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.lista_juegos_2);
        adapter = new JuegoAdapterPaciente2(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        juegoViewModel = new ViewModelProvider(getActivity()).get(JuegoViewModel.class);
        preguntaViewModel = new ViewModelProvider(getActivity()).get(PreguntaViewModel.class);
        categoriaJuegoViewModel = new ViewModelProvider(getActivity()).get(CategoriaViewModel.class);
        Bundle objetoCategoriaJuego = getArguments();
        resultadoViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);
        resultadoDao= appDatabase.getDatabase(getActivity()).resultadoDao();

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
        FloatingActionButton fab = view.findViewById(R.id.fab_nuevo_juego_2);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getBoolean("bandera")) {
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //diseña nueva activity
                Intent intent = new Intent(getActivity(), NuevoJuego.class);
                //envia ID de categoria de juego
                intent.putExtra("categoriaJuego", key);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onJuegoClick(Juego juego, View v) {
      /*  Bundle bundle = getArguments();
        if (bundle != null) {
            //se verifica la cantidad de preguntas que tiene el juego seleccionado
            Bundle bundleEnvio = new Bundle();
            AdministarSesion administarSesion = new AdministarSesion(getContext());
            Resultado res = new Resultado();
            int numero = preguntaViewModel.numeroPreguntas(juego.getJuego_id());
            if (numero > 0) {
                if (bundle.getBoolean("bandera")) { //Si viene del menu principal

                    VistaMemoriaPaciente vistaPaciente = new VistaMemoriaPaciente();
                    bundleEnvio.putInt("juegoId", juego.getJuego_id());
                    bundleEnvio.putString("nombreJuego", juego.getJuego_nombre());

                    Navigation.findNavController(v).navigate(R.id.vistaMemoriaPaciente2, bundleEnvio);
                    if (administarSesion.obtenerIDSesion() > 0) {
                        DetalleSesion detalleSesion = new DetalleSesion();
                        detalleSesion.setSesion_id(administarSesion.obtenerIDSesion());
                        Date hora = UtilidadFecha.obtenerFechaHoraActual();
                        detalleSesion.setHora_inicio(hora);
                        detalleSesion.setNombre_opcion("JUEGO: ".concat(juego.getJuego_nombre()));

                        DetalleSesionDao detalleSesionDao = appDatabase.getDatabase(getContext()).detalleSesionDao();
                        detalleSesionDao.insertarDetalleSesion(detalleSesion);

                        //INSERTANDO EN RESULTADO
                        Resultado resultado = new Resultado();
                        resultado.setSesion_id(administarSesion.obtenerIDSesion());
                        resultado.setNombre_juego(juego.getJuego_nombre());
                        resultado.setHora_juego(hora);
                        resultadoViewModel.insertResultado(resultado);

                        res = resultadoDao.obtenerResultado();
                        System.out.println("Resultado id : " + res.getResultado_id());
                        System.out.println("Resultado juego : " + res.getNombre_juego());
                        System.out.println("Resultado SESION : " + res.getSesion_id());

                    }

                    bundleEnvio.putInt("resultado", res.getResultado_id());
                    System.out.println("RESULTADO AFUERA" + res.getResultado_id());
                    vistaPaciente.setArguments(bundleEnvio);

                }
            }
        }*/

    }
}