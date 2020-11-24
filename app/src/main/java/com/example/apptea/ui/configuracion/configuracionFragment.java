package com.example.apptea.ui.configuracion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.apptea.R;

import java.util.ArrayList;


public class configuracionFragment extends Fragment {

    private Spinner spinnerIdioma, spinnerDesbloqueo;
    private Button cambiarConfiguracion;
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
        cambiarConfiguracion = vista.findViewById(R.id.cambiarConfiguracion);

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




        return vista;
    }


}