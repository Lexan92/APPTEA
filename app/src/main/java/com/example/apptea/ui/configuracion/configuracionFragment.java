package com.example.apptea.ui.configuracion;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.ArrayList;


public class configuracionFragment extends Fragment {

    private Spinner spinnerIdioma, spinnerDesbloqueo;
    String opcionIdioma, opcionDesbloqueo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_configuracion, container, false);

        spinnerIdioma = vista.findViewById(R.id.IdiomaSelect);
        spinnerDesbloqueo= vista.findViewById(R.id.DesbloqueoSelect);
        AdministarSesion administarSesion = new AdministarSesion(getContext());
        int español= 1, ingles= 2;


        ArrayList<String> idioma = new ArrayList<String>();
        idioma.add("Español");
        idioma.add("Ingles");

        ArrayList<String> desbloqueo = new ArrayList<String>();
        desbloqueo.add("Por Contraseña");
        desbloqueo.add("Por Huella");

        ArrayAdapter<CharSequence> adapterIdioma = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,idioma);
        spinnerIdioma.setAdapter(adapterIdioma);

        ArrayAdapter<CharSequence> adapterDesbloqueo = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,desbloqueo);
        spinnerDesbloqueo.setAdapter(adapterDesbloqueo);

        //SETEANDO SPINNER
        System.out.println("Idioma es en share " +administarSesion.getIdioma());
        if(administarSesion.getIdioma()==1){
            spinnerIdioma.setSelection(0);
        }
        else
            spinnerIdioma.setSelection(1);


        //SELECCION SPINNER Y ACTUALIZANDO SHAREPREFERENCE
        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        //ACTUALIZANDO SHAREPREFERENCES
                        administarSesion.configuracionIdioma(español);
                        administarSesion.getIdioma();
                        System.out.println("Idioma es español " + administarSesion.getIdioma());
                        break;

                    case 1:
                        //ACTUALIZANDO SHAREPREFERENCES
                        administarSesion.configuracionIdioma(ingles);
                        System.out.println("Idioma  es ingles " + administarSesion.getIdioma());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return vista;
    }


}