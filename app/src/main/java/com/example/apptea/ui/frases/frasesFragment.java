/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.frases;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;
import com.example.apptea.ui.categoriapictograma.CategoriaPictogramaAdapter;
import com.example.apptea.ui.categoriapictograma.CategoriaPictogramaViewModel;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaViewModel;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;


public class frasesFragment extends Fragment {

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;

    private CategoriaPictogramaViewModel ModelCatPicto;
    private CategoriaPictogramaAdapter adapterCatPicto;
    private PictogramaViewModel ModelPicto;
    private PictogramaAdapter adapterPicto;


    public frasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_frases, container, false);

       // recyclerView1 = vista.findViewById(R.id.recycler_frases);
        recyclerView2 = vista.findViewById(R.id.recycler_categorias);
        //recyclerView3 = vista.findViewById(R.id.recycler_picto);

        adapterCatPicto =new CategoriaPictogramaAdapter(getActivity());
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(adapterCatPicto);
        ModelCatPicto= new ViewModelProvider(getActivity()).get(CategoriaPictogramaViewModel.class);
        ModelCatPicto.getAllCategoriaPictograma().observe(getActivity(), new Observer<List<CategoriaPictograma>>() {
            @Override
            public void onChanged(List<CategoriaPictograma> categoriaPictogramas) {
                adapterCatPicto.setCategoria(categoriaPictogramas);
            }
        });
        return vista;

    }
}
