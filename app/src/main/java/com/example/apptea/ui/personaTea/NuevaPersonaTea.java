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

package com.example.apptea.ui.personaTea;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.apptea.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import roomsqlite.entidades.PersonaTea;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.apptea.ui.categoriahabilidadcotidiana.NuevaCategoriaDialog.EXTRA_REPLY;


public class NuevaPersonaTea extends Fragment {
    public static final String EXTRA_PERSONA = "com.example.apptea.ui.personaTea.PERSONA";
    TextInputEditText nombreTea;
    TextInputEditText apellidoTea;
    TextInputEditText fecha;
    private Spinner spinnerSexo;
    TextInputEditText foto;
    ArrayList sexo = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    PersonaTea personaTea = new PersonaTea();


    public NuevaPersonaTea() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_nueva_persona_tea, container, false);

        nombreTea = (TextInputEditText)vista.findViewById(R.id.nombreTea);
        apellidoTea = (TextInputEditText)vista.findViewById(R.id.apellidoTea);
        fecha = (TextInputEditText)vista.findViewById(R.id.fechaTea);
        spinnerSexo = (Spinner)vista.findViewById(R.id.SexoTea);
        foto = (TextInputEditText)vista.findViewById(R.id.fotoPersona);

        ArrayList<String> sexo = new ArrayList<String>();
        sexo.add("Masculino");
        sexo.add("Femenino");

        //PARA EL SPINNER
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,sexo);
        spinnerSexo.setAdapter(adapter);

        //PARA EL CALENDAR
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //PARA GUARDAR UNA PERSONA
        final Button button = vista.findViewById(R.id.guardarPersona);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(nombreTea.getText()) || TextUtils.isEmpty(apellidoTea.getText())
                        ||TextUtils.isEmpty(fecha.getText())  ||TextUtils.isEmpty(spinnerSexo.getSelectedItem().toString())) {
                    getActivity().setResult(RESULT_CANCELED, replyIntent);
                } else {

                    personaTea.setPersona_nombre(nombreTea.getText().toString());
                    personaTea.setPersona_apellido(apellidoTea.getText().toString());
                    try {
                        personaTea.setPersona_fecha_nac(format.parse(fecha.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    personaTea.setPersona_foto(foto.getText().toString());
                    personaTea.setPersona_sexo(spinnerSexo.getSelectedItem().toString());
                    replyIntent.putExtra(EXTRA_PERSONA, personaTea);
                    getActivity().setResult(RESULT_OK, replyIntent);
                }
                getActivity().finish();
            }
        });

        return vista;
    }

    //CALENDAR
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fecha.setText(sdf.format(myCalendar.getTime()));
    }

    }
