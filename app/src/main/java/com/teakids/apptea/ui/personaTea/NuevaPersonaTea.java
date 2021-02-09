/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.personaTea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.teakids.apptea.R;
import com.teakids.apptea.ui.Utilities.UtilCamara;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.rol.RolViewModel;
import com.teakids.apptea.ui.usuario.UsuarioViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Rol;
import roomsqlite.entidades.Usuario;

public class NuevaPersonaTea extends AppCompatActivity {

    //ACCION
    public static final String EXTRA_EDIT = "com.example.apptea.ui.personaTea.EDIT";
    //NUEVO
    public static final String EXTRA_PERSONA = "com.example.apptea.ui.personaTea.PERSONA";
    //ACTUALIZAR
    public static final String EXTRA_ID_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_ID_PERSONA_UPDATE";
    public static final String EXTRA_ID_USUARIO_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_ID_USUARIO_UPDATE";
    public static final String EXTRA_NOMBRE_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_NOMBRE_PERSONA_UPDATE";
    public static final String EXTRA_APELLIDO_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_APELLIDO_PERSONA_UPDATE";
    public static final String EXTRA_FECHA_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_FECHA_PERSONA_UPDATE";
    public static final String EXTRA_SEXO_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_SEXO_PERSONA_UPDATE";
    public static final String EXTRA_FOTO_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_FOTO_PERSONA_UPDATE";
    public static final String EXTRA_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_PERSONA_UPDATE";

    private RolViewModel rolViewModel;
    int rolId;
    List<Rol> rolesArray = new ArrayList<>();
    TextInputEditText nombreTea;
    TextInputEditText apellidoTea;
    TextInputEditText fecha;
    private Spinner spinnerSexo;
    private TextView titulo;

    ArrayList sexo = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    PersonaTea personaTea = new PersonaTea();
    UsuarioViewModel usuarioViewModel;
    int id;
   // String error=getResources().getString(R.string.campoRequerido);
    private Button camara;
    private ImageView foto;
    private Uri output;
    private boolean tomarfoto=false;
    private  Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_persona_tea);

        titulo = findViewById(R.id.datosTea);
        nombreTea = (TextInputEditText)findViewById(R.id.nombreTea);
        apellidoTea = (TextInputEditText)findViewById(R.id.apellidoTea);
        fecha = (TextInputEditText)findViewById(R.id.fechaTea);
        spinnerSexo = (Spinner)findViewById(R.id.SexoTea);
        foto = (ImageView)findViewById(R.id.fotoPersona);
        camara=(Button)findViewById(R.id.btn_camara);
        cancelar=(Button)findViewById(R.id.cancelarPersona);

        rolViewModel = new ViewModelProvider(this).get(RolViewModel.class);
        rolViewModel.getRolAll().observe(this, new Observer<List<Rol>>() {
            @Override
            public void onChanged(List<Rol> rols) {
                for(Rol roles:rols){
                    if(roles.isRol_is_persona_tea()==true){
                        rolId=roles.getRol_id();
                        System.out.println("rol arriba"+rolId);
                    }

                }

            }
        });

        //ASIGNANDO ROL
       /* for( Rol rol : rolesArray){
            if(rol.isRol_is_persona_tea()== true){
                rolId=rol.getRol_id();
            }
        }*/

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        ArrayList<String> sexo = new ArrayList<String>();
        sexo.add(getResources().getString(R.string.masculino));
        sexo.add(getResources().getString(R.string.femenino));

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

        //OBTENIENDO EL ID
        usuarioViewModel.getUsuarioAll().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                for(Usuario user : usuarios){
                    id= user.getUsuario_id();
                    System.out.println(user.getUsuario_id());
                    System.out.println(user.getUsuario_nombre());
                }

            }
        });

        //VALIDANDO SI ES EDICION
        Intent intent = getIntent();

        if(intent.getIntExtra(EXTRA_EDIT,-1)==1){
            //ES NUEVO
            titulo.setText(getResources().getString(R.string.datosTea));
        }else{
            // ES ACTUALIZACION
            titulo.setText(R.string.actualizarTea);
            nombreTea.setText(intent.getStringExtra(EXTRA_NOMBRE_PERSONA_UPDATE));
            apellidoTea.setText(intent.getStringExtra(EXTRA_APELLIDO_PERSONA_UPDATE));
            spinnerSexo.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_SEXO_PERSONA_UPDATE)));
            myCalendar.setTime(new Date(intent.getStringExtra(EXTRA_FECHA_PERSONA_UPDATE)));
            updateLabel();

            byte[] actualizarfoto= intent.getByteArrayExtra(EXTRA_FOTO_PERSONA_UPDATE);
            if (actualizarfoto==null){
                String sexoAc= intent.getStringExtra(EXTRA_SEXO_PERSONA_UPDATE);
                if (sexoAc.equals(getResources().getString(R.string.femenino))) {
                    foto.setImageResource(R.drawable.ic_linda);
                } else {
                    foto.setImageResource(R.drawable.ic_smile);
                }

            } else {
                foto.setImageBitmap(ImageConverter.convertirByteArrayAImagen(intent.getByteArrayExtra(EXTRA_FOTO_PERSONA_UPDATE)));
            }
        }


        //PARA GUARDAR UNA PERSONA
        final Button button = findViewById(R.id.guardarPersona);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(validacionespersona()>0) {
                    System.out.println(getResources().getString(R.string.vacio));
                    Toast.makeText(NuevaPersonaTea.this,getResources().getString(R.string.esNecesarioCampoRequerido),Toast.LENGTH_LONG).show();
                   // setResult(RESULT_CANCELED, replyIntent);
                } else {

                    personaTea.setUsuario_id(id);
                    personaTea.setPersona_nombre(nombreTea.getText().toString());
                    personaTea.setPersona_apellido(apellidoTea.getText().toString());
                    try {
                        personaTea.setPersona_fecha_nac(format.parse(fecha.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                       if (tomarfoto) {

                           personaTea.setPersona_foto(ImageConverter.convertirImagenAByteArray(((BitmapDrawable) foto.getDrawable()).getBitmap()));
                       }

                    personaTea.setPersona_sexo(spinnerSexo.getSelectedItem().toString());
                    personaTea.setRol_id(rolId);
                    //ES NUEVO
                    if (intent.getIntExtra(EXTRA_EDIT,-1)==1){
                        replyIntent.putExtra(EXTRA_PERSONA, personaTea);
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }
                    // ES ACTUALIZACION
                    else{
                        personaTea.setPersona_id(intent.getIntExtra(EXTRA_ID_PERSONA_UPDATE,-1));
                        personaTea.setUsuario_id(intent.getIntExtra(EXTRA_ID_USUARIO_UPDATE,-1));
                        personaTea.setRol_id(rolId);
                        if(tomarfoto==false){
                            personaTea.setPersona_foto(intent.getByteArrayExtra(EXTRA_FOTO_PERSONA_UPDATE));
                        }
                        replyIntent.putExtra(EXTRA_PERSONA_UPDATE, personaTea);
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }

                }

            }
        });

        //PARA LA CAMARA
        camara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(NuevaPersonaTea.this, UtilCamara.class);
                startActivityForResult(intent, 0);
            }

        });

        //CANCELAR
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    //VALIDACIONES
    public int validacionespersona(){
        int validar=0;

        if (TextUtils.isEmpty(nombreTea.getText())) {
            validar+=1;
            nombreTea.setError(getResources().getString(R.string.campoRequerido));
        }else{nombreTea.setError(null); }

        if(TextUtils.isEmpty(apellidoTea.getText())){
            validar+=1;;
            apellidoTea.setError(getResources().getString(R.string.campoRequerido));
        }else{apellidoTea.setError(null); }

        if(TextUtils.isEmpty(fecha.getText())){
            validar+=1;
            fecha.setError(getResources().getString(R.string.campoRequerido));
        }else{fecha.setError(null); }



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
                Toast.makeText(NuevaPersonaTea.this, (String) (e.toString()),
                        Toast.LENGTH_LONG).show();
                Log.e("ioexception",e.toString());
            }


        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
}
