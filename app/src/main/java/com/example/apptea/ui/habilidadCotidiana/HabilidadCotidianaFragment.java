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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.NuevaCategoriaDialog;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Secuencia;
import roomsqlite.repositorios.HabilidadCotidianaRepository;
import roomsqlite.repositorios.SecuenciaRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class HabilidadCotidianaFragment extends Fragment implements HabilidadCotidianaAdapter.OnHabilidadListener{

    private HabilidadCotidianaRepository habilidadCotidianaRepository;
    private LiveData<List<HabilidadCotidiana>> HabCotidianaAll;
    RecyclerView recyclerView;
    private HabilidadCotidianaAdapter adapter= null;
    private HabilidadCotidianaViewModel habilidadCotidianaViewModel;
    private CategoriaHabCotidiana categoriaHabCotidiana = null;
    public static final int NEW_HAB_REQUEST_CODE = 1;
    public static final int HAB_UPDATE_REQUEST_CODE = 2;
    SecuenciaViewModel secuenciaViewModel;
    private List<Secuencia> secuenciaList= new ArrayList<>();
    private List<Pictograma> pictoFraseList= new ArrayList<>();;
    PictogramaDAO pictogramaDao= appDatabase.getDatabase(getActivity()).pictogramaDAO();

    public HabilidadCotidianaFragment(){
        //constructor vacio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_detalle_habilidad_cotidiana, container, false);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_hab_cotidiana);
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
                    adapter.setHabiilidad(habilidadCotidianas);
                }
            });
        }

        //Setteando Toolbar para categorias
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categoria: " + categoriaHabCotidiana.getCat_hab_cotidiana_nombre());


        //Boton de + para agregar una nueva categoria
        FloatingActionButton fab = vista.findViewById(R.id.fabHab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SecuenciaFragment.class);
                intent.putExtra("llaveCatHabilidad", categoriaHabCotidiana.getCat_hab_cotidiana_id());
                startActivityForResult(intent, NEW_HAB_REQUEST_CODE);
            }
        });


        return vista;
    }


    @Override
    public void onHabilidadClick(HabilidadCotidiana habilidadCotidiana) {
        int idHab = habilidadCotidiana.getHabilidad_cotidiana_id();
        //Se valida que la lista de pictogramas este vacia (si lo esta se llena) sino se imprime la misma
        if(pictoFraseList.isEmpty() || pictoFraseList.size() == 0){
            secuenciaList = secuenciaViewModel.getSecuenciaById(idHab);
            //Se recorre la lista de secuencias
            for(Secuencia secuencia:secuenciaList){
                //se obtienen mediante referecnia del id de pictogramas
                int idPicto = secuencia.getPictograma_id();
                Pictograma pictograma = pictogramaDao.findbyPictoId(idPicto);
                //lista auxiliar para guardar pictogramas
                pictoFraseList.add(pictograma);
            }
        }else{
            pictoFraseList = pictoFraseList;
        }

        Intent intent = new Intent(getContext(), VistaPreviaActivity.class);
        intent.putExtra("listaSecuencia",(Serializable) pictoFraseList );
        intent.putExtra("vistaDeNiño",1);
        intent.putExtra("nombreHabilidad", habilidadCotidiana.getHabilidad_cotidiana_nombre());
        startActivity(intent);

    }
}
