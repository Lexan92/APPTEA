package com.example.apptea.ui.cerrarSesion;

import android.content.Context;
import android.content.Intent;
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

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.inicioSesion.ListadoInicioSesion;
import com.example.apptea.ui.usuario.UsuarioViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.UtilidadFecha;

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
                    Toast.makeText(getApplicationContext(), "Escriba la contraseña para continuar", Toast.LENGTH_SHORT).show();
                } else {
                    usuario = usuarioViewModel.getUsuarioAll();
                    usuario.observe(CerrarSesionUsuario.this, usuarios -> {
                        if (usuarios.get(0).getContrasenia().equals(contraseña.getText().toString())) {
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
                            Toast.makeText(getApplicationContext(), "Sesion Guardada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });

        descartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isAllBlank(contraseña.getText())) {
                    Toast.makeText(getApplicationContext(), "Escriba la contraseña para DESCARTAR la sesión", Toast.LENGTH_SHORT).show();
                } else {
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
                    Toast.makeText(getApplicationContext(), "Sesión Descartada", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}


