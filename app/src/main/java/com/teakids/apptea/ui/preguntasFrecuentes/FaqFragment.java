package com.teakids.apptea.ui.preguntasFrecuentes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teakids.apptea.R;

import java.util.List;

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
                RespuestaFaqFragment respuestaFaqFragment = new RespuestaFaqFragment();
                Bundle bundleEnvio = new Bundle();

                bundleEnvio.putSerializable("elementos",faqViewModel.getFaqAll().getValue().get(recyclerView.getChildAdapterPosition(v)));
                respuestaFaqFragment.setArguments(bundleEnvio);
                Navigation.findNavController(v).navigate(R.id.fragment_respuesta_faq,bundleEnvio);
            }
        });


        return vista;
    }

    @Override
    public void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }
}
