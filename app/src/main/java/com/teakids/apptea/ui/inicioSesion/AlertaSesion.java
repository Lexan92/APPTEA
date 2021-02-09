package com.teakids.apptea.ui.inicioSesion;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teakids.apptea.MainActivity;
import com.teakids.apptea.R;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.utilidades.AdministarSesion;

import roomsqlite.dao.ResultadoDao;
import roomsqlite.dao.SesionDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Sesion;

public class AlertaSesion extends AppCompatActivity {
    AdministarSesion administarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_sesion);
       administarSesion = new AdministarSesion(AlertaSesion.this);

    }


    public void ContinuarSesion(View view) {
        Intent intent = new Intent(AlertaSesion.this, MainActivity.class);
        intent.putExtra("bandera", false);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.sesionReanudada), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();

    }

    public void DescartarSesion(View view) {
        Sesion sesion;
        int id = administarSesion.obtenerIDSesion();
        SesionDao sesionDao = appDatabase.getDatabase(getApplicationContext()).sesionDao();
        sesion = sesionDao.obtenerSesionPorId(id);
        if (sesion==null){

        }else {
            sesionDao.borrarSesion(sesion);
        }

        ResultadoDao resultadoDao = appDatabase.getDatabase(getApplicationContext()).resultadoDao();
        resultadoDao.borrarResultadoPorId(id);
        administarSesion.cerrarSesionPersonaTea();
        Intent intent = new Intent(AlertaSesion.this, ListadoInicioSesion.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.sesionDescartada), Toast.LENGTH_SHORT).show();
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