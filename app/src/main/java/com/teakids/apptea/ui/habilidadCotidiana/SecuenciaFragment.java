package com.teakids.apptea.ui.habilidadCotidiana;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teakids.apptea.R;
import com.teakids.apptea.ui.categoriapictograma.CategoriaPictogramaViewModel;
import com.teakids.apptea.ui.configuracion.LocaleHelper;
import com.teakids.apptea.ui.frases.CatPictoFrasesAdapter;
import com.teakids.apptea.ui.frases.PictoFrasesAdapter;
import com.teakids.apptea.ui.pictograma.PictogramaViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.TTSManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;

public class SecuenciaFragment extends AppCompatActivity{

    private List<Pictograma> pictoFraseList;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    private ImageButton ver;
    private ImageButton backspace;
    TTSManager ttsManager=null;

    private CategoriaPictogramaViewModel ModelCatPicto;
    private CatPictoFrasesAdapter adapterCatPicto;
    private CategoriaPictograma CatPicto;

    private PictogramaViewModel ModelPicto;
    private PictoFrasesAdapter adapterPicto;

    private PictoSecuenciaAdapter adapterFrases;

    public SecuenciaFragment() {
    }

@Override
    protected void  onCreate(Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_crear_secuencia);

    ttsManager = new TTSManager();
    ttsManager.init(getApplicationContext());
    recyclerView1 = findViewById(R.id.recycler_secuencia);
    recyclerView2 = findViewById(R.id.recycler_categorias);
    recyclerView3 = findViewById(R.id.recycler_picto);
    ver = findViewById(R.id.btn_save);
    backspace = findViewById(R.id.btn_backspace);
    int catHabilidadId = getIntent().getIntExtra("llaveCatHabilidad",0);
    AdministarSesion idioma = new AdministarSesion(getApplicationContext());
    //RECYCLER FRASES
    recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    pictoFraseList = new ArrayList<Pictograma>();


    //RECYCLER CATEGORIA PICTOGRAMAS
    adapterCatPicto = new CatPictoFrasesAdapter(new FragmentActivity());
    recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    recyclerView2.setAdapter(adapterCatPicto);
    ModelCatPicto = new ViewModelProvider(this).get(CategoriaPictogramaViewModel.class);

    //RECYCLER PICTOGRAMAS
    adapterPicto = new PictoFrasesAdapter(new FragmentActivity());
    recyclerView3.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
    recyclerView3.setAdapter(adapterPicto);
    ModelPicto = new ViewModelProvider(this).get(PictogramaViewModel.class);

    //LLENANDO RECYCLER CATEGORIAS
    ModelCatPicto.getAllCategoriaPictograma().observe(this, new Observer<List<CategoriaPictograma>>() {
        @Override
        public void onChanged(List<CategoriaPictograma> categoriaPictogramas) {
            adapterCatPicto.setCategoria(categoriaPictogramas);
            //CatPicto = categoriaPictogramas.get((int) adapterCatPicto.getItemId(0));
            CatPicto = categoriaPictogramas.get(0);
            //System.out.println("en el fragment" + CatPicto.getCat_pictograma_nombre());
            //INICIALIZANDO SEGUNDO RECYCLER PICTOGRAMAS
            ItemClicked(CatPicto);
        }
    });


    //ONCLICK CATEGORIAS
    adapterCatPicto.setClickedCatPicto(new CatPictoFrasesAdapter.ClickedCatPicto() {
        @Override
        public void CategoriaClicked(CategoriaPictograma categoriaPictograma) {
            ItemClicked(categoriaPictograma);
        }
    });

    //ONCLICK PICTOGRAMA
    adapterPicto.setClickedPicto(new PictoFrasesAdapter.ClickedPicto() {
        @Override
        public void PictoClicked(Pictograma pictograma) {
            //System.out.println("en el fragment" + pictograma.getPictograma_nombre());
            //REPRODUCIENDO NOMBRE
            if(idioma.getIdioma()==1){
                ttsManager.initQueue(pictograma.getPictograma_nombre());
            }else{
                ttsManager.initQueue(pictograma.getPictograma_name());}


            //FRASE
            adapterFrases = new PictoSecuenciaAdapter((ArrayList<Pictograma>) pictoFraseList);
            pictoFraseList.add(pictograma);
            recyclerView1.setAdapter(adapterFrases);
            recyclerView1.scrollToPosition(adapterFrases.getItemCount() - 1);
        }
    });

    backspace.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (pictoFraseList.size()>0) {
                pictoFraseList.remove(pictoFraseList.size() - 1);
                adapterFrases.notifyDataSetChanged();
            }
        }
    });


    ver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(pictoFraseList.isEmpty() || pictoFraseList.size() == 0){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.paraVerDebe),
                        Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(getApplicationContext(), VistaPreviaActivity.class);
                intent.putExtra("listaSecuencia",(Serializable) pictoFraseList);
                intent.putExtra("idCatHabilidad",catHabilidadId);
                intent.putExtra("definirPantalla",false);
                startActivity(intent);
            }
        }
    });

}


    //LLENADO SEGUNDO RECYCLER
    public void ItemClicked (CategoriaPictograma categoriaPictograma){

        ModelPicto.getAllPictogramaByCategoria(categoriaPictograma.getCat_pictograma_id()).observe(this, new Observer<List<Pictograma>>() {
            @Override
            public void onChanged(List<Pictograma> pictogramas) {
                adapterPicto.setPictograma(pictogramas);
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
