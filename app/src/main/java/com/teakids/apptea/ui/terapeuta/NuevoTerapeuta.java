package com.teakids.apptea.ui.terapeuta;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.google.android.material.textfield.TextInputEditText;
import roomsqlite.entidades.Terapeuta;


public class NuevoTerapeuta  extends AppCompatActivity {
    //ACCION
    public static final String EXTRA_EDIT = "com.example.apptea.ui.personaTea.EDIT";
    //NUEVO
    public static final String EXTRA_TERAPEUTA = "com.example.apptea.ui.terapeuta.TERAPEUTA";
    //ACTUALIZAR
    public static final String EXTRA_ID_TERAPEUTA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_ID_TERAPEUTA_UPDATE";
    public static final String EXTRA_ID_PERSONA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_ID_PERSONA_UPDATE";
    public static final String EXTRA_NOMBRE_TERAPEUTA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_NOMBRE_TERAPEUTA_UPDATE";
    public static final String EXTRA_APELLIDO_TERAPEUTA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_APELLIDO_TERAPEUTA_UPDATE";
    public static final String EXTRA_CORREO_TERAPEUTA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_CORREO_TERAPEUTA_UPDATE";
    public static final String EXTRA_TELEFONO_TERAPEUTA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_TELEFONO_TERAPEUTA_UPDATE";
    public static final String EXTRA_TERAPEUTA_UPDATE = "com.example.apptea.ui.terapeuta.EXTRA_TERAPEUTA_UPDATE";

    TextInputEditText nombreTerapeuta;
    TextInputEditText apellidoTerapeuta;
    TextInputEditText correoTerapeuta;
    TextInputEditText telefonoTerapeuta;
    private TextView titulo;
    //String error="Campo Obligatorio";
    private Button cancelar;
    Terapeuta terapeuta = new Terapeuta();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialComponents_Light_Dialog_MinWidth);
        setContentView(R.layout.activity_nuevo_terapeuta);

        titulo = findViewById(R.id.datosTerapeuta);
        nombreTerapeuta = (TextInputEditText)findViewById(R.id.nombreTerapeuta);
        apellidoTerapeuta = (TextInputEditText)findViewById(R.id.apellidoTerapeuta);
        correoTerapeuta = (TextInputEditText)findViewById(R.id.correoTerapeuta);
        telefonoTerapeuta = (TextInputEditText)findViewById(R.id.telefonoTerapeuta);
        cancelar=(Button)findViewById(R.id.cancelarTerapeuta);

        //OBTENIENDO EL ID DEL NIÑO
        int keyPersona =  getIntent().getIntExtra("llavePersona",0);



        //VALIDANDO SI ES EDICION
        Intent intent = getIntent();
        String txtNueTera = getResources().getString(R.string.txtNueTera);
        String txtModiTera= getResources().getString(R.string.txtModiTera);

        if(intent.getIntExtra(EXTRA_EDIT,-1)==1){
            //ES NUEVO
            titulo.setText(txtNueTera);
        }else{
            // ES ACTUALIZACION
            titulo.setText(txtModiTera);
            nombreTerapeuta.setText(intent.getStringExtra(EXTRA_NOMBRE_TERAPEUTA_UPDATE));
            apellidoTerapeuta.setText(intent.getStringExtra(EXTRA_APELLIDO_TERAPEUTA_UPDATE));
            correoTerapeuta.setText(intent.getStringExtra(EXTRA_CORREO_TERAPEUTA_UPDATE));
            telefonoTerapeuta.setText(intent.getStringExtra(EXTRA_TELEFONO_TERAPEUTA_UPDATE));


        }


        //PARA GUARDAR UN TERAPEUTA
        final Button button = findViewById(R.id.guardarTerapeuta);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(validacionesterapeuta()>0) {
                    System.out.println(getResources().getString(R.string.vacio));
                    Toast.makeText(NuevoTerapeuta.this,getResources().getString(R.string.esNecesarioCampoRequerido),Toast.LENGTH_LONG).show();
                    // setResult(RESULT_CANCELED, replyIntent);
                } else {

                    terapeuta.setPersona_id(keyPersona);
                    terapeuta.setTerapeuta_nombre(nombreTerapeuta.getText().toString());
                    terapeuta.setTerapeuta_apellido(apellidoTerapeuta.getText().toString());
                    terapeuta.setTerapeuta_correo(correoTerapeuta.getText().toString());
                    terapeuta.setTerapeuta_telefono(telefonoTerapeuta.getText().toString());

                    //ES NUEVO
                    if (intent.getIntExtra(EXTRA_EDIT,-1)==1){
                        replyIntent.putExtra(EXTRA_TERAPEUTA, terapeuta);
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }
                    // ES ACTUALIZACION
                    else{
                        terapeuta.setTerapeuta_id(intent.getIntExtra(EXTRA_ID_TERAPEUTA_UPDATE,-1));
                        terapeuta.setPersona_id(intent.getIntExtra(EXTRA_ID_PERSONA_UPDATE,-1));

                        replyIntent.putExtra(EXTRA_TERAPEUTA_UPDATE, terapeuta);
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }

                }

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

        //VALIDACIONES
    public int validacionesterapeuta(){
        int validar=0;

        if (TextUtils.isEmpty(nombreTerapeuta.getText())) {
            validar+=1;
            nombreTerapeuta.setError(getResources().getString(R.string.campoRequerido));
        }else{nombreTerapeuta.setError(null); }

        if(TextUtils.isEmpty(apellidoTerapeuta.getText())){
            validar+=1;
            apellidoTerapeuta.setError(getResources().getString(R.string.campoRequerido));
        }else{apellidoTerapeuta.setError(null); }

        if(TextUtils.isEmpty(correoTerapeuta.getText())){
            validar+=1;
            correoTerapeuta.setError(getResources().getString(R.string.campoRequerido));
        }else{correoTerapeuta.setError(null); }

        if(TextUtils.isEmpty(telefonoTerapeuta.getText())){
            validar+=1;
            telefonoTerapeuta.setError(getResources().getString(R.string.campoRequerido));
        }else{telefonoTerapeuta.setError(null); }

        return validar;
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

