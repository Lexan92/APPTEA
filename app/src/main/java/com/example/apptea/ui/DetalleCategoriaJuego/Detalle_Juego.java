/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.google.android.material.appbar.AppBarLayout;

import roomsqlite.entidades.CategoriaJuego;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detalle_Juego#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detalle_Juego extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textoTituloCategoria;
    ActionBar actionBar;


    public Detalle_Juego() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detalle_Juego.
     */
    // TODO: Rename and change types and number of parameters
    public static Detalle_Juego newInstance(String param1, String param2) {
        Detalle_Juego fragment = new Detalle_Juego();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_detalle__juego, container, false);



        textoTituloCategoria = vista.findViewById(R.id.text_titulo_categoria_juego);


        Bundle objetoCategoriaJuego=getArguments();
        CategoriaJuego categoriaJuego = null;
        if(objetoCategoriaJuego!= null){
            categoriaJuego = (CategoriaJuego) objetoCategoriaJuego.getSerializable("objeto");
            textoTituloCategoria.setText(categoriaJuego.getCategoriaJuegoNombre());
        }

        //Definiendo nombre para el toolbar
        Toolbar  toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(categoriaJuego.getCategoriaJuegoNombre());


        return vista;
    }
}