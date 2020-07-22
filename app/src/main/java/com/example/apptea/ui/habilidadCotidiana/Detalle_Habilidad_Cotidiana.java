
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.pictograma.NuevoPictogramaDialog;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detalle_Habilidad_Cotidiana#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detalle_Habilidad_Cotidiana extends Fragment implements PictogramaAdapter.OnPictogramaListener {


    private static final int ACTIVITY_REQUEST_CODE = 10;

    private PictogramaViewModel pictogramaViewModel;
    RecyclerView recyclerView;
    CategoriaPictograma categoriaPictograma = null;
    PictogramaAdapter adapter;


    public Detalle_Habilidad_Cotidiana() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detalle_Pictograma.
     */
    // TODO: Rename and change types and number of parameters
    public static Detalle_Habilidad_Cotidiana newInstance(String param1, String param2) {
        Detalle_Habilidad_Cotidiana fragment = new Detalle_Habilidad_Cotidiana();

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
        return inflater.inflate(R.layout.fragment_detalle__pictograma, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lista_pictogramas);
        adapter = new PictogramaAdapter(getActivity(), Detalle_Habilidad_Cotidiana.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        pictogramaViewModel = new ViewModelProvider(getActivity()).get(PictogramaViewModel.class);


        Bundle objetoCategoriaPictograma = getArguments();

        if (objetoCategoriaPictograma != null) {
            categoriaPictograma = (CategoriaPictograma) objetoCategoriaPictograma.getSerializable("elementos");
            pictogramaViewModel.getAllPictogramaByCategoria(categoriaPictograma.getCat_pictograma_id()).observe(getActivity(), new Observer<List<Pictograma>>() {
                @Override
                public void onChanged(List<Pictograma> pictogramas) {
                    adapter.setPictograma(pictogramas);
                }
            });
        }

        //Definiendo nombre para el toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categoreeeia: " + categoriaPictograma.getCat_pictograma_nombre());


        //Boton de + para agregar un nuevo pictograma
        FloatingActionButton fab = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevoPictogramaDialog.class);
                //envio de ID de categoria
                intent.putExtra("llaveCategoria", categoriaPictograma.getCat_pictograma_id());
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       /* if (adapter != null && recyclerView != null) {
            recyclerView.setAdapter(null);
            adapter = null;
        }*/
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

    @Override
    public void onPictogramaClick(Pictograma posicion) {

    }
}