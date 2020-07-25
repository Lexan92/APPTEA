package com.example.apptea.ui.habilidadCotidiana;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

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

    public VistaPreviaActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_vista_previa_secuencia);

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
                Toast.makeText(getApplicationContext(), "Boton reproducir",
                        Toast.LENGTH_LONG).show();
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
