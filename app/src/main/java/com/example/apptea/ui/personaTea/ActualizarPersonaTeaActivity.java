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
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptea.R;
import com.example.apptea.ui.Utilities.UtilCamara;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import roomsqlite.database.ImageConverter;
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

    private TextView titulo;
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
    private Button camara;
    private ImageView foto;
    private Uri output;
    private boolean tomarfoto=false;
    String error="Campo Obligatorio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_persona_tea);

        /*actualizarNombreTea = (TextInputEditText)findViewById(R.id.actualizarNombreTea);
        actualizarApellidoTea = (TextInputEditText)findViewById(R.id.actualizarApellidoTea);
        actualizarFecha = (TextInputEditText)findViewById(R.id.actualizarFechaTea);
        actualizarSpinnerSexo = (Spinner)findViewById(R.id.actualizarSexoTea);*/

        titulo = findViewById(R.id.datosTea);
        actualizarNombreTea = (TextInputEditText)findViewById(R.id.nombreTea);
        actualizarApellidoTea = (TextInputEditText)findViewById(R.id.apellidoTea);
        actualizarFecha = (TextInputEditText)findViewById(R.id.fechaTea);
        actualizarSpinnerSexo  = (Spinner)findViewById(R.id.SexoTea);
        foto = (ImageView)findViewById(R.id.fotoPersona);
        camara=(Button)findViewById(R.id.btn_camara);

        NuevaPersonaTea funciones = new NuevaPersonaTea();

        //PARA SETEAR LOS CAMPOS CON LA PERSONA SELECCIONADA
        titulo.setText(R.string.actualizarTea);
        Intent intent = getIntent();

            actualizarNombreTea.setText(intent.getStringExtra(EXTRA_NOMBRE_PERSONA_UPDATE));
            actualizarApellidoTea.setText(intent.getStringExtra(EXTRA_APELLIDO_PERSONA_UPDATE));

        byte[] actualizarfoto= intent.getByteArrayExtra(EXTRA_FOTO_PERSONA_UPDATE);
        if (actualizarfoto==null){
            String sexoAc= intent.getStringExtra(EXTRA_SEXO_PERSONA_UPDATE);
            if (sexoAc.equals("Femenino")) {
                foto.setImageResource(R.drawable.ic_linda);
            } else {
                foto.setImageResource(R.drawable.ic_smile);
            }

        } else {
            foto.setImageBitmap(ImageConverter.convertirByteArrayAImagen(intent.getByteArrayExtra(EXTRA_FOTO_PERSONA_UPDATE)));
        }


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
        final Button button = findViewById(R.id.guardarPersona);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(validacionespersona()>0) {
                    System.out.println("está vacio");
                    Toast.makeText(ActualizarPersonaTeaActivity.this,"Es necesario llenar los campos obligatorios",Toast.LENGTH_LONG).show();
                    //setResult(RESULT_CANCELED, replyIntent);
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

                    if (tomarfoto) {

                        personaTea.setPersona_foto(ImageConverter.convertirImagenAByteArray(((BitmapDrawable) foto.getDrawable()).getBitmap()));
                    }
                    personaTea.setPersona_sexo(actualizarSpinnerSexo.getSelectedItem().toString());
                    replyIntent.putExtra(EXTRA_PERSONA_UPDATE, personaTea);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        //PARA LA CAMARA
        camara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ActualizarPersonaTeaActivity.this, UtilCamara.class);
                startActivityForResult(intent, 0);
            }

        });

    }

    //CALENDAR
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        actualizarFecha.setText(sdf.format(myCalendar.getTime()));
    }

    //VALIDACIONES
    public int validacionespersona(){
        int validar=0;

        if (TextUtils.isEmpty(actualizarNombreTea.getText())) {
            validar+=1;
            actualizarNombreTea.setError(error);
        }else{actualizarNombreTea.setError(null); }

        if(TextUtils.isEmpty(actualizarApellidoTea.getText())){
            validar+=1;;
            actualizarApellidoTea.setError(error);
        }else{actualizarApellidoTea.setError(null); }

        if(TextUtils.isEmpty(actualizarFecha.getText())){
            validar+=1;
            actualizarFecha.setError(error);
        }else{actualizarFecha.setError(null); }



        return validar;
    }

    //CAMARA
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //output = (Uri) data.getExtras().get("data");
            ContentResolver cr = this.getContentResolver();

            Bitmap bit = BitmapFactory.decodeByteArray((byte[])data.getExtras().get("data"), 0, ((byte[])data.getExtras().get("data")).length, null);
            int rotation = (int) data.getExtras().get("rotation");
            System.out.println("rotation "+rotation);
            try {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotation);
                bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
                bit = Bitmap.createScaledBitmap(bit, 250, 250, true);
                foto.setImageBitmap(bit);
                tomarfoto=true;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Toast.makeText(ActualizarPersonaTeaActivity.this, (String) (e.toString()),
                        Toast.LENGTH_LONG).show();
                Log.e("ioexception",e.toString());
            }


        }

    }

}
