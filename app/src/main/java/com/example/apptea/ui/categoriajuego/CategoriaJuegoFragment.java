

package com.example.apptea.ui.categoriajuego;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.Detalle_Juego;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.repositorios.CategoriaJuegoRepository;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class CategoriaJuegoFragment extends Fragment implements ICategoriaJuego {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private CategoriaJuegoRepository categoriaJuegoRepository;
    private LiveData<List<CategoriaJuego>> categoriasJuegos;
    RecyclerView recyclerView;
    private CategoriaViewModel categoriaViewModel;
    private String message;
    public static final String EXTRA_MESSAGE = "com.example.apptea.MESSAGE";
    public static final  int REQUEST_CODE = 1;


    public CategoriaJuegoFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_categoria_juego, container, false);

        recyclerView =  vista.findViewById(R.id.lista_categoria_juego);
        final CategoriaJuegoAdapter adapter = new CategoriaJuegoAdapter(getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);


        categoriaViewModel = new ViewModelProvider(getActivity()).get(CategoriaViewModel.class);
        categoriaViewModel.getAllCategoriasJuegos().observe(getActivity(), new Observer<List<CategoriaJuego>>() {
            @Override
            public void onChanged(List<CategoriaJuego> categoriaJuegos) {
                adapter.setCategoriasJuegos(categoriaJuegos);
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            message = categoriaViewModel.getAllCategoriasJuegos().getValue().get(recyclerView.getChildAdapterPosition(v)).getCategoriaJuegoNombre();


                Detalle_Juego detalle_juego =new Detalle_Juego();
                //Bundle
                Bundle  bundleEnvio = new Bundle();
                bundleEnvio.putSerializable("objeto",categoriaViewModel.getAllCategoriasJuegos().getValue().get(recyclerView.getChildAdapterPosition(v)));
                detalle_juego.setArguments(bundleEnvio);
                //fragment destino
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,detalle_juego);
                transaction.addToBackStack(null).commit();


              /*  Intent intent = new Intent(getActivity(), DetalleCategoriaJuego.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/

              /*  Toast.makeText(getActivity(),"Click en: " + (categoriaViewModel.getAllCategoriasJuegos()
                        .getValue().get(recyclerView.getChildAdapterPosition(v)).getCategoriaJuegoNombre()), Toast.LENGTH_SHORT ).show();
                */

            }
        });

        return vista;
    }


    @Override
    public void setNameToolbar(String message) {

    }
}
