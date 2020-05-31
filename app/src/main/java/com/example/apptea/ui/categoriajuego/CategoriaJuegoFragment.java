

package com.example.apptea.ui.categoriajuego;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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

        return inflater.inflate(R.layout.fragment_categoria_juego, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView =  view.findViewById(R.id.lista_categoria_juego);
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

                //Instancia de fragment al cual se dirigira
                Detalle_Juego detalle_juego =new Detalle_Juego();
                //objeto Bundle que encapsula el objeto de tipo CategoriaJuego
                Bundle  bundleEnvio = new Bundle();
                bundleEnvio.putSerializable("objeto",categoriaViewModel.getAllCategoriasJuegos().getValue().get(recyclerView.getChildAdapterPosition(v)));
                detalle_juego.setArguments(bundleEnvio);

                //Se define navegacion a siguiente fragment, se manda de parametros ID de fragment y objeto bundle
                Navigation.findNavController(v).navigate(R.id.detalle_Juego,bundleEnvio);

            }
        });

    }

    @Override
    public void setNameToolbar(String message) {

    }
}
