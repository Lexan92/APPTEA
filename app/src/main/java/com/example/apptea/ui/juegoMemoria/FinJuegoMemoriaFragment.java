package com.example.apptea.ui.juegoMemoria;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apptea.R;
import com.example.apptea.ui.juegoSeleccion.DetalleResultadoAdapter;
import com.example.apptea.ui.juegoSeleccion.DetalleResultadoViewModel;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.TTSManager;

import java.util.List;

import roomsqlite.entidades.DetalleResultado;
import roomsqlite.entidades.Resultado;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinJuegoMemoriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinJuegoMemoriaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    TTSManager ttsManager = null;
    RecyclerView recyclerView;
    private DetalleResultadoViewModel detalleResultadoViewModel;
    Resultado resultado = new Resultado();
    TextView nombreJuego;
    Button fin;
    int catJuego = 0;


    public FinJuegoMemoriaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinJuegoMemoriaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinJuegoMemoriaFragment newInstance(String param1, String param2) {
        FinJuegoMemoriaFragment fragment = new FinJuegoMemoriaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fin_juego_memoria, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle argumento = getArguments();
        resultado = (Resultado) argumento.getSerializable("resultado");
        nombreJuego = view.findViewById(R.id.ResNombreJuego);
        ttsManager = new TTSManager();
        ttsManager.init(getContext());
        fin = view.findViewById(R.id.fin);
        String frase = "¡Fin del Juego! ¡Bien hecho! ";
        AdministarSesion administarSesion = new AdministarSesion(getContext());

        int milisegundos = 1000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ttsManager.initQueue(frase);
            }
        }, milisegundos);

        if (administarSesion.obtenerIDSesion() > 0) {
            nombreJuego.setText("N° de fallos: " + resultado.getNombre_juego());
            recyclerView = view.findViewById(R.id.lista_detalleRes);
            final DetalleResultadoAdapter detalleResultadoAdapter = new DetalleResultadoAdapter();
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            recyclerView.setAdapter(detalleResultadoAdapter);
            detalleResultadoViewModel = new ViewModelProvider(this).get(DetalleResultadoViewModel.class);
            detalleResultadoViewModel.getDetalleResultadoByResultado(resultado.getResultado_id()).observe(getActivity(), new Observer<List<DetalleResultado>>() {
                @Override
                public void onChanged(List<DetalleResultado> detalleResultados) {
                    detalleResultadoAdapter.setDetalleResultados(detalleResultados);

                }
            });
        }

        fin.setOnClickListener(v -> {
            Bundle bundleEnvio = new Bundle();
            catJuego = argumento.getInt("categoriaJuego", -1);
            bundleEnvio.putInt("objeto", catJuego);
            Navigation.findNavController(view).navigate(R.id.action_finJuegoMemoriaFragment_to_detalleJuegoPaciente, bundleEnvio);
        });


    }

}
