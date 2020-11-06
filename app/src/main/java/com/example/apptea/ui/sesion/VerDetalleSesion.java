package com.example.apptea.ui.sesion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.DetalleSesion;
import roomsqlite.entidades.Sesion;

public class VerDetalleSesion extends Fragment implements DetalleSesionAdapter.OnDetalleListener  {

    RecyclerView recyclerView;
    private DetalleSesionAdapter adapter = null;
    private DetalleSesionViewModel detalleSesionViewModel;
    Sesion sesion = null;



    public VerDetalleSesion() {
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
        return inflater.inflate(R.layout.fragment_ver_detalle_sesion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_detalle_sesion);
        adapter = new DetalleSesionAdapter(getActivity(),VerDetalleSesion.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
        detalleSesionViewModel = new ViewModelProvider(getActivity()).get(DetalleSesionViewModel.class);
        Bundle  objetoSesion = getArguments();
        if (objetoSesion!=null){
            sesion = (Sesion) objetoSesion.getSerializable("sesion");
            detalleSesionViewModel.obtenerDetalleSesiones(sesion.getSesion_id()).observe(getActivity(), new Observer<List<DetalleSesion>>() {
                @Override
                public void onChanged(List<DetalleSesion> detalleSesions) {
                    adapter.setDetalles(detalleSesions);
                }
            });
        }

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Opciones Selecionadas");
    }

    @Override
    public void onDetalleClick(DetalleSesion detalleSesion) {

    }



}