package com.teakids.apptea.ui.DetalleCategoriaJuego;

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

import com.teakids.apptea.R;
import com.teakids.apptea.ui.categoriajuego.CategoriaViewModel;
import com.teakids.apptea.ui.juego.JuegoPrincipal;
import com.teakids.apptea.ui.juego.NuevoJuego;
import com.teakids.apptea.ui.juego.PreguntaViewModel;
import com.teakids.apptea.ui.juego.VisorPregunta;
import com.teakids.apptea.ui.juegoMemoria.VistaMemoriaPaciente;
import com.teakids.apptea.ui.juegoSeleccion.ResultadoViewModel;
import com.teakids.apptea.ui.juegoSeleccion.SeleccionaOpcion;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.UtilidadFecha;
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

public class DetalleJuegoPaciente extends Fragment implements JuegoAdapterPaciente.OnJuegoListener {

    RecyclerView recyclerView;
    JuegoAdapterPaciente adapter;
    CategoriaViewModel categoriaJuegoViewModel;
    PreguntaViewModel preguntaViewModel;
    JuegoViewModel juegoViewModel;
    LiveData<CategoriaJuego> categoriaJuegoLiveData;
    List<Juego> juegosConPregunta = new ArrayList<>();
    int key = 0;

    ResultadoViewModel resultadoViewModel;
    ResultadoDao resultadoDao;

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
        resultadoViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);
        resultadoDao= appDatabase.getDatabase(getActivity()).resultadoDao();
        AdministarSesion idioma = new AdministarSesion(getContext());
        juegosConPregunta.clear();

        Bundle objetoCategoriaJuego = getArguments();

        if (objetoCategoriaJuego != null ) {
            key = objetoCategoriaJuego.getInt("objeto", -1);
            juegoViewModel.findJuegosByCategoriaId(key).observe(getActivity(), new Observer<List<Juego>>() {
                @Override
                public void onChanged(List<Juego> juegos) {

                    //se verifica si la llamada proviene del menu principal
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        if (bundle.getBoolean("bandera")){
                            //se recorre los juegos colocando en el adapter aquellos que si tengan preguntas
                            for (int i = 0; i <= juegos.size() - 1; i++) {
                                int numero = preguntaViewModel.numeroPreguntas(juegos.get(i).getJuego_id());
                                if (numero > 0)
                                    juegosConPregunta.add(juegos.get(i));
                            }
                            adapter.setJuegos(juegosConPregunta);
                        }
                        else{
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
                if (idioma.getIdioma()==1) {
                    toolbar.setTitle(categoriaJuego.getCategoriaJuegoNombre());
                }else{
                    toolbar.setTitle(categoriaJuego.getCategoryNameGame());
                }
            }
        });


        //Boton de + para agregar un nuevo juego
       FloatingActionButton fab = view.findViewById(R.id.fab_nuevo_juego);
       Bundle bundle = getArguments();
        if (bundle!=null&&bundle.getBoolean("bandera")) {
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
    public void onJuegoClick(Juego juego, View v) {

        Bundle bundleEnvio = new Bundle();
        Bundle  bundle = getArguments();
        Resultado res = new Resultado();
        if (bundle != null) {
            AdministarSesion administarSesion = new AdministarSesion(getContext());
            if (key == 2) {
                VistaMemoriaPaciente vistaPaciente = new VistaMemoriaPaciente();
                bundleEnvio.putSerializable("juego",juego);

                if (administarSesion.obtenerIDSesion() > 0) {
                    DetalleSesion detalleSesion = new DetalleSesion();
                    detalleSesion.setSesion_id(administarSesion.obtenerIDSesion());
                    Date hora = UtilidadFecha.obtenerFechaHoraActual();
                    detalleSesion.setHora_inicio(hora);
                    if(administarSesion.getIdioma()==1){
                        detalleSesion.setNombre_opcion(getResources().getString(R.string.juego)+" ".concat(juego.getJuego_nombre()));
                    }else{
                        detalleSesion.setNombre_opcion(getResources().getString(R.string.juego)+" ".concat(juego.getName_game()));}

                    DetalleSesionDao detalleSesionDao = appDatabase.getDatabase(getContext()).detalleSesionDao();
                    detalleSesionDao.insertarDetalleSesion(detalleSesion);

                    //INSERTANDO EN RESULTADO
                    Resultado resultado = new Resultado();
                    resultado.setSesion_id(administarSesion.obtenerIDSesion());
                    if(administarSesion.getIdioma()==1){
                        resultado.setNombre_juego(juego.getJuego_nombre());
                    }else{
                        resultado.setNombre_juego(juego.getName_game());}

                    resultado.setHora_juego(hora);
                    resultadoViewModel.insertResultado(resultado);

                    res = resultadoDao.obtenerResultado();

                }

                bundleEnvio.putInt("resultadoId",res.getResultado_id());
                bundleEnvio.putSerializable("resultado", res);
                bundleEnvio.putBoolean("bandera",bundle.getBoolean("bandera"));
                vistaPaciente.setArguments(bundleEnvio);
                Navigation.findNavController(v).navigate(R.id.vistaMemoriaPaciente2,bundleEnvio);
            } else {


            //se verifica la cantidad de preguntas que tiene el juego seleccionado
            int numero = preguntaViewModel.numeroPreguntas(juego.getJuego_id());
            if (numero > 0) {
                if (bundle.getBoolean("bandera")) { //Si viene del menu principal
                    Intent intent = new Intent(getActivity(), SeleccionaOpcion.class);

                    if (administarSesion.obtenerIDSesion() > 0) {
                        DetalleSesion detalleSesion = new DetalleSesion();
                        detalleSesion.setSesion_id(administarSesion.obtenerIDSesion());
                        Date hora = UtilidadFecha.obtenerFechaHoraActual();
                        detalleSesion.setHora_inicio(hora);
                        if(administarSesion.getIdioma()==1){
                            detalleSesion.setNombre_opcion(getResources().getString(R.string.juego)+" ".concat(juego.getJuego_nombre()));
                        }else{
                            detalleSesion.setNombre_opcion(getResources().getString(R.string.juego)+" ".concat(juego.getName_game()));}

                        DetalleSesionDao detalleSesionDao = appDatabase.getDatabase(getContext()).detalleSesionDao();
                        detalleSesionDao.insertarDetalleSesion(detalleSesion);

                        //INSERTANDO EN RESULTADO
                        Resultado resultado = new Resultado();
                        resultado.setSesion_id(administarSesion.obtenerIDSesion());
                        if(administarSesion.getIdioma()==1){
                            resultado.setNombre_juego(juego.getJuego_nombre());
                        }else{
                            resultado.setNombre_juego(juego.getName_game());}


                        resultado.setHora_juego(hora);
                        resultadoViewModel.insertResultado(resultado);
                        res = resultadoDao.obtenerResultado();
                        intent.putExtra("resultado", res);

                    }


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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();

    }

    @Override
    public void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();

    }
}