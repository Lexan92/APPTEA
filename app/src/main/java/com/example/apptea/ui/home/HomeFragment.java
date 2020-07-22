package com.example.apptea.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.apptea.R;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {
    MaterialCardView frases, vocabulario, habilidades, juegos;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        frases = vista.findViewById(R.id.card_frases);
        vocabulario = vista.findViewById(R.id.card_vocabulario);
        habilidades = vista.findViewById(R.id.card_habilidades);
        juegos = vista.findViewById(R.id.card_juegos);


        frases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_frasesFragment);

            }
        });


        vocabulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean banderaToolbar = true;
                Bundle bundleBanderaToolbar = new Bundle();
                bundleBanderaToolbar.putBoolean("bandera", banderaToolbar);
                Navigation.findNavController(v).navigate(R.id.nav_gestion_pictograma, bundleBanderaToolbar);
            }
        });

        habilidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Habilidades Cotidianas pronto estará disponible",
                        Toast.LENGTH_LONG).show();
            }
        });

        juegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean bandera = true;
                Bundle bundle = new Bundle();
                bundle.putBoolean("bandera",bandera);
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_gestion_juego,bundle);
            }
        });

        return vista;
    }


}
