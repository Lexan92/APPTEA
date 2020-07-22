/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.categoriahabilidadcotidiana;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apptea.R;
import com.example.apptea.ui.habilidadCotidiana.HabilidadCotidianaFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.repositorios.CategoriaHabCotidianaRepository;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaHabCotidianaFragment extends Fragment  {


    private CategoriaHabCotidianaRepository categoriaHabCotidianaRepository;
    private LiveData<List<CategoriaHabCotidiana>> categoriaHabCotidianaAll;
    RecyclerView recyclerView;
    private CategoriaHabCotidianaViewModel categoriaHabCotidianaViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int CAT_UPDATE_REQUEST_CODE = 2;
    private CategoriaHabCotidianaAdapter adapter=null;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

    public CategoriaHabCotidianaFragment() {
        //requiere un constructor vacio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_habilidad, container, false);


        //App bar de busqueda
        setHasOptionsMenu(true);



        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_cat_hab_cotidiana);
        this.adapter = new CategoriaHabCotidianaAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        categoriaHabCotidianaViewModel = new ViewModelProvider(getActivity()).get(CategoriaHabCotidianaViewModel.class);
        categoriaHabCotidianaViewModel.getCategoriaHabCotidianaAll().observe(getActivity(), new Observer<List<CategoriaHabCotidiana>>() {
            @Override
            public void onChanged(List<CategoriaHabCotidiana> categoriaHabCotidianaList) {
                // Update the cached copy of the words in the adapter.
                adapter.setCategoria(categoriaHabCotidianaList);

            }
        });
//Boton de + para agregar una nueva categoria
        FloatingActionButton fab = vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevaCategoriaDialog.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        // METODOS PARA ACTUALIZAR Y ELIMINAR
        adapter.setButtonClicked(new CategoriaHabCotidianaAdapter.ButtonClicked() {
            @Override
            public void updateClickedCatHab(CategoriaHabCotidiana categoriaHabCotidiana) {
                System.out.println("en el fragment"+categoriaHabCotidiana.getCat_hab_cotidiana_id());
                Intent intentUpdate = new Intent(getActivity(),EditCategoriaHab.class);
                intentUpdate.putExtra(EditCategoriaHab.EXTRA_ID_CAT_UPDATE, categoriaHabCotidiana.getCat_hab_cotidiana_id());
                intentUpdate.putExtra(EditCategoriaHab.EXTRA_NOMBRE_CAT_UPDATE, categoriaHabCotidiana.getCat_hab_cotidiana_nombre());
                intentUpdate.putExtra(EditCategoriaHab.EXTRA_CAT_PREDETERMINADO_UPDATE, categoriaHabCotidiana.isCat_predeterminado());
                startActivityForResult(intentUpdate,CAT_UPDATE_REQUEST_CODE);
            }

            @Override
            public void deleteClickedCatHab(CategoriaHabCotidiana categoriaHabCotidiana) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alerta");
                builder.setMessage("¿Esta seguro de eliminar a la Categoria de:\n"+categoriaHabCotidiana.getCat_hab_cotidiana_nombre()+"?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("La Categoria de Habilidad Cotidiana" + categoriaHabCotidiana.getCat_hab_cotidiana_nombre());

                        categoriaHabCotidianaViewModel.delete(categoriaHabCotidiana);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deleteDialog = builder.create();
                deleteDialog.show();
            }
            });


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Instancia de fragment al cual se dirigira
                HabilidadCotidianaFragment detalle_habilidad_cotidiana = new HabilidadCotidianaFragment();
                //objeto Bundle que encapsula el objeto de tipo CategoriaPictograma
                Bundle  bundleEnvio = new Bundle();
                bundleEnvio.putSerializable("elementos",categoriaHabCotidianaViewModel.getCategoriaHabCotidianaAll().getValue().get(recyclerView.getChildAdapterPosition(v)));
                detalle_habilidad_cotidiana.setArguments(bundleEnvio);

                //Se define navegacion a siguiente fragment, se manda de parametros ID de fragment y objeto bundle
                Navigation.findNavController(v).navigate(R.id.detalle_habilidad_Cotidiana,bundleEnvio);

            }
        });


        return vista;
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

                   return true;


                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                   // Log.i("onQueryTextSubmit", query);
                  adapter.getFilter().filter(query);
                   // Log.i("valor query:", String.valueOf(query));

                    return true;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CategoriaHabCotidiana categoria = (CategoriaHabCotidiana) data.getSerializableExtra(NuevaCategoriaDialog.EXTRA_REPLY);
            categoriaHabCotidianaViewModel.insert(categoria);
        } else
        if (requestCode == CAT_UPDATE_REQUEST_CODE && resultCode == RESULT_OK){
           CategoriaHabCotidiana categoriaHabCotidiana = (CategoriaHabCotidiana) data.getSerializableExtra(EditCategoriaHab.EXTRA_CAT_HAB_UPDATE);
            categoriaHabCotidianaViewModel.update(categoriaHabCotidiana);
        } else {
            Toast.makeText(getActivity(), R.string.vacio_cat_hab_cot,
                    Toast.LENGTH_LONG).show();
        }
    }

}