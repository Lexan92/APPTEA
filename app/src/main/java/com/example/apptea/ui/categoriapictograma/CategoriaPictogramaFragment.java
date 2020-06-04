
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
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;


import com.example.apptea.ui.DetalleCategoriaPictograma.Detalle_Pictograma;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.repositorios.CategoriaPictogramaRepository;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaPictogramaFragment extends Fragment {


    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private CategoriaPictogramaRepository categoriaPictogramaRepository;
    private LiveData<List<CategoriaPictograma>> categoriaPictogramaAll;

    RecyclerView recyclerView;

    private CategoriaPictogramaViewModel categoriaPictogramaViewModel;



    public CategoriaPictogramaFragment() {
        //constructor vacio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_pictograma, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView =  view.findViewById(R.id.lista_categoria_pictograma);
        final CategoriaPictogramaAdapter adapter = new CategoriaPictogramaAdapter(getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);


        categoriaPictogramaViewModel = new ViewModelProvider(getActivity()).get(CategoriaPictogramaViewModel.class);
        categoriaPictogramaViewModel.getAllCategoriaPictograma().observe(getActivity(), new Observer<List<CategoriaPictograma>>() {
            @Override
            public void onChanged(List<CategoriaPictograma> categoriaPictogramas) {
                adapter.setCategoria(categoriaPictogramas);
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Instancia de fragment al cual se dirigira
                Detalle_Pictograma detalle_pictograma =new Detalle_Pictograma();
                //objeto Bundle que encapsula el objeto de tipo CategoriaPictograma
                Bundle  bundleEnvio = new Bundle();
                bundleEnvio.putSerializable("elementos",categoriaPictogramaViewModel.getAllCategoriaPictograma().getValue().get(recyclerView.getChildAdapterPosition(v)));
                detalle_pictograma.setArguments(bundleEnvio);

                //Se define navegacion a siguiente fragment, se manda de parametros ID de fragment y objeto bundle
                Navigation.findNavController(v).navigate(R.id.action_nav_gestion_pictograma_to_detalle_Pictograma,bundleEnvio);

            }
        });

        //Comprobacion para pintar el nombre del toolbar proveniente del menu principal y quitar el FAB
        Bundle objetoBundle = getArguments();
        boolean bandera = false;
        if (objetoBundle!=null){
            bandera =  objetoBundle.getBoolean("bandera");

            if (bandera == true){
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                //toolbar se setea con VOCABULARIO
                toolbar.setTitle("Vocabulario");
                // el FAB se hace invisible
                fab1.setVisibility(View.INVISIBLE);
            }
        }




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