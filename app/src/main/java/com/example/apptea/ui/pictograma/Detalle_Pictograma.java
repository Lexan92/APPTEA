
/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.pictograma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;
import com.example.apptea.ui.juego.OpcionViewModel;
import com.example.apptea.ui.personaTea.NuevaPersonaTea;
import com.example.apptea.utilidades.TTSManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Pictograma;
import roomsqlite.repositorios.PictogramaRepository;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detalle_Pictograma#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detalle_Pictograma extends Fragment implements PictogramaAdapter.OnPictogramaListener {


    private static final int ACTIVITY_REQUEST_CODE = 10;
    public static final int PICTOGRAMA_UPDATE_REQUEST_CODE = 2;


    private Pictograma pictograma;
    private PictogramaViewModel pictogramaViewModel;
    private OpcionViewModel opcionViewModel;
    RecyclerView recyclerView;
    CategoriaPictograma categoriaPictograma = null;
    PictogramaAdapter adapter;
    boolean bandera =false;
    TTSManager ttsManager=null;


    public Detalle_Pictograma() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detalle_Pictograma.
     */
    // TODO: Rename and change types and number of parameters
    public static Detalle_Pictograma newInstance(String param1, String param2) {
        Detalle_Pictograma fragment = new Detalle_Pictograma();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle__pictograma, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lista_pictogramas);
        adapter = new PictogramaAdapter(getActivity(), Detalle_Pictograma.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        pictogramaViewModel = new ViewModelProvider(getActivity()).get(PictogramaViewModel.class);

        ttsManager= new TTSManager();
        ttsManager.init(getActivity());

        Bundle objetoCategoriaPictograma = getArguments();

        if (objetoCategoriaPictograma != null) {
            categoriaPictograma = (CategoriaPictograma) objetoCategoriaPictograma.getSerializable("elementos");
            pictogramaViewModel.getAllPictogramaByCategoria(categoriaPictograma.getCat_pictograma_id()).observe(getActivity(), new Observer<List<Pictograma>>() {
                @Override
                public void onChanged(List<Pictograma> pictogramas) {
                    adapter.setPictograma(pictogramas);
                }
            });
        }

        //Definiendo nombre para el toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categoria: " + categoriaPictograma.getCat_pictograma_nombre());


        //NUEVO PICTOGRAMA
        FloatingActionButton fab = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevoPictogramaDialog.class);
                intent.putExtra(NuevoPictogramaDialog.EXTRA_EDIT,1);
                //envio de ID de categoria
                intent.putExtra("llaveCategoria", categoriaPictograma.getCat_pictograma_id());
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });

        // ONCLICK ITEM, ACTUALIZAR, ELIMINAR
        adapter.setButtonClickedPictograma(new PictogramaAdapter.ButtonClickedPictograma() {
            @Override
            public void deleteClickedPictograma(Pictograma pictograma) {

               // System.out.println("id pictograma "+pictograma.getPictograma_id());
                int numHabPicto= pictogramaViewModel.numHabPicto(pictograma.getPictograma_id());
                int numpictoO= pictogramaViewModel.numJuegoPicto(pictograma.getPictograma_id());
               if (numpictoO>0){
                    //System.out.println("cantidad de veces que se usa el pictograma "+ numpictoO);
                    if(numHabPicto>0){
                        MensajeAlerta("Esta imagen no se puede eliminar \n  Imagen utilizada en: \n \n" +
                                numHabPicto + " habilidades cotidianas \n"+
                                numpictoO + " juegos interactivos");
                    }else{
                        MensajeAlerta("Esta imagen no se puede eliminar \n  Imagen utilizada en: \n \n" +
                                numpictoO + "  juegos interactivos");
                    }

               }else{
                   if(numHabPicto>0){
                       MensajeAlerta("Esta imagen no se puede eliminar \n  Imagen utilizada en: \n \n" +
                               numHabPicto+ " habilidades cotidianas ");
                   }else{
                       MensajeDelete( pictograma,"¿Esta seguro? \n Se eliminara la imagen");
                   }

               }

            }

            @Override
            public void updateClicledPictograma(Pictograma pictograma) {
                Intent intentUpdate = new Intent(getActivity(), NuevoPictogramaDialog.class);

                intentUpdate.putExtra(NuevoPictogramaDialog.EXTRA_EDIT,2);
                intentUpdate.putExtra(NuevoPictogramaDialog.EXTRA_ID_PICTOGRAMA_UPDATE, pictograma.getPictograma_id());
                intentUpdate.putExtra(NuevoPictogramaDialog.EXTRA_ID_CATEGORIA_UPDATE, pictograma.getCat_pictograma_id());
                intentUpdate.putExtra(NuevoPictogramaDialog.EXTRA_FOTO_PICTOGRAMA_UPDATE, pictograma.getPictograma_imagen());

                startActivityForResult(intentUpdate,PICTOGRAMA_UPDATE_REQUEST_CODE);
            }

            @Override
            public void itemClickedPictograma(Pictograma pictograma) {
                ttsManager.initQueue(pictograma.getPictograma_nombre());
            }
        });


        //COMPROBANDO SI LA PANTALLA ES DE VOCABULARIO
        if (objetoCategoriaPictograma !=null){
            bandera = objetoCategoriaPictograma.getBoolean("bandera");

            if(bandera==true){
                fab.setVisibility(View.INVISIBLE);
                adapter.isVocabulary=true;
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            pictograma = (Pictograma) data.getSerializableExtra(NuevoPictogramaDialog.EXTRA_PICTOGRAMA);
            pictogramaViewModel.insert(pictograma);
        } else
        if (requestCode == PICTOGRAMA_UPDATE_REQUEST_CODE && resultCode == RESULT_OK){
            pictograma = (Pictograma) data.getSerializableExtra(NuevoPictogramaDialog.EXTRA_PICTOGRAMA_UPDATE);
            pictogramaViewModel.update(pictograma);
        } else {
            Toast.makeText(getActivity(),"esta vacio",Toast.LENGTH_LONG).show();
        }
    }

    //ALERTA DELETE
    public void MensajeDelete(Pictograma pictograma, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alerta");
        builder.setMessage(mensaje);
        builder.setIcon(android.R.drawable.ic_delete);

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //DELETE CASCADE
                pictogramaViewModel.deletePictograma(pictograma);
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
    //ALERTA
    public void MensajeAlerta(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alerta");
        builder.setMessage(mensaje);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Cerrar",null);
        builder.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
       /* if (adapter != null && recyclerView != null) {
            recyclerView.setAdapter(null);
            adapter = null;
        }*/
        Runtime.getRuntime().gc();

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

    @Override
    public void onPictogramaClick(Pictograma posicion) {

    }
}