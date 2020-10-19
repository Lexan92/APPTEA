package com.example.apptea.ui.preguntasFrecuentes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.Faq;
import roomsqlite.entidades.RespuestaFaq;

public class RespuestaFaqFragment extends Fragment implements RespuestaFaqAdapter.OnRespuestaListener{

    RecyclerView recyclerView;
    private RespuestaFaqAdapter adapter = null;
    private RespuestaFaqViewModel respuestaFaqViewModel;
    private Faq faq = null;

    public RespuestaFaqFragment() {
        //contructor vacio
    }

    public static RespuestaFaqFragment newInstance(String param1, String param2){
        RespuestaFaqFragment fragment = new RespuestaFaqFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_respuesta_faq, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_respuesta_faq);
        adapter = new RespuestaFaqAdapter(getActivity(), RespuestaFaqFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        respuestaFaqViewModel = new ViewModelProvider(getActivity()).get(RespuestaFaqViewModel.class);


        Bundle objetoRespuesta = getArguments();

        if(objetoRespuesta != null){
            faq = (Faq)objetoRespuesta.getSerializable("elementos");
            respuestaFaqViewModel.getRespuestasFaqAll(faq.getFaq_id()).observe(getActivity(), new Observer<List<RespuestaFaq>>() {
                @Override
                public void onChanged(List<RespuestaFaq> respuestaFaqs) {
                    adapter.setRespuesta(respuestaFaqs);
                }
            });
        }

        //Setteando Toolbar para categorias
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(faq.getFaq_info());

    }



    @Override
    public void onRespuestaClick(RespuestaFaq posicion) {

    }
}
