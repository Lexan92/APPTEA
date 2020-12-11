package com.example.apptea.ui.configuracion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.verificarPin.AccesoPinInicio;
import com.example.apptea.utilidades.AdministarSesion;

import java.util.ArrayList;


public class configuracionFragment extends Fragment {

    Button spinnerIdioma, spinnerDesbloqueo;
    String [] opcionIdioma, opcionDesbloqueo;
    AdministarSesion administarSesion;


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
        administarSesion = new AdministarSesion(getContext());

        //SETEANDO IDIOMA
        System.out.println("Idioma es en share " +administarSesion.getIdioma());
        if(administarSesion.getIdioma()==1){
            spinnerIdioma.setText(R.string.español);
        }
        else
            spinnerIdioma.setText(R.string.ingles);

        System.out.println("desbloqueo es en share " +administarSesion.getIdioma());
        if(administarSesion.getDesbloqueo()==1){
            spinnerDesbloqueo.setText(R.string.porContrasena);
        }
        else
            spinnerDesbloqueo.setText(R.string.porHuella);



        spinnerIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectIdioma();
            }
        });

        spinnerDesbloqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDesbloqueo();
            }
        });

        return vista;
    }

    private void SelectIdioma(){
        int codespañol= 1, codingles= 2;
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle(getResources().getString(R.string.seleccionarIdioma));

        opcionIdioma = new String[]{getResources().getString(R.string.español), getResources().getString(R.string.ingles) };
        b.setItems(opcionIdioma, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i;
                switch(which){
                    case 0:
                        //ACTUALIZANDO SHAREPREFERENCES
                        administarSesion.configuracionIdioma(codespañol);
                        LocaleHelper.setLocale(getContext(),"es");
                        spinnerIdioma.setText(R.string.español);
                        i = new Intent(getContext(), MainActivity.class);
                        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case 1:
                        //ACTUALIZANDO SHAREPREFERENCES
                        administarSesion.configuracionIdioma(codingles);
                        LocaleHelper.setLocale(getContext(),"en");
                        spinnerIdioma.setText(R.string.ingles);
                        i = new Intent(getContext(), MainActivity.class);
                        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                }
            }
        });
        b.show();
    }

    private void SelectDesbloqueo(){
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle(getResources().getString(R.string.seleccionarDesbloqueo));
        int contrasena=1,huella=2;
        opcionDesbloqueo = new String[]{getResources().getString(R.string.porContrasena), getResources().getString(R.string.porHuella) };

        b.setItems(opcionDesbloqueo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch(which){
                    case 0:
                        //ACTUALIZANDO SHAREPREFERENCES
                        administarSesion.configurarDesbloqueo(contrasena);
                        spinnerDesbloqueo.setText(R.string.porContrasena);
                        break;

                    case 1:
                        //ACTUALIZANDO SHAREPREFERENCES
                        administarSesion.configurarDesbloqueo(huella);
                        spinnerDesbloqueo.setText(R.string.porHuella);
                        break;
                }
            }
        });
        b.show();
    }

    private void updateStrigs(){

    }

}