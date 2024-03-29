/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.ui.personaTea;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.teakids.apptea.ui.sesion.VerSesion;
import com.teakids.apptea.ui.terapeuta.TerapeutaFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.PersonaTea;

import static android.app.Activity.RESULT_OK;

public class PersonaTeaFragment extends Fragment {
    private PersonaTeaViewModel personaTeaViewModel;
    private PersonaTea personaTea;
    RecyclerView recyclerView;
    public static final int PERSONAS_REQUEST_CODE = 1;
    public static final int PERSONAS_UPDATE_REQUEST_CODE = 2;
    public static final int PERSONAS_TERA_REQUEST_CODE = 3;
   // private PersonaTeaAdapter person;



    //contructor vacio
    public PersonaTeaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_persona_tea, container, false);

        recyclerView = vista.findViewById(R.id.lista_personasTea);


        final PersonaTeaAdapter adapter = new PersonaTeaAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(adapter);


        personaTeaViewModel = new ViewModelProvider(getActivity()).get(PersonaTeaViewModel.class);

        personaTeaViewModel.getPersonaTeaAll().observe(getActivity(), new Observer<List<PersonaTea>>() {
            @Override
            public void onChanged(List<PersonaTea> personaTeas) {
                adapter.setPersonas(personaTeas);
            }
        });


        // AGREGAR PERSONAS
        FloatingActionButton fab = vista.findViewById(R.id.fabpersona);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevaPersonaTea.class);
                intent.putExtra(NuevaPersonaTea.EXTRA_EDIT, 1);
                startActivityForResult(intent, PERSONAS_REQUEST_CODE);
            }
        });


        // ACTUALIZAR Y ELIMINAR
        adapter.setButtonClicked(new PersonaTeaAdapter.ButtonClicked() {
            @Override
            public void updateClickedPersona(PersonaTea personaTea) {
                System.out.println("en el fragment" + personaTea.getPersona_id());
                Intent intentUpdate = new Intent(getActivity(), NuevaPersonaTea.class);

                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_EDIT, 2);
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_ID_PERSONA_UPDATE, personaTea.getPersona_id());
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_ID_USUARIO_UPDATE, personaTea.getUsuario_id());
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_NOMBRE_PERSONA_UPDATE, personaTea.getPersona_nombre());
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_APELLIDO_PERSONA_UPDATE, personaTea.getPersona_apellido());
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_FECHA_PERSONA_UPDATE, personaTea.getPersona_fecha_nac().toString());
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_SEXO_PERSONA_UPDATE, personaTea.getPersona_sexo());
                intentUpdate.putExtra(NuevaPersonaTea.EXTRA_FOTO_PERSONA_UPDATE, personaTea.getPersona_foto());

                startActivityForResult(intentUpdate, PERSONAS_UPDATE_REQUEST_CODE);
            }

            @Override
            public void terapeutaClicked(PersonaTea personaTea) {
                TerapeutaFragment  terapeutaFragment = new TerapeutaFragment();
                Bundle bundleEnvio= new Bundle();
                bundleEnvio.putSerializable("persona", personaTea);
                terapeutaFragment.setArguments(bundleEnvio);
                Navigation.findNavController(vista).navigate(R.id.terapeutaFragment,bundleEnvio);
            }

            @Override
            public void personaTeaClicked(PersonaTea personaTea) {
                VerSesion verSesion = new VerSesion();
                Bundle bundle = new Bundle();
                bundle.putSerializable("persona",personaTea);
                verSesion.setArguments(bundle);
                Navigation.findNavController(vista).navigate(R.id.verSesion,bundle);
            }

            @Override
            public void deleteClickedPersona(PersonaTea personaTea) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.alerta));
                builder.setMessage(getResources().getString(R.string.estaSeguro)+"\n" + personaTea.getPersona_nombre() + "?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton(getResources().getString(R.string.eliminar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("La persona TEA es " + personaTea.getPersona_nombre());

                        personaTeaViewModel.delete(personaTea);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
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
        } else if (requestCode == PERSONAS_UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            personaTea = (PersonaTea) data.getSerializableExtra(NuevaPersonaTea.EXTRA_PERSONA_UPDATE);
            personaTeaViewModel.update(personaTea);
        }
    }

}
