

package com.example.apptea.ui.categoriajuego;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;
import com.example.apptea.ui.DetalleCategoriaJuego.DetalleJuegoPaciente;
import com.example.apptea.ui.DetalleCategoriaJuego.Detalle_Juego;
import com.example.apptea.ui.juegoMemoria.DetalleJuegoPaciente2;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.UtilidadFecha;

import java.util.List;

import roomsqlite.dao.DetalleSesionDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.DetalleSesion;


public class CategoriaJuegoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    RecyclerView recyclerView;
    private CategoriaViewModel categoriaViewModel;
    boolean bandera = false;
    public LottieAnimationView juegoImg;


    @Override
    public void onStart() {
        super.onStart();
        AdministarSesion administarSesion = new AdministarSesion(getContext());
        //registro de sesion
        if (administarSesion.obtenerIDSesion() > 0) {
            DetalleSesion detalleSesion = new DetalleSesion();
            detalleSesion.setSesion_id(administarSesion.obtenerIDSesion());
            detalleSesion.setHora_inicio(UtilidadFecha.obtenerFechaHoraActual());
            detalleSesion.setNombre_opcion("OPCION MENU: Juegos Interactivos");

            DetalleSesionDao detalleSesionDao = appDatabase.getDatabase(getContext()).detalleSesionDao();

            detalleSesionDao.insertarDetalleSesion(detalleSesion);
        }
    }

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
        recyclerView = view.findViewById(R.id.lista_categoria_juego);
        AdministarSesion administarSesion = new AdministarSesion(getContext());
        final CategoriaJuegoAdapter adapter = new CategoriaJuegoAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
                //objeto Bundle que encapsula el objeto de tipo CategoriaJuego
                CategoriaJuego categoriaJuego = categoriaViewModel.getAllCategoriasJuegos().getValue().get(recyclerView.getChildAdapterPosition(v));
                Bundle bundleEnvio = new Bundle();
                bundleEnvio.putInt("objeto", categoriaJuego.getCategoriaJuegoId());


                Bundle bundle = getArguments();
                if (bundle != null) {
                    bandera = bundle.getBoolean("bandera");
                }
                bundleEnvio.putBoolean("bandera", bandera);

                //direcciona a fragmente proveniente del menu principal
                if (bandera) {
                    AdministarSesion administarSesion = new AdministarSesion(getContext());
                    if (administarSesion.obtenerIDSesion() > 0) {
                        DetalleSesion detalleSesion = new DetalleSesion();
                        detalleSesion.setSesion_id(administarSesion.obtenerIDSesion());
                        detalleSesion.setHora_inicio(UtilidadFecha.obtenerFechaHoraActual());
                        detalleSesion.setNombre_opcion("CATEGORIA JUEGO: " + categoriaJuego.getCategoriaJuegoNombre());
                        DetalleSesionDao detalleSesionDao = appDatabase.getDatabase(getContext()).detalleSesionDao();
                        detalleSesionDao.insertarDetalleSesion(detalleSesion);
                    }

                    if (categoriaJuego.getCategoriaJuegoId()==1){
                        //fragment para paciente categoria 1
                        DetalleJuegoPaciente detalle_juego = new DetalleJuegoPaciente();
                        detalle_juego.setArguments(bundleEnvio);
                        Navigation.findNavController(v).navigate(R.id.detalleJuegoPaciente, bundleEnvio);
                    }else if(categoriaJuego.getCategoriaJuegoId()==2){
                        //fragment para paciente categoria 2
                        DetalleJuegoPaciente2 detalle_juego = new DetalleJuegoPaciente2();
                        detalle_juego.setArguments(bundleEnvio);
                        Navigation.findNavController(v).navigate(R.id.detalleJuegoPaciente,bundleEnvio);
                    }
                } else {

                    if (categoriaJuego.getCategoriaJuegoId()==1){
                        //Instancia de fragment al cual se dirigira
                        //Se define navegacion a siguiente fragment, se manda de parametros ID de fragment y objeto bundle
                        Detalle_Juego detalle_juego = new Detalle_Juego();
                        detalle_juego.setArguments(bundleEnvio);
                        Navigation.findNavController(v).navigate(R.id.detalle_Juego, bundleEnvio);
                    }else  if(categoriaJuego.getCategoriaJuegoId()==2){
                        //fragment para paciente categoria 2
                        Detalle_Juego detalle_juego = new Detalle_Juego();
                        detalle_juego.setArguments(bundleEnvio);
                        Navigation.findNavController(v).navigate(R.id.detalle_Juego,bundleEnvio);
                    }


                }

            }

        });
    }
}

