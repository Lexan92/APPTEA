package com.teakids.apptea.ui.verificarPin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.teakids.apptea.MainActivity;
import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.teakids.apptea.ui.usuario.UsuarioViewModel;
import com.teakids.apptea.ui.usuario.ValidarCodigo;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.EnviarCorreo;
import com.teakids.apptea.utilidades.GenerarNumAleatorio;
import com.teakids.apptea.utilidades.ValidarConexion;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Usuario;

import static android.view.View.VISIBLE;

public class AccesoPinInicio extends AppCompatActivity {

    TextInputLayout entradaPinLayout;
    EditText entradaPin;
    Button acceder, cancelar,contraseña;
    UsuarioViewModel usuarioViewModel;
    LiveData<List<Usuario>> usuario;
    int codigo;
    public LottieAnimationView sobre;
    FrameLayout contenedor;

    public AccesoPinInicio() {
        //constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_pin_inicio);

        entradaPinLayout = findViewById(R.id.entrada_pin);
        entradaPin = findViewById(R.id.edit_word);
        acceder = findViewById(R.id.boton_acceder);
        cancelar = findViewById(R.id.boton_cancelar);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        contraseña = findViewById(R.id.boton_olvidar_contra);
        contenedor = findViewById(R.id.contenedorAnimado);
        contenedor.setVisibility(View.GONE);
        sobre = findViewById(R.id.sobre);
        sobre.setVisibility(View.INVISIBLE);



        acceder.setOnClickListener(v -> {

            usuario = usuarioViewModel.getUsuarioAll();
            usuario.observe(this, usuarios -> {
                if (entradaPin.getText().toString().isEmpty()) {
                    entradaPinLayout.setError(getResources().getString(R.string.campoVaciIngreseContra));
                } else if (usuarios.get(0).getContrasenia().equals(entradaPin.getText().toString())) {

                    //Se obtiene el usuario guardado se obtiene la primera fila.
                    UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(this).usuarioDao();
                    Usuario usuario = usuarioDao.obtenerUsuario();
                    //se inicializa la sesion
                    AdministarSesion administarSesion = new AdministarSesion(this);
                    administarSesion.guardarSesion(usuario);
                    Intent intent = new Intent(AccesoPinInicio.this, MainActivity.class);
                    boolean bandera =getIntent().hasExtra("bandera");
                    intent.putExtra("bandera", bandera);
                    startActivity(intent);
                    finish();

                } else {
                    entradaPinLayout.setError(getResources().getString(R.string.contraseIncorecta));
                }
            });
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regreso = new Intent(AccesoPinInicio.this, ListadoInicioSesion.class);
                startActivity(regreso);
                finish();

            }
        });


        contraseña.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!ValidarConexion.compruebaConexion(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.conexion_internet), Toast.LENGTH_SHORT).show();
                }else {
                    new AccesoPinInicio.progreso().execute();
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        ocultarTeclado();
    }

    private void ocultarTeclado() {
        View vieww = this.getCurrentFocus();
        if (vieww != null) {
            InputMethodManager input = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

    class progreso extends AsyncTask<Void, Integer,Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            contenedor.setVisibility(VISIBLE);
            contraseña.setVisibility(View.GONE);
            cancelar.setVisibility(View.GONE);
            sobre.setVisibility(View.VISIBLE);
            sobre.playAnimation();

        }

        @Override
        protected Void doInBackground(Void... voids) {


            //Se obtiene el usuario guardado se obtiene la primera fila.
            UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getApplicationContext()).usuarioDao();
            Usuario usuario = usuarioDao.obtenerUsuario();

            //Se genera el codigo aleatorio y se guarda en variable int codigo
            codigo = GenerarNumAleatorio.getNumeroAleatorio();

            //Se inicializa el metodo para enviar correo
            EnviarCorreo enviarCorreo = new EnviarCorreo();
            enviarCorreo.Enviar(codigo,usuario.getCorreo());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.correoConExito),Toast.LENGTH_LONG).show();

            contenedor.setVisibility(View.GONE);
            sobre.setVisibility(View.INVISIBLE);
            contraseña.setVisibility(VISIBLE);
            cancelar.setVisibility(VISIBLE);
            sobre.cancelAnimation();

            Intent intent = new Intent(getApplicationContext(), ValidarCodigo.class);
            intent.putExtra("codigo", codigo);
            startActivity(intent);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)));
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