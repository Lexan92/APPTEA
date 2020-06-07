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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.categoriahabilidadcotidiana.NuevaCategoriaDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.repositorios.PersonaTeaRepository;

import static android.app.Activity.RESULT_OK;

public class PersonaTeaFragment extends Fragment{
    private PersonaTeaViewModel personaTeaViewModel;
    private PersonaTeaRepository personaTeaRepository;
    private  PersonaTea personaTea;
    private LiveData<List<PersonaTea>> personastea;
    RecyclerView recyclerView;
    public static final int PERSONAS_REQUEST_CODE = 1;
    private PersonaTeaAdapter person;



    //contructor vacio
    public PersonaTeaFragment(){}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_persona_tea, container, false);

        recyclerView =  vista.findViewById(R.id.lista_personasTea);
        final TextView textpersona = vista.findViewById(R.id.text_persona);

        final PersonaTeaAdapter adapter = new PersonaTeaAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);



        personaTeaViewModel = new ViewModelProvider(getActivity()).get(PersonaTeaViewModel.class);

        textpersona.setText(" Listado de niños y niñas");
        personaTeaViewModel.getPersonaTeaAll().observe(getActivity(), new Observer<List<PersonaTea>>() {
            @Override
            public void onChanged(List<PersonaTea> personaTeas) {
                adapter.setPersonas(personaTeas);
            }
        });
        //}

        // AGREGAR PERSONAS
        FloatingActionButton fab = vista.findViewById(R.id.fabpersona);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getActivity(), NuevaPersonaTea.class);
                startActivityForResult(intent, PERSONAS_REQUEST_CODE);
            }
        });

       // System.out.println(recyclerView.findViewHolderForLayoutPosition(0).getItemId());

        adapter.setButtonClicked(new PersonaTeaAdapter.ButtonClicked() {
            @Override
            public void updateClickedPersona(PersonaTea personaTea) {

            }

            @Override
            public void deleteClickedPersona(PersonaTea personaTea) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alerta");
                builder.setMessage("¿esta seguro de eliminar a \n"+personaTea.getPersona_nombre()+"?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("La persona TEA es " + personaTea.getPersona_nombre());

                        personaTeaViewModel.delete(personaTea);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deleteDialog = builder.create();
                deleteDialog.show();
            }
        });

        return vista;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERSONAS_REQUEST_CODE && resultCode == RESULT_OK) {
            personaTea = (PersonaTea) data.getSerializableExtra(NuevaPersonaTea.EXTRA_PERSONA);
            personaTeaViewModel.insert(personaTea);
        } else {
            Toast.makeText(getActivity(),"esta vacio",Toast.LENGTH_LONG).show();
        }
    }

}
