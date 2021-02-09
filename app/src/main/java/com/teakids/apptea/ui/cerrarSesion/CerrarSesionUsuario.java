package com.teakids.apptea.ui.cerrarSesion;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.teakids.apptea.MainActivity;
import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.teakids.apptea.ui.usuario.UsuarioViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.UtilidadFecha;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import roomsqlite.dao.PersonaTeaDao;
import roomsqlite.dao.ResultadoDao;
import roomsqlite.dao.SesionDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Sesion;
import roomsqlite.entidades.Usuario;

public class CerrarSesionUsuario extends AppCompatActivity {

    LiveData<List<Usuario>> usuario;
    @Override
    protected void onStart() {
        super.onStart();
        ocultarTeclado();
        verificarSesion();
    }

    private void verificarSesion() {
        AdministarSesion administarSesion = new AdministarSesion(this);
        int ban = administarSesion.obtenerTipoUsuario();
        if (ban == 0) {
            administarSesion.setearTipoUsuario(-1);
            administarSesion.cerrarSesionUsuario();
            Intent intent = new Intent(CerrarSesionUsuario.this, ListadoInicioSesion.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }

    private void ocultarTeclado() {
        View vieww = getCurrentFocus();
        if (vieww != null) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_sesion_usuario);

        AdministarSesion administarSesion = new AdministarSesion(this);
        Button guardar, cancelar, descartar;
        TextView nombreUsuario;
        EditText comentario, contraseña;
        contraseña = findViewById(R.id.editTextTextPassword);
        guardar = findViewById(R.id.btn_guardar_sesion);
        cancelar = findViewById(R.id.btn_cancelar_sesion);
        comentario = findViewById(R.id.edit_text_guardar_sesion);
        descartar = findViewById(R.id.btn_descartar);
        UsuarioViewModel usuarioViewModel;
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuario = usuarioViewModel.getUsuarioAll();

        if (administarSesion.obtenerTipoUsuario() == 1) {
            nombreUsuario = findViewById(R.id.text_nombre_usuario);
            int idPersona = administarSesion.obtenerIdPersonaTea();
            PersonaTeaDao personaTeaDao = appDatabase.getDatabase(getApplicationContext()).personaTeaDao();
            PersonaTea personaTea;
            personaTea = personaTeaDao.obtenerPersonaPorId(idPersona);
            nombreUsuario.setText(personaTea.getPersona_nombre().concat(" ").concat(personaTea.getPersona_apellido()));

        }

        //cancelar guardado de la sesion, direcciona al menu principal
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CerrarSesionUsuario.this, MainActivity.class);
                intent.putExtra("bandera", false);
                startActivity(intent);
                finish();
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isAllBlank(contraseña.getText())) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.contraParaConti), Toast.LENGTH_SHORT).show();
                } else {

                    usuario.observe(CerrarSesionUsuario.this, usuarios -> {
                        if (comentario.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.comentarioSesionGuardar), Toast.LENGTH_SHORT).show();
                        } else if (usuarios.get(0).getContrasenia().equals(contraseña.getText().toString())) {
                            Sesion sesion;
                            int id = administarSesion.obtenerIDSesion();
                            SesionDao sesionDao = appDatabase.getDatabase(getApplicationContext()).sesionDao();
                            sesion = sesionDao.obtenerSesionPorId(id);
                            sesion.setComentario(comentario.getText().toString());
                            sesion.setFin_sesion(UtilidadFecha.obtenerFechaHoraActual());
                            sesionDao.actualizarSesion(sesion);
                            administarSesion.setearTipoUsuario(-1);
                            administarSesion.guardarIDSesion(-1);
                            administarSesion.cerrarSesionPersonaTea();
                            Intent intent = new Intent(CerrarSesionUsuario.this, ListadoInicioSesion.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sesionGuardada), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.contraseIncorecta), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });

        descartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isAllBlank(contraseña.getText())) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.escribaContrasDescartar), Toast.LENGTH_SHORT).show();
                } else {


                    usuario.observe(CerrarSesionUsuario.this, usuarios -> {

                        if (usuarios.get(0).getContrasenia().equals(contraseña.getText().toString())) {

                            Sesion sesion;
                            int id = administarSesion.obtenerIDSesion();
                            SesionDao sesionDao = appDatabase.getDatabase(getApplicationContext()).sesionDao();
                            sesion = sesionDao.obtenerSesionPorId(id);
                            sesionDao.borrarSesion(sesion);
                            ResultadoDao resultadoDao = appDatabase.getDatabase(getApplicationContext()).resultadoDao();
                            resultadoDao.borrarResultadoPorId(id);
                            administarSesion.setearTipoUsuario(-1);
                            administarSesion.guardarIDSesion(-1);
                            administarSesion.cerrarSesionPersonaTea();
                            Intent intent = new Intent(CerrarSesionUsuario.this, ListadoInicioSesion.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sesionDescartada), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.contraseIncorecta), Toast.LENGTH_SHORT).show();
                        }


                    });


                }
            }
        });

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


