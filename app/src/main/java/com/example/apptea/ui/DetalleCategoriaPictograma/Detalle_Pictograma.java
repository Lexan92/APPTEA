
package com.example.apptea.ui.DetalleCategoriaPictograma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.apptea.R;

import roomsqlite.entidades.CategoriaPictograma;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detalle_Pictograma#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detalle_Pictograma extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textoTituloCategoria;



    public Detalle_Pictograma() {
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
    public static Detalle_Pictograma newInstance(String param1, String param2) {
        Detalle_Pictograma fragment = new Detalle_Pictograma();
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
        View vista = inflater.inflate(R.layout.fragment_detalle__pictograma, container, false);



        Bundle objetoCategoriaPictograma=getArguments();
        CategoriaPictograma categoriaPictograma = null;
        if(objetoCategoriaPictograma!= null){
            categoriaPictograma = (CategoriaPictograma) objetoCategoriaPictograma.getSerializable("elementos");


        }

        //Definiendo nombre para el toolbar
        Toolbar  toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categoria: " + categoriaPictograma.getCat_pictograma_nombre());


        return vista;
    }
}