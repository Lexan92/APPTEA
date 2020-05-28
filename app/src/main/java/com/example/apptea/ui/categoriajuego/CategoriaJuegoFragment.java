

package com.example.apptea.ui.categoriajuego;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.DetalleCategoriaJuego;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.repositorios.CategoriaJuegoRepository;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class CategoriaJuegoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private CategoriaJuegoRepository categoriaJuegoRepository;
    private LiveData<List<CategoriaJuego>> categoriasJuegos;
    RecyclerView recyclerView;
    private CategoriaViewModel categoriaViewModel;


    public CategoriaJuegoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_categoria_juego, container, false);

        recyclerView =  vista.findViewById(R.id.lista_categoria_juego);
        final CategoriaJuegoAdapter adapter = new CategoriaJuegoAdapter(getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);


        categoriaViewModel = new ViewModelProvider(getActivity()).get(CategoriaViewModel.class);
        categoriaViewModel.getAllCategoriasJuegos().observe(getActivity(), new Observer<List<CategoriaJuego>>() {
            @Override
            public void onChanged(List<CategoriaJuego> categoriaJuegos) {
                adapter.setCategoriasJuegos(categoriaJuegos);
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //FragmentTransaction fragment1= FragmentManager.
                //fragment1.replace(R.id.nav_home, new DetalleCategoriaJuego()).addToBackStack(null).commit();


                Toast.makeText(getActivity(),"Click en: " + (categoriaViewModel.getAllCategoriasJuegos()
                        .getValue().get(recyclerView.getChildAdapterPosition(v)).getCategoriaJuegoNombre()), Toast.LENGTH_SHORT ).show();


            }
        });


        return vista;
    }






}
