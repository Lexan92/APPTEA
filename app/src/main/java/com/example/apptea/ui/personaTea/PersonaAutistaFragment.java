package com.example.apptea.ui.personaTea;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptea.R;
import com.example.apptea.ui.personaTea.dummy.DummyContent;

import java.util.List;

import roomsqlite.entidades.PersonaTea;

/**
 * A fragment representing a list of Items.
 */
public class PersonaAutistaFragment extends Fragment {

    private PersonaTeaViewModel personaTeaViewModel;
    private PersonaTea personaTea;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonaAutistaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PersonaAutistaFragment newInstance(int columnCount) {
        PersonaAutistaFragment fragment = new PersonaAutistaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        final PersonaTeaAdapter adapter = new PersonaTeaAdapter();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
                personaTeaViewModel = new ViewModelProvider(getActivity()).get(PersonaTeaViewModel.class);
                personaTeaViewModel.getPersonaTeaAll().observe(getActivity(), new Observer<List<PersonaTea>>() {
                    @Override
                    public void onChanged(List<PersonaTea> personaTeas) {
                        adapter.setPersonas(personaTeas);
                    }
                });
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPersonaAutistaRecyclerViewAdapter(DummyContent.ITEMS));
        }







        return view;
    }



}