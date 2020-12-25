package com.example.apptea.ui.habilidadCotidiana;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaViewModel;
import com.example.apptea.ui.configuracion.LocaleHelper;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.TTSManager;
import com.example.apptea.utilidades.TTSManagerSecuencia;
import java.util.ArrayList;
import java.util.List;

import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Secuencia;

public class VistaPreviaActivity extends AppCompatActivity {


    public static final String EXTRA_ID_HAB_UPDATE = "com.example.apptea.ui.habilidadcotidiana.EXTRA_ID_HAB_UPDATE";
    public static final String EXTRA_NOMBRE_HAB_UPDATE = "com.example.apptea.ui.habilidadcotidiana.EXTRA_NOMBRE_HAB_UPDATE";
    public static final String EXTRA_NAME_HAB_UPDATE = "com.example.apptea.ui.habilidadcotidiana.EXTRA_NAME_HAB_UPDATE";
    public static final String UPDATE_HAB_REQUEST_CODE = "com.example.apptea.ui.habilidadcotidiana.UPDATE_HAB_REQUEST_CODE";
    public static final String EXTRA_HAB_PREDETERMINADO_UPDATE = "com.example.apptea.ui.habilidadcotidiana.EXTRA_HAB_PREDETERMINADO_UPDATE";
    private ArrayList<Pictograma> pictoSecuenciaList;
    RecyclerView recyclerView1;
    private PictoSecuenciaAdapter adapterSecuencia;
    private Button atras;
    private Button play;
    private Button guardar;
    private EditText nombreHabilidad;
    TTSManager ttsManager=null;
    TTSManagerSecuencia ttsManagerSecuencia2=null;
    private ArrayList<Secuencia> listaSecuencia;
    private CategoriaHabCotidianaViewModel categoriaHabCotidianaViewModel;
    AdministarSesion idioma ;


    public VistaPreviaActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(R.style.temaHabilidad);
        setContentView(R.layout.activity_vista_previa_secuencia);
        ttsManagerSecuencia2 = new TTSManagerSecuencia();
        ttsManagerSecuencia2.init(getApplicationContext());
        ttsManager = new TTSManager();
        ttsManager.init(getApplicationContext());
        atras = findViewById(R.id.btn_atras);
        atras.setVisibility(View.INVISIBLE);
        play = findViewById(R.id.btn_play);
        play.setVisibility(View.INVISIBLE);
        guardar= findViewById(R.id.btn_save);
        guardar.setVisibility(View.INVISIBLE);
        nombreHabilidad = findViewById(R.id.nombreSecuencia);
        nombreHabilidad.setVisibility(View.INVISIBLE);
        recyclerView1 = findViewById(R.id.recycler_viewSecuencia);
        int idCategoriaHab = getIntent().getIntExtra("idCatHabilidad",0);
        HabilidadCotidianaViewModel habilidadCotidianaViewModel;
        habilidadCotidianaViewModel= new ViewModelProvider(this).get(HabilidadCotidianaViewModel.class);
        SecuenciaViewModel secuenciaViewModel;
        secuenciaViewModel = new ViewModelProvider(this).get(SecuenciaViewModel.class );
        HabilidadCotidianaDao habilidadCotidianaDao= appDatabase.getDatabase(getApplicationContext()).habilidadCotidianaDao();
        boolean bandera = getIntent().getBooleanExtra("definirPantalla",true);
        String tituloHabilidad = getIntent().getStringExtra("nombreHabilidad");
        String titleHabilidad = getIntent().getStringExtra("nameHabilidad");
        boolean editar = getIntent().getBooleanExtra("editar", false);
        int idHabilidad = getIntent().getIntExtra("idHabilidad",0);
        categoriaHabCotidianaViewModel = new ViewModelProvider(this).get(CategoriaHabCotidianaViewModel.class);
        idioma = new AdministarSesion(getApplicationContext());

        //RECYCLER FRASES
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        pictoSecuenciaList = (ArrayList<Pictograma> ) getIntent().getSerializableExtra("listaSecuencia");
        adapterSecuencia = new PictoSecuenciaAdapter((ArrayList<Pictograma>) pictoSecuenciaList);
        recyclerView1.setAdapter(null);




        int milisegundos = 3000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if(bandera == true){
                    nombreHabilidad.setVisibility(View.VISIBLE);
                    play.setVisibility(View.VISIBLE);
                    atras.setVisibility(View.VISIBLE);

                    System.out.println("Idioma es  " + idioma.getIdioma());
                    if(idioma.getIdioma()==1){
                        nombreHabilidad.setText(tituloHabilidad);
                    }else{
                        nombreHabilidad.setText(titleHabilidad);}

                    nombreHabilidad.setEnabled(false);

                }else{
                atras.setVisibility(View.VISIBLE);
                nombreHabilidad.setVisibility(View.VISIBLE);
                    if(idioma.getIdioma()==1){
                        nombreHabilidad.setText(tituloHabilidad);
                    }else{
                        nombreHabilidad.setText(titleHabilidad);}

                play.setVisibility(View.VISIBLE);
                guardar.setVisibility(View.VISIBLE);
                    if(!nombreHabilidad.getText().toString().isEmpty() && editar == false ){

                        if(idioma.getIdioma()==1){
                            nombreHabilidad.setText(tituloHabilidad);
                        }else{
                            nombreHabilidad.setText(titleHabilidad);}

                        nombreHabilidad.setEnabled(false);
                        guardar.setVisibility(View.INVISIBLE);
                    }else{
                        if(idioma.getIdioma()==1){
                            nombreHabilidad.setText(tituloHabilidad);
                        }else{
                            nombreHabilidad.setText(titleHabilidad);}
                    }
                }
                recyclerView1.setAdapter(adapterSecuencia);
                recyclerView1.scrollToPosition(adapterSecuencia.getItemCount());

            }
        }, milisegundos);
        reproducirSecuencia(pictoSecuenciaList);



//Boton atras
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsManager.shutDown();
                ttsManagerSecuencia2.shutDown();
                finish();

            }
        });

        //Boton play
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frase = "";
                for(Pictograma palabra: pictoSecuenciaList){
                    if(idioma.getIdioma()==1){
                        frase+=palabra.getPictograma_nombre()+" ";
                    }else{
                        frase+=palabra.getPictograma_name()+" ";}

                }

                //REPRODUCTOR DE TEXTO A VOZ
                ttsManager.initQueue(frase);

            }

        });

        //Boton guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orden = 0;

                if (TextUtils.isEmpty(nombreHabilidad.getText())) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.debeAgregarNombre),
                            Toast.LENGTH_LONG).show();
                } else {
                    //NUEVO
                    if (editar == false) {

                        HabilidadCotidiana habilidadCotidiana = new HabilidadCotidiana();
                        habilidadCotidiana.setCat_hab_cotidiana_id(idCategoriaHab);

                        habilidadCotidiana.setHabilidad_cotidiana_nombre(nombreHabilidad.getText().toString());
                        habilidadCotidiana.setEveryday_skills_name(nombreHabilidad.getText().toString());
                        habilidadCotidiana.setPredeterminado(false);

                        if (TTSManagerSecuencia.pictogramaSeleccion == null) {
                            TTSManagerSecuencia.pictogramaSeleccion = pictoSecuenciaList.get(0);
                            habilidadCotidiana.setPictograma_id(TTSManagerSecuencia.pictogramaSeleccion.getPictograma_id());
                        } else {
                            habilidadCotidiana.setPictograma_id(TTSManagerSecuencia.pictogramaSeleccion.getPictograma_id());
                        }
                        habilidadCotidianaViewModel.insert(habilidadCotidiana);

                        HabilidadCotidiana habInsertada = habilidadCotidianaDao.obtenerHabilidadCotidiana();

                        for (Pictograma pictograma : pictoSecuenciaList) {
                            //guardado de secuencia
                            Secuencia secuencia = new Secuencia();
                            secuencia.setHabilidad_cotidiana_id(habInsertada.getHabilidad_cotidiana_id());
                            secuencia.setPictograma_id(pictograma.getPictograma_id());
                            secuencia.setSec_predeterminado(true);
                            secuencia.setSecuencia_orden(orden);
                            secuenciaViewModel.insert(secuencia);
                            orden += 1;
                        }
                        TTSManagerSecuencia.pictogramaSeleccion = null;
                    }
                    //EDITAR
                    else {
                        HabilidadCotidiana habInsertada = habilidadCotidianaDao.obtenerUnaHabilidadCotidiana(idHabilidad);

                        if(idioma.getIdioma()==1){
                            habInsertada.setHabilidad_cotidiana_nombre(nombreHabilidad.getText().toString());
                            habInsertada.setEveryday_skills_name(titleHabilidad);
                        }else{
                            habInsertada.setEveryday_skills_name(nombreHabilidad.getText().toString());
                            habInsertada.setHabilidad_cotidiana_nombre(tituloHabilidad);
                        }

                        if (TTSManagerSecuencia.pictogramaSeleccion == null) {
                            TTSManagerSecuencia.pictogramaSeleccion = pictoSecuenciaList.get(0);
                            habInsertada.setPictograma_id(TTSManagerSecuencia.pictogramaSeleccion.getPictograma_id());
                        } else {
                            habInsertada.setPictograma_id(TTSManagerSecuencia.pictogramaSeleccion.getPictograma_id());
                        }
                        habilidadCotidianaViewModel.update(habInsertada);


                        listaSecuencia = (ArrayList<Secuencia>) secuenciaViewModel.getSecuenciaById(habInsertada.getHabilidad_cotidiana_id());

                        for (Secuencia secuencia : listaSecuencia) {
                            secuenciaViewModel.delete(secuencia);
                        }

                        for (Pictograma pictograma : pictoSecuenciaList) {
                            //guardado de secuencia
                            Secuencia secuencia = new Secuencia();
                            secuencia.setHabilidad_cotidiana_id(habInsertada.getHabilidad_cotidiana_id());
                            secuencia.setPictograma_id(pictograma.getPictograma_id());
                            secuencia.setSec_predeterminado(true);
                            secuencia.setSecuencia_orden(orden);
                            secuenciaViewModel.insert(secuencia);
                            orden += 1;
                        }
                        TTSManagerSecuencia.pictogramaSeleccion = null;
                    }
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.habilidadGuardada), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    public void reproducirSecuencia(ArrayList<Pictograma> pictoSecuenciaList) {
        int milisegundos = 1000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String frase = "";
                for (Pictograma palabra : pictoSecuenciaList) {

                    if(idioma.getIdioma()==1){
                        frase += palabra.getPictograma_nombre() + " ";
                    }else{
                        frase += palabra.getPictograma_name() + " ";}

                }

                //REPRODUCTOR DE TEXTO A VOZ
                if(idioma.getIdioma()==1){
                    ttsManagerSecuencia2.initQueue("Repite despues de mi: ");
                }else{
                    ttsManagerSecuencia2.initQueue("Repeat after me: ");}

                ttsManager.initQueue(frase);
            }
        }, milisegundos);

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
