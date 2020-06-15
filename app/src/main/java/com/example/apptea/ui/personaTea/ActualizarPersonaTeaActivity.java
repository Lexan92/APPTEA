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
import java.util.Date;
import java.util.Locale;

import roomsqlite.entidades.PersonaTea;

import static com.example.apptea.ui.personaTea.NuevaPersonaTea.EXTRA_PERSONA;

public class ActualizarPersonaTeaActivity extends AppCompatActivity {
    public static final String EXTRA_ID_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_ID_PERSONA_UPDATE";
    public static final String EXTRA_ID_USUARIO_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_ID_USUARIO_UPDATE";
    public static final String EXTRA_NOMBRE_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_NOMBRE_PERSONA_UPDATE";
    public static final String EXTRA_APELLIDO_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_APELLIDO_PERSONA_UPDATE";
    public static final String EXTRA_FECHA_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_FECHA_PERSONA_UPDATE";
    public static final String EXTRA_SEXO_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_SEXO_PERSONA_UPDATE";
    public static final String EXTRA_FOTO_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_FOTO_PERSONA_UPDATE";
    public static final String EXTRA_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_PERSONA_UPDATE";

    TextInputEditText actualizarNombreTea;
    TextInputEditText actualizarApellidoTea;
    TextInputEditText actualizarFecha;
    private Spinner actualizarSpinnerSexo;
    TextInputEditText actualizarFoto;
    String prueba;
    ArrayList sexo = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    PersonaTea personaTea = new PersonaTea();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_persona_tea);

        actualizarNombreTea = (TextInputEditText)findViewById(R.id.actualizarNombreTea);
        actualizarApellidoTea = (TextInputEditText)findViewById(R.id.actualizarApellidoTea);
        actualizarFecha = (TextInputEditText)findViewById(R.id.actualizarFechaTea);
        actualizarSpinnerSexo = (Spinner)findViewById(R.id.actualizarSexoTea);
        actualizarFoto = (TextInputEditText)findViewById(R.id.actualizarFotoPersona);

        //PARA SETEAR LOS CAMPOS CON LA PERSONA SELECCIONADA
        Intent intent = getIntent();

            actualizarNombreTea.setText(intent.getStringExtra(EXTRA_NOMBRE_PERSONA_UPDATE));
            actualizarApellidoTea.setText(intent.getStringExtra(EXTRA_APELLIDO_PERSONA_UPDATE));
            actualizarFoto.setText(intent.getStringExtra(EXTRA_FOTO_PERSONA_UPDATE));


        ArrayList<String> sexo = new ArrayList<String>();
        sexo.add("Masculino");
        sexo.add("Femenino");

        //PARA EL SPINNER
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,sexo);
        actualizarSpinnerSexo.setAdapter(adapter);
        actualizarSpinnerSexo.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_SEXO_PERSONA_UPDATE)));

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

        actualizarFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ActualizarPersonaTeaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        myCalendar.setTime(new Date(intent.getStringExtra(EXTRA_FECHA_PERSONA_UPDATE)));
        updateLabel();

        //PARA GUARDAR ACTUALIZACION PERSONA
        final Button button = findViewById(R.id.btnActualizarPersona);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(actualizarNombreTea.getText()) || TextUtils.isEmpty(actualizarApellidoTea.getText())
                        ||TextUtils.isEmpty(actualizarFecha.getText())  ||TextUtils.isEmpty(actualizarSpinnerSexo.getSelectedItem().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    personaTea.setPersona_id(intent.getIntExtra(EXTRA_ID_PERSONA_UPDATE,-1));
                    personaTea.setUsuario_id(intent.getIntExtra(EXTRA_ID_USUARIO_UPDATE,-1));
                    personaTea.setPersona_nombre(actualizarNombreTea.getText().toString());
                    personaTea.setPersona_apellido(actualizarApellidoTea.getText().toString());
                    try {
                        personaTea.setPersona_fecha_nac(format.parse(actualizarFecha.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    personaTea.setPersona_foto(actualizarFoto.getText().toString());
                    personaTea.setPersona_sexo(actualizarSpinnerSexo.getSelectedItem().toString());
                    replyIntent.putExtra(EXTRA_PERSONA_UPDATE, personaTea);
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

        actualizarFecha.setText(sdf.format(myCalendar.getTime()));
    }


}
