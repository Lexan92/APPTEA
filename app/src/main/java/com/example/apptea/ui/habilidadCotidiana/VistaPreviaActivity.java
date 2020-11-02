package com.example.apptea.ui.habilidadCotidiana;

import android.animation.ObjectAnimator;
import android.content.Intent;
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
        boolean editar = getIntent().getBooleanExtra("editar", false);
        int idHabilidad = getIntent().getIntExtra("idHabilidad",0);
        categoriaHabCotidianaViewModel = new ViewModelProvider(this).get(CategoriaHabCotidianaViewModel.class);


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
                    nombreHabilidad.setText(tituloHabilidad);
                    nombreHabilidad.setEnabled(false);

                }else{
                atras.setVisibility(View.VISIBLE);
                nombreHabilidad.setVisibility(View.VISIBLE);
                nombreHabilidad.setText(tituloHabilidad);
                play.setVisibility(View.VISIBLE);
                guardar.setVisibility(View.VISIBLE);
                    if(!nombreHabilidad.getText().toString().isEmpty() && editar == false ){
                        nombreHabilidad.setText(tituloHabilidad);
                        nombreHabilidad.setEnabled(false);
                        guardar.setVisibility(View.INVISIBLE);
                    }else{
                        nombreHabilidad.setText(tituloHabilidad);
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
                    frase+=palabra.getPictograma_nombre()+" ";
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
                    Toast.makeText(getApplicationContext(), "Debes agregar un nombre",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (editar == false) {

                        HabilidadCotidiana habilidadCotidiana = new HabilidadCotidiana();
                        habilidadCotidiana.setCat_hab_cotidiana_id(idCategoriaHab);
                        habilidadCotidiana.setHabilidad_cotidiana_nombre(nombreHabilidad.getText().toString());
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
                    } else {
                        HabilidadCotidiana habInsertada = habilidadCotidianaDao.obtenerUnaHabilidadCotidiana(idHabilidad);
                        habInsertada.setHabilidad_cotidiana_nombre(nombreHabilidad.getText().toString());
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
                    Toast.makeText(getApplicationContext(), "Habilidad Cotidiana Guardada ", Toast.LENGTH_LONG).show();
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
                    frase += palabra.getPictograma_nombre() + " ";
                }

                //REPRODUCTOR DE TEXTO A VOZ
                ttsManagerSecuencia2.initQueue("Repite despues de mi: ");
                ttsManager.initQueue(frase);
            }
        }, milisegundos);

    }
}
