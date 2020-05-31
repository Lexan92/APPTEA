/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.personaTea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.repositorios.PersonaTeaRepository;

public class PersonaTeaFragment extends Fragment {
    private PersonaTeaViewModel personaTeaViewModel;
    private PersonaTeaRepository personaTeaRepository;
    private LiveData<List<PersonaTea>> personastea;
    RecyclerView recyclerView;



    //contructor vacio
    public PersonaTeaFragment(){}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_persona_tea, container, false);

        recyclerView =  vista.findViewById(R.id.lista_personasTea);
        final TextView textpersona = vista.findViewById(R.id.text_persona);

        final PersonaTeaAdapter adapter = new PersonaTeaAdapter(getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);

        personaTeaViewModel = new ViewModelProvider(getActivity()).get(PersonaTeaViewModel.class);

        if (personaTeaViewModel==null){

            textpersona.setText(" No se han registrado personas en la lista");
        }
        else{
            textpersona.setText(" Gestion");
        personaTeaViewModel.getPersonaTeaAll().observe(getActivity(), new Observer<List<PersonaTea>>() {
            @Override
            public void onChanged(List<PersonaTea> personaTeas) {
                adapter.setPersonas(personaTeas);
            }
        });
        }

        return vista;
    }

}
