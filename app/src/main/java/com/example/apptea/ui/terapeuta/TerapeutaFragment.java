package com.example.apptea.ui.terapeuta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Terapeuta;
import roomsqlite.repositorios.TerapeutaRepository;

import static android.app.Activity.RESULT_OK;

public class TerapeutaFragment extends Fragment {
    private TerapeutaViewModel terapeutaViewModel;
    private TerapeutaRepository terapeutaRepository;
    private Terapeuta terapeuta;
    PersonaTea personaTea = null;
    private LiveData<List<Terapeuta>> terapeutas;
    RecyclerView recyclerView;
    public static final int TERAPEUTA_REQUEST_CODE = 1;
    public static final int TERAPEUTA_UPDATE_REQUEST_CODE = 2;
    public static final String EXTRA_ID_PERSONA_UPDATE = "com.example.apptea.ui.personaTea.EXTRA_ID_PERSONA_UPDATE";

    public TerapeutaFragment(){}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_terapeuta, container, false);

        recyclerView =  vista.findViewById(R.id.lista_terapeutas);


        final TerapeutaAdapter adapter = new TerapeutaAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
        terapeutaViewModel = new ViewModelProvider(getActivity()).get(TerapeutaViewModel.class);
        Bundle objetoPersona = getArguments();

        if(objetoPersona!=null){
            personaTea = (PersonaTea) objetoPersona.getSerializable("persona");
            terapeutaViewModel.getAllTerapeutaByPersona(personaTea.getPersona_id()).observe(getActivity(), new Observer<List<Terapeuta>>() {
                @Override
                public void onChanged(List<Terapeuta> terapeutas) {
                    adapter.setTerapeutas(terapeutas);
                }
            });

        }



        // AGREGAR TERAPEUTAS
        FloatingActionButton fab = vista.findViewById(R.id.fabterapeuta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevoTerapeuta.class);
                intent.putExtra(NuevoTerapeuta.EXTRA_EDIT,1);
                intent.putExtra("llavePersona", personaTea.getPersona_id());
                startActivityForResult(intent, TERAPEUTA_REQUEST_CODE);
            }
        });


        // ACTUALIZAR Y ELIMINAR

        adapter.setButtonClicked(new TerapeutaAdapter.ButtonClicked() {
            @Override
            public void deleteClickedTerapeuta(Terapeuta terapeuta) {
                String alerta=getResources().getString(R.string.alerta);
                String estaSeguro=getResources().getString(R.string.estaSeguro);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(alerta);
                builder.setMessage(estaSeguro +"\n"+terapeuta.getTerapeuta_nombre()+"?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton(getResources().getString(R.string.eliminarTera), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("el terapeuta es " + terapeuta.getTerapeuta_nombre());

                        terapeutaViewModel.delete(terapeuta);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.cancelarTera), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deleteDialog = builder.create();
                deleteDialog.show();
            }

            @Override
            public void updateClickedTerapeuta(Terapeuta terapeuta) {
                System.out.println("en el fragment"+terapeuta.getTerapeuta_id());
                Intent intentUpdate = new Intent(getActivity(),NuevoTerapeuta.class);

                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_EDIT,2);
                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_ID_TERAPEUTA_UPDATE, terapeuta.getTerapeuta_id());
                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_ID_PERSONA_UPDATE, terapeuta.getPersona_id());
                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_NOMBRE_TERAPEUTA_UPDATE, terapeuta.getTerapeuta_nombre());
                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_APELLIDO_TERAPEUTA_UPDATE, terapeuta.getTerapeuta_apellido());
                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_CORREO_TERAPEUTA_UPDATE, terapeuta.getTerapeuta_correo());
                intentUpdate.putExtra(NuevoTerapeuta.EXTRA_TELEFONO_TERAPEUTA_UPDATE, terapeuta.getTerapeuta_telefono());

                startActivityForResult(intentUpdate,TERAPEUTA_UPDATE_REQUEST_CODE);
            }
        });




        return vista;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TERAPEUTA_REQUEST_CODE && resultCode == RESULT_OK) {
            terapeuta = (Terapeuta) data.getSerializableExtra(NuevoTerapeuta.EXTRA_TERAPEUTA);
            terapeutaViewModel.insert(terapeuta);
        } else
        if (requestCode == TERAPEUTA_UPDATE_REQUEST_CODE && resultCode == RESULT_OK){
            terapeuta = (Terapeuta) data.getSerializableExtra(NuevoTerapeuta.EXTRA_TERAPEUTA_UPDATE);
            terapeutaViewModel.update(terapeuta);
        } else {
            //Toast.makeText(getActivity(),"esta vacio",Toast.LENGTH_LONG).show();
        }
    }

}
