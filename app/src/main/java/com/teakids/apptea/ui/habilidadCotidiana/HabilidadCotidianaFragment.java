/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.habilidadCotidiana;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.teakids.apptea.R;
import com.teakids.apptea.utilidades.AdministarSesion;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class HabilidadCotidianaFragment extends Fragment implements HabilidadCotidianaAdapter.OnHabilidadListener {


    RecyclerView recyclerView;
    private HabilidadCotidianaAdapter adapter = null;
    private HabilidadCotidianaViewModel habilidadCotidianaViewModel;
    private CategoriaHabCotidiana categoriaHabCotidiana = null;
    public static final int NEW_HAB_REQUEST_CODE = 1;
    public static final int UPDATE_HAB_REQUEST_CODE = 2;
    SecuenciaViewModel secuenciaViewModel;
    private List<Secuencia> secuenciaList = new ArrayList<>();
    private List<Pictograma> pictoFraseList = new ArrayList<>();
    PictogramaDAO pictogramaDao = appDatabase.getDatabase(getActivity()).pictogramaDAO();
    boolean bandera = false;


    public HabilidadCotidianaFragment() {
        //constructor vacio
    }

    public static HabilidadCotidianaFragment newInstance(String param1, String param2) {

        return new HabilidadCotidianaFragment();
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
        AdministarSesion idioma = new AdministarSesion(getContext());
        habilidadCotidianaViewModel = new ViewModelProvider(getActivity()).get(HabilidadCotidianaViewModel.class);
        secuenciaViewModel = new ViewModelProvider(getActivity()).get(SecuenciaViewModel.class);


        Bundle objetoHabilidad = getArguments();

        if (objetoHabilidad != null) {
            categoriaHabCotidiana = (CategoriaHabCotidiana) objetoHabilidad.getSerializable("elementos");
            habilidadCotidianaViewModel.getHabilidadCotidianaAll(categoriaHabCotidiana.getCat_hab_cotidiana_id()).observe(getActivity(), new Observer<List<HabilidadCotidiana>>() {
                @Override
                public void onChanged(List<HabilidadCotidiana> habilidadCotidianas) {
                    if (habilidadCotidianas.size() == 0 || habilidadCotidianas.isEmpty()) {
                        cajita.setVisibility(View.VISIBLE);
                        mensaje.setVisibility(View.VISIBLE);
                        cajita.playAnimation();
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        cajita.setVisibility(View.INVISIBLE);
                        mensaje.setVisibility(View.INVISIBLE);
                        cajita.cancelAnimation();
                        adapter.setHabiilidad(habilidadCotidianas);
                    }

                }
            });
        }

        // METODOS PARA ACTUALIZAR Y ELIMINAR
        adapter.setButtonClicked(new HabilidadCotidianaAdapter.ButtonClicked() {

            @Override
            public void deleteClickedHab(HabilidadCotidiana habilidadCotidiana) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.alerta));
                if(idioma.getIdioma()==1){
                    builder.setMessage(getResources().getString(R.string.estaSeguroEliminarHab)+"\n" + habilidadCotidiana.getHabilidad_cotidiana_nombre() + "?");
                }else{
                    builder.setMessage(getResources().getString(R.string.estaSeguroEliminarHab)+"\n" + habilidadCotidiana.getEveryday_skills_name() + "?");}

                builder.setIcon(android.R.drawable.ic_delete);
                builder.setPositiveButton(getResources().getString(R.string.eliminar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        habilidadCotidianaViewModel.delete(habilidadCotidiana);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deleteDialog = builder.create();
                deleteDialog.show();
            }

            @Override
            public void updateClickedHab(HabilidadCotidiana habilidadCotidiana) {
                editarHabilidad(habilidadCotidiana);
            }
        });


        //Setteando Toolbar para categorias
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        if(idioma.getIdioma()==1){
            toolbar.setTitle(getResources().getString(R.string.categoria)+" " + categoriaHabCotidiana.getCat_hab_cotidiana_nombre());
        }else{
            toolbar.setTitle(getResources().getString(R.string.categoria)+" " + categoriaHabCotidiana.getCat_hab_cotidiana_name());}




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

        if (objetoHabilidad != null) {
            bandera = objetoHabilidad.getBoolean("ban");

            if (bandera == true) {
                fab.setVisibility(View.INVISIBLE);
                adapter.isHabilidad = true;
            }

        }

    }


    public void editarHabilidad(HabilidadCotidiana habilidadCotidiana) {
        int idHab = habilidadCotidiana.getHabilidad_cotidiana_id();


        pictoFraseList.clear();
        //Se valida que la lista de pictogramas este vacia (si lo esta se llena) sino se imprime la misma

        secuenciaList = secuenciaViewModel.getSecuenciaById(idHab);
        //Se recorre la lista de secuencias
        for (Secuencia secuencia : secuenciaList) {
            //se obtienen mediante referecnia del id de pictogramas
            int idPicto = secuencia.getPictograma_id();
            Pictograma pictograma = pictogramaDao.findbyPictoId(idPicto);
            //lista auxiliar para guardar pictogramas
            pictoFraseList.add(pictograma);
        }
        Intent intent = new Intent(getActivity(), EditSecuencia.class);
        intent.putExtra("llaveCatHabilidad", categoriaHabCotidiana.getCat_hab_cotidiana_id());
        intent.putExtra("listaSecuencia", (Serializable) pictoFraseList);
        intent.putExtra("nombreHabilidad", habilidadCotidiana.getHabilidad_cotidiana_nombre());
        intent.putExtra("nameHabilidad", habilidadCotidiana.getEveryday_skills_name());
        intent.putExtra("idHabilidad", habilidadCotidiana.getHabilidad_cotidiana_id());
        intent.putExtra("predeterminadoHabilidad", habilidadCotidiana.isPredeterminado());
        startActivityForResult(intent, UPDATE_HAB_REQUEST_CODE);
    }


    @Override
    public void onHabilidadClick(HabilidadCotidiana habilidadCotidiana) {
        int idHab = habilidadCotidiana.getHabilidad_cotidiana_id();


        pictoFraseList.clear();
        //Se valida que la lista de pictogramas este vacia (si lo esta se llena) sino se imprime la misma

        secuenciaList = secuenciaViewModel.getSecuenciaById(idHab);
        //Se recorre la lista de secuencias
        for (Secuencia secuencia : secuenciaList) {
            //se obtienen mediante referecnia del id de pictogramas
            int idPicto = secuencia.getPictograma_id();
            Pictograma pictograma = pictogramaDao.findbyPictoId(idPicto);
            //lista auxiliar para guardar pictogramas
            pictoFraseList.add(pictograma);
        }


        Intent intent = new Intent(getContext(), VistaPreviaActivity.class);
        intent.putExtra("listaSecuencia", (Serializable) pictoFraseList);
        intent.putExtra("definirPantalla", bandera);
        intent.putExtra("nombreHabilidad", habilidadCotidiana.getHabilidad_cotidiana_nombre());
        intent.putExtra("nameHabilidad", habilidadCotidiana.getEveryday_skills_name());
        startActivity(intent);

    }

}
