package com.example.apptea.ui.sesion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Sesion;


public class VerSesion extends Fragment implements SesionAdapter.OnSesionListener {

    RecyclerView recyclerView;
    private SesionAdapter adapter = null;
    private SesionViewModel sesionViewModel;
    PersonaTea personaTea = null;



    public VerSesion() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_sesion, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LottieAnimationView cajita = view.findViewById(R.id.cajita);
        TextView mensaje = view.findViewById(R.id.mensaje);
        recyclerView = view.findViewById(R.id.recyclerview_sesion);
        adapter = new SesionAdapter(getActivity(), VerSesion.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(adapter);
        sesionViewModel = new ViewModelProvider(getActivity()).get(SesionViewModel.class);
        Bundle objetoPersona = getArguments();



        if (objetoPersona != null) {
            personaTea = (PersonaTea) objetoPersona.getSerializable("persona");
            sesionViewModel.obtenerSesionesPorIDPersona(personaTea.getPersona_id()).observe(getActivity(), new Observer<List<Sesion>>() {
                @Override
                public void onChanged(List<Sesion> sesiones) {
                    if (sesiones.size() == 0 || sesiones.isEmpty()) {
                        cajita.setVisibility(View.VISIBLE);
                        mensaje.setVisibility(View.VISIBLE);
                        cajita.playAnimation();
                        recyclerView.setVisibility(View.GONE);
                    } else {

                        cajita.setVisibility(View.INVISIBLE);
                        mensaje.setVisibility(View.INVISIBLE);
                        cajita.cancelAnimation();
                        adapter.setSesiones(sesiones);
                    }
                }
            });


            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.opcionesSeleccionadas).concat(": ").concat(personaTea.getPersona_nombre()).concat(" ").concat(personaTea.getPersona_apellido()));

        }




        adapter.setButtonClicked(new SesionAdapter.ButtonCliked() {
            @Override
            public void comentarioCliked(Sesion sesion) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("sesion",sesion);
                Navigation.findNavController(getView()).navigate(R.id.action_verSesion_to_comentarioDialogo,bundle);
            }
        });
    }

    @Override
    public void onSesionClick(Sesion sesion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("sesion",sesion);
        VerDetalleSesion verDetalleSesion = new VerDetalleSesion();
        verDetalleSesion.setArguments(bundle);
        Navigation.findNavController(getView()).navigate(R.id.verDetalleSesion,bundle);

    }
}