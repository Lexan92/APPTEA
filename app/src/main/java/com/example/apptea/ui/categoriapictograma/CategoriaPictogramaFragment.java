
package com.example.apptea.ui.categoriapictograma;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;


import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaAdapter;
import com.example.apptea.ui.pictograma.Detalle_Pictograma;
import com.example.apptea.utilidades.TTSManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.repositorios.CategoriaPictogramaRepository;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaPictogramaFragment extends Fragment {


    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private CategoriaPictogramaRepository categoriaPictogramaRepository;
    private LiveData<List<CategoriaPictograma>> categoriaPictogramaAll;

    RecyclerView recyclerView;

    private CategoriaPictogramaViewModel categoriaPictogramaViewModel;
    private CategoriaPictogramaAdapter adapter;
    CategoriaPictograma categoriaPictograma;
    TTSManager ttsManager =null;
    boolean bandera = false;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;


    public CategoriaPictogramaFragment() {
        //constructor vacio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_pictograma, container, false);
        //App bar de busqueda



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        ttsManager = new TTSManager();
        ttsManager.init(getActivity());
        recyclerView =  view.findViewById(R.id.lista_categoria_pictograma);
        adapter = new CategoriaPictogramaAdapter(getActivity());


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);


        categoriaPictogramaViewModel = new ViewModelProvider(getActivity()).get(CategoriaPictogramaViewModel.class);
        categoriaPictogramaViewModel.getAllCategoriaPictograma().observe(getActivity(), new Observer<List<CategoriaPictograma>>() {
            @Override
            public void onChanged(List<CategoriaPictograma> categoriaPictogramas) {
                adapter.setCategoria(categoriaPictogramas);
            }
        });

        //OBTENIENDO SI EL LLAMADO ES DE VOCABULARIO O DE GESTION
        Bundle objetoBundle = getArguments();

        // NUEVA CATEGORIA
        FloatingActionButton fab1 = view.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CategoriaNueva.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        // ONCLICK ITEM , ACTUALIZAR , ELIMINAR
        adapter.setButtonClickedCatPicto(new CategoriaPictogramaAdapter.ButtonClickedCatPicto() {
            @Override
            public void deleteClickedCatPicto(CategoriaPictograma categoriaPictograma) {

            }

            @Override
            public void updateClickedCatPicto(CategoriaPictograma categoriaPictograma) {

            }

            @Override
            public void itemClickedCatPicto(CategoriaPictograma categoriaPictograma, View v) {
                Detalle_Pictograma detalle_pictograma = new Detalle_Pictograma(); //Instancia de fragment al cual se dirigira
                Bundle bundleEnvio = new Bundle(); //objeto Bundle que encapsula el objeto de tipo CategoriaPictograma
                bundleEnvio.putBoolean("bandera", bandera);
                bundleEnvio.putSerializable("elementos",categoriaPictograma);
                detalle_pictograma.setArguments(bundleEnvio);
                System.out.println("en el fragment"+ categoriaPictograma.getCat_pictograma_nombre());
                ttsManager.initQueue(categoriaPictograma.getCat_pictograma_nombre());
                Navigation.findNavController(v).navigate(R.id.detalle_Pictograma,bundleEnvio); //Se define navegacion a siguiente fragment, se manda de parametros ID de fragment y objeto bundle
            }
        });


      /*  adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Instancia de fragment al cual se dirigira
                Detalle_Pictograma detalle_pictograma =new Detalle_Pictograma();
                //objeto Bundle que encapsula el objeto de tipo CategoriaPictograma
                Bundle  bundleEnvio = new Bundle();
                bundleEnvio.putSerializable("elementos",categoriaPictogramaViewModel.getAllCategoriaPictograma().getValue().get(recyclerView.getChildAdapterPosition(v)));
                detalle_pictograma.setArguments(bundleEnvio);

                //Se define navegacion a siguiente fragment, se manda de parametros ID de fragment y objeto bundle
                Navigation.findNavController(v).navigate(R.id.detalle_Pictograma,bundleEnvio);

            }
        });*/

        //Comprobacion para pintar el nombre del toolbar proveniente del menu principal y quitar el FAB
        if (objetoBundle!=null){
            bandera =  objetoBundle.getBoolean("bandera");

            if (bandera == true){
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                //toolbar se setea con VOCABULARIO
                toolbar.setTitle("Vocabulario");
                // el FAB se hace invisible
                fab1.setVisibility(View.INVISIBLE);
                adapter.isVocabulary=true;
            }
        }




    }

    /////////
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main2, menu);
        MenuItem searchItem= menu.findItem(R.id.app_bar_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    // Log.i("onQueryTextChange", newText);


                    adapter.getFilter().filter(newText);
                    // Log.i("valor adapter:", String.valueOf(adapter));

                    return false;


                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Log.i("onQueryTextSubmit", query);
                    adapter.getFilter().filter(query);
                    // Log.i("valor query:", String.valueOf(query));

                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_search) {
            return true;
        }

        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
/////////


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CategoriaPictograma categoria = (CategoriaPictograma) data.getSerializableExtra(CategoriaNueva.EXTRA_REPLY);
            categoriaPictogramaViewModel.insert(categoria);
        } else {
            Toast.makeText(getActivity(), R.string.vacio_cat_hab_cot,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecycle", "OnResume Categoria");
    }

    @Override
    public void onPause() {
        super.onPause();

       /* if(adapter!=null && recyclerView!=null){
            recyclerView.setAdapter(null);
            adapter.setOnClickListener(null);
            adapter = null;
            Log.d("lifecycle","OnPause Categoria");
        }
        categoriaPictogramaViewModel=null;
        categoriaPictogramaAll=null;
        categoriaPictogramaRepository=null;*/
        Runtime.getRuntime().gc();
    }

    @Override
    public void onStop() {
        super.onStop();

       /* if(adapter!=null && recyclerView!=null){
            recyclerView.setAdapter(null);
            adapter.setOnClickListener(null);
            Log.d("lifecycle","onStop Categoria");
            adapter=null;
        }
        categoriaPictogramaViewModel=null;
        categoriaPictogramaAll=null;
        categoriaPictogramaRepository=null;*/
        Runtime.getRuntime().gc();
    }
}