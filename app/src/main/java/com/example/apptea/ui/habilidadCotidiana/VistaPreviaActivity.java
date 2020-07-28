package com.example.apptea.ui.habilidadCotidiana;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptea.R;
import com.example.apptea.utilidades.TTSManager;
import com.example.apptea.utilidades.TTSManagerSecuencia;
import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.Pictograma;

public class VistaPreviaActivity extends AppCompatActivity {

    private ArrayList<Pictograma> pictoSecuenciaList;
    RecyclerView recyclerView1;
    private PictoSecuenciaAdapter adapterSecuencia;
    private Button atras;
    private Button play;
    private Button guardar;
    TTSManager ttsManager=null;
    TTSManagerSecuencia ttsManagerSecuencia2=null;

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
        recyclerView1 = findViewById(R.id.recycler_viewSecuencia);

        //RECYCLER FRASES
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        pictoSecuenciaList = (ArrayList<Pictograma> ) getIntent().getSerializableExtra("listaSecuencia");
        adapterSecuencia = new PictoSecuenciaAdapter((ArrayList<Pictograma>) pictoSecuenciaList);
        recyclerView1.setAdapter(null);



        int milisegundos = 3000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                atras.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                guardar.setVisibility(View.VISIBLE);
                recyclerView1.setAdapter(adapterSecuencia);
                recyclerView1.scrollToPosition(adapterSecuencia.getItemCount());
            }
        }, milisegundos);
        reproducirSecuencia(pictoSecuenciaList);




//Boton atras
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Toast.makeText(getApplicationContext(), "Boton guardar",
                        Toast.LENGTH_LONG).show();
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
