/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.frases;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;
import com.example.apptea.ui.categoriapictograma.CategoriaPictogramaViewModel;
import com.example.apptea.ui.pictograma.PictogramaAdapter;
import com.example.apptea.ui.pictograma.PictogramaViewModel;

import java.util.ArrayList;
import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;


public class frasesFragment extends Fragment {

    private List<Pictograma> pictoFraseList;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;

    private CategoriaPictogramaViewModel ModelCatPicto;
    private CatPictoFrasesAdapter adapterCatPicto;
    private CategoriaPictograma CatPicto;

    private PictogramaViewModel ModelPicto;
    private PictoFrasesAdapter adapterPicto;

    private frasesAdapter adapterFrases;

    public frasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_frases, container, false);


        recyclerView1 = vista.findViewById(R.id.recycler_frases);
        recyclerView2 = vista.findViewById(R.id.recycler_categorias);
        recyclerView3 = vista.findViewById(R.id.recycler_picto);


        //RECYCLER FRASES
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        pictoFraseList = new ArrayList<Pictograma>();



        //RECYCLER CATEGORIA PICTOGRAMAS
        adapterCatPicto =new CatPictoFrasesAdapter(getActivity());
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(adapterCatPicto);
        ModelCatPicto= new ViewModelProvider(getActivity()).get(CategoriaPictogramaViewModel.class);

        //RECYCLER PICTOGRAMAS
        adapterPicto=new PictoFrasesAdapter(getActivity());
        recyclerView3.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView3.setAdapter(adapterPicto);
        ModelPicto= new ViewModelProvider(getActivity()).get(PictogramaViewModel.class);

        //LLENANDO RECYCLER CATEGORIAS
        ModelCatPicto.getAllCategoriaPictograma().observe(getActivity(), new Observer<List<CategoriaPictograma>>() {
            @Override
            public void onChanged(List<CategoriaPictograma> categoriaPictogramas) {
                adapterCatPicto.setCategoria(categoriaPictogramas);
                //CatPicto = categoriaPictogramas.get((int) adapterCatPicto.getItemId(0));
                CatPicto= categoriaPictogramas.get(0);
                System.out.println("en el fragment"+ CatPicto.getCat_pictograma_nombre());
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
                System.out.println("en el fragment"+ pictograma.getPictograma_nombre());
                adapterFrases = new frasesAdapter((ArrayList<Pictograma>) pictoFraseList);
                pictoFraseList.add(pictograma);
                recyclerView1.setAdapter(adapterFrases);
            }
        });


        return vista;

    }

    //LLENADO SEGUNDO RECYCLER
    public void ItemClicked (CategoriaPictograma categoriaPictograma){
        System.out.println("en el fragment"+categoriaPictograma.getCat_pictograma_id());
        System.out.println("en el fragment"+categoriaPictograma.getCat_pictograma_nombre());
        ModelPicto.getAllPictogramaByCategoria(categoriaPictograma.getCat_pictograma_id()).observe(getActivity(), new Observer<List<Pictograma>>() {
            @Override
            public void onChanged(List<Pictograma> pictogramas) {
                adapterPicto.setPictograma(pictogramas);
            }
        });
    }

}
