
package com.example.apptea.ui.categoriapictograma;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.repositorios.CategoriaPictogramaRepository;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaPictogramaFragment extends Fragment {


    private CategoriaPictogramaRepository categoriaPictogramaRepository;
    private LiveData<List<CategoriaPictograma>> categoriaPictogramaAll;
    RecyclerView recyclerView;
    private CategoriaPictogramaViewModel categoriaPictogramaViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public CategoriaPictogramaFragment() {
        //constructor vacio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_pictograma, container, false);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_cat_pictograma);
        final CategoriaPictogramaAdapter adapter = new CategoriaPictogramaAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        categoriaPictogramaViewModel = new ViewModelProvider(getActivity()).get(CategoriaPictogramaViewModel.class);
        categoriaPictogramaViewModel.getAllCategoriaPictograma().observe(getActivity(), new Observer<List<CategoriaPictograma>>() {
            @Override
            public void onChanged(@Nullable final List<CategoriaPictograma> categoriaPictogramaList) {
                // Update the cached copy of the words in the adapter.
                adapter.setCategoria(categoriaPictogramaList);
            }
        });

        FloatingActionButton fab1 = vista.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoriaNueva.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //Comprobacion para pintar el nombre del toolbar proveniente del menu principal
        Bundle objetoBundle = getArguments();
        boolean bandera = false;
        if (objetoBundle!=null){
            bandera =  objetoBundle.getBoolean("bandera");

            if (bandera == true){
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Vocabulario");
            }
        }

        return vista;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CategoriaPictograma categoria = (CategoriaPictograma) data.getSerializableExtra(CategoriaNueva.EXTRA_REPLY);
            categoriaPictogramaViewModel.insert(categoria);
        } else {
            Toast.makeText(getActivity(), R.string.vacio_cat_hab_cot,
                    Toast.LENGTH_LONG).show();
        }
    }

}