package com.example.apptea.ui.preguntasFrecuentes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaAdapter;
import com.example.apptea.ui.categoriahabilidadcotidiana.CategoriaHabCotidianaViewModel;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Faq;

public class FaqFragment extends Fragment {

    RecyclerView recyclerView;
    private FaqViewModel faqViewModel;
    private FaqAdapter faqAdapter = null;

    public FaqFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_faq, container, false);


        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview_faq);
        this.faqAdapter = new FaqAdapter(getActivity());
        recyclerView.setAdapter(faqAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        faqViewModel = new ViewModelProvider(getActivity()).get(FaqViewModel.class);
        faqViewModel.getFaqAll().observe(getActivity(), new Observer<List<Faq>>() {
            @Override
            public void onChanged(List<Faq> faqList) {
                faqAdapter.setFaq(faqList);
            }
        });

        faqAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Clic para pasos",Toast.LENGTH_LONG).show();
            }
        });


        return vista;
    }
}
