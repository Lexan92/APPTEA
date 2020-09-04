/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.habilidadCotidiana;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Secuencia;
import roomsqlite.repositorios.HabilidadCotidianaRepository;



/**
 * A simple {@link Fragment} subclass.
 */
public class HabilidadCotidianaFragment extends Fragment implements HabilidadCotidianaAdapter.OnHabilidadListener{

    private HabilidadCotidianaRepository habilidadCotidianaRepository;
    private LiveData<List<HabilidadCotidiana>> HabCotidianaAll;
    RecyclerView recyclerView;
    View cajita;
    private HabilidadCotidianaAdapter adapter= null;
    private HabilidadCotidianaViewModel habilidadCotidianaViewModel;
    private CategoriaHabCotidiana categoriaHabCotidiana = null;
    public static final int NEW_HAB_REQUEST_CODE = 1;
    public static final int HAB_UPDATE_REQUEST_CODE = 2;
    SecuenciaViewModel secuenciaViewModel;
    private List<Secuencia> secuenciaList= new ArrayList<>();
    private List<Pictograma> pictoFraseList= new ArrayList<>();
    PictogramaDAO pictogramaDao= appDatabase.getDatabase(getActivity()).pictogramaDAO();
    boolean bandera=false;


    public HabilidadCotidianaFragment(){
        //constructor vacio
    }

    public static HabilidadCotidianaFragment newInstance(String param1, String param2) {
        HabilidadCotidianaFragment fragment = new HabilidadCotidianaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_detalle_habilidad_cotidiana, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        LottieAnimationView cajita = view.findViewById(R.id.cajita);
        TextView mensaje = view.findViewById(R.id.mensaje);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_hab_cotidiana);
        adapter = new HabilidadCotidianaAdapter(getActivity(), HabilidadCotidianaFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        habilidadCotidianaViewModel = new ViewModelProvider(getActivity()).get(HabilidadCotidianaViewModel.class);
        secuenciaViewModel =  new ViewModelProvider(getActivity()).get(SecuenciaViewModel.class);

        Bundle objetoHabilidad = getArguments();

        if(objetoHabilidad != null){
            categoriaHabCotidiana = (CategoriaHabCotidiana) objetoHabilidad.getSerializable("elementos");
            habilidadCotidianaViewModel.getHabilidadCotidianaAll(categoriaHabCotidiana.getCat_hab_cotidiana_id()).observe(getActivity(), new Observer<List<HabilidadCotidiana>>() {
                @Override
                public void onChanged(List<HabilidadCotidiana> habilidadCotidianas) {
                    if( habilidadCotidianas.size() == 0 || habilidadCotidianas.isEmpty()){
                        cajita.setVisibility(View.VISIBLE);
                        mensaje.setVisibility(View.VISIBLE);
                        cajita.playAnimation();
                    }else{
                        cajita.setVisibility(View.INVISIBLE);
                        mensaje.setVisibility(View.INVISIBLE);
                        cajita.cancelAnimation();
                        adapter.setHabiilidad(habilidadCotidianas);
                    }

                }
            });
        }


        //Setteando Toolbar para categorias
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categoria: " + categoriaHabCotidiana.getCat_hab_cotidiana_nombre());




        //Boton de + para agregar una nueva categoria
        FloatingActionButton fab = view.findViewById(R.id.fabHab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SecuenciaFragment.class);
                intent.putExtra("llaveCatHabilidad", categoriaHabCotidiana.getCat_hab_cotidiana_id());
                startActivityForResult(intent, NEW_HAB_REQUEST_CODE);
            }
        });

        if(objetoHabilidad!=null){
            bandera = objetoHabilidad.getBoolean("ban");

            if(bandera == true){
                fab.setVisibility(View.INVISIBLE);
                adapter.isHabilidad=true;
            }

        }

    }



    @Override
    public void onHabilidadClick(HabilidadCotidiana habilidadCotidiana) {
        int idHab = habilidadCotidiana.getHabilidad_cotidiana_id();
       
        pictoFraseList.clear();
        //Se valida que la lista de pictogramas este vacia (si lo esta se llena) sino se imprime la misma

            secuenciaList = secuenciaViewModel.getSecuenciaById(idHab);
            //Se recorre la lista de secuencias
            for(Secuencia secuencia:secuenciaList){
                //se obtienen mediante referecnia del id de pictogramas
                int idPicto = secuencia.getPictograma_id();
                Pictograma pictograma = pictogramaDao.findbyPictoId(idPicto);
                //lista auxiliar para guardar pictogramas
                pictoFraseList.add(pictograma);
            }


        Intent intent = new Intent(getContext(), VistaPreviaActivity.class);
        intent.putExtra("listaSecuencia",(Serializable) pictoFraseList );
        intent.putExtra("definirPantalla",bandera);
        intent.putExtra("nombreHabilidad", habilidadCotidiana.getHabilidad_cotidiana_nombre());
        startActivity(intent);

    }
}
