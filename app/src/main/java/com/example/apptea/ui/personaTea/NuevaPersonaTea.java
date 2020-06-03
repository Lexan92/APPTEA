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

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

public class NuevaPersonaTea extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_persona_tea);

        nombreTea = (TextInputEditText)findViewById(R.id.nombreTea);
        apellidoTea = (TextInputEditText)findViewById(R.id.apellidoTea);
        fecha = (TextInputEditText)findViewById(R.id.fechaTea);
        spinnerSexo = (Spinner)findViewById(R.id.SexoTea);
        foto = (TextInputEditText)findViewById(R.id.fotoPersona);

        ArrayList<String> sexo = new ArrayList<String>();
        sexo.add("Masculino");
        sexo.add("Femenino");

        //PARA EL SPINNER
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,sexo);
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
                new DatePickerDialog(NuevaPersonaTea.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //PARA GUARDAR UNA PERSONA
        final Button button = findViewById(R.id.guardarPersona);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(nombreTea.getText()) || TextUtils.isEmpty(apellidoTea.getText())
                        ||TextUtils.isEmpty(fecha.getText())  ||TextUtils.isEmpty(spinnerSexo.getSelectedItem().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    personaTea.setUsuario_id(1);
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
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    //CALENDAR
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fecha.setText(sdf.format(myCalendar.getTime()));
    }

}
