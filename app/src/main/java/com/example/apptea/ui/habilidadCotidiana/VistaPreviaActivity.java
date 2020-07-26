package com.example.apptea.ui.habilidadCotidiana;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptea.R;
import com.example.apptea.utilidades.TTSManagerSecuencia;
import java.util.ArrayList;
import roomsqlite.entidades.Pictograma;

public class VistaPreviaActivity extends AppCompatActivity {

    private ArrayList<Pictograma> pictoSecuenciaList;
    RecyclerView recyclerView1;
    private PictoSecuenciaAdapter adapterSecuencia;
    private Button atras;
    private Button play;
    private Button guardar;
    TTSManagerSecuencia ttsManagerSecuencia=null;

    public VistaPreviaActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_vista_previa_secuencia);

        ttsManagerSecuencia = new TTSManagerSecuencia();
        ttsManagerSecuencia.init(getApplicationContext());
        atras = findViewById(R.id.btn_atras);
        play = findViewById(R.id.btn_play);
        guardar= findViewById(R.id.btn_save);
        recyclerView1 = findViewById(R.id.recycler_viewSecuencia);

        //RECYCLER FRASES
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        pictoSecuenciaList = (ArrayList<Pictograma> ) getIntent().getSerializableExtra("listaSecuencia");
        adapterSecuencia = new PictoSecuenciaAdapter((ArrayList<Pictograma>) pictoSecuenciaList);
        recyclerView1.setAdapter(adapterSecuencia);
        //recyclerView1.scrollToPosition(adapterSecuencia.getItemCount() - 1);



//Boton atras
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Boton atras",
                        Toast.LENGTH_LONG).show();
            }
        });

        //Boton play
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frase = "";
                for(Pictograma palabra: pictoSecuenciaList){
                    frase+=palabra.getPictograma_nombre();

                }
                //REPRODUCTOR DE TEXTO A VOZ
                ttsManagerSecuencia.initQueue(frase);
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
}
