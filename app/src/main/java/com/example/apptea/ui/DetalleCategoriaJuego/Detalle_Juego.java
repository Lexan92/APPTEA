/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.ui.juego.JuegoPrincipal;
import com.example.apptea.ui.juego.NuevoJuego;
import com.example.apptea.ui.juego.OpcionViewModel;
import com.example.apptea.ui.juego.PreguntaViewModel;
import com.example.apptea.ui.juego.VisorPregunta;
import com.example.apptea.ui.pictograma.Detalle_Pictograma;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialContainerTransform;

import java.util.List;

import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detalle_Juego} factory method to
 * create an instance of this fragment.
 */
public class Detalle_Juego extends Fragment implements JuegoAdapter.OnJuegoListener{

    private JuegoViewModel juegoViewModel;
    RecyclerView recyclerView;
    JuegoAdapter adapter;
    CategoriaJuego categoriaJuego=null;
    PreguntaViewModel preguntaViewModel;




    public Detalle_Juego() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_detalle__juego, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lista_juegos);
        adapter = new JuegoAdapter(getActivity(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        juegoViewModel = new ViewModelProvider(getActivity()).get(JuegoViewModel.class);
        preguntaViewModel= new ViewModelProvider(getActivity()).get(PreguntaViewModel.class);

        Bundle objetoCategoriaJuego=getArguments();

        if(objetoCategoriaJuego!= null){
            categoriaJuego = (CategoriaJuego) objetoCategoriaJuego.getSerializable("objeto");
            juegoViewModel.findJuegosByCategoriaId(categoriaJuego.getCategoriaJuegoId()).observe(getActivity(), new Observer<List<Juego>>() {
                @Override
                public void onChanged(List<Juego> juegos) {
                    adapter.setJuegos(juegos);
                }
            });


        }

        //Definiendo nombre para el toolbar
        Toolbar  toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(categoriaJuego.getCategoriaJuegoNombre());

        //Boton de + para agregar un nuevo juego
        FloatingActionButton fab = view.findViewById(R.id.fab_nuevo_juego);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NuevoJuego.class);
                //envia ID de categoria de juego
                intent.putExtra("categoriaJuego", categoriaJuego.getCategoriaJuegoId());
                startActivity(intent);

            }
        });


        //METODO PARA ELIMINAR UN JUEGO
        adapter.setButtonClicked(new JuegoAdapter.ButtonClicked() {
            @Override
            public void deleteClickedCatHab(Juego juego) {

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle("Alerta");
                builder.setMessage("¿Está seguro de eliminar el Juego :\n"+juego.getJuego_nombre()+"?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        juegoViewModel.delete(juego);
                        adapter.notifyDataSetChanged();

                    }
                });





                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                builder.show();
            }


            @Override
            public void updateClickedCatHab(Juego juego) {

            //PENDIENTE

            }


        });



    }

    //METODO PARA INGRESAR A LOS JUEGOS CREADOS
    @Override
    public void onJuegoClick(Juego juego) {

        int numero = preguntaViewModel.numeroPreguntas(juego.getJuego_id());
        Log.d("DetalleJuego","NUMERO "+numero);
        if (numero>0){
            Intent intent = new Intent(getActivity(), VisorPregunta.class);
            intent.putExtra("juego",juego);
            startActivity(intent);


        } else if (numero==0){
            Intent intent = new Intent(getActivity(), JuegoPrincipal.class);
            intent.putExtra("juego",juego);
            startActivity(intent);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}