package com.example.apptea.ui.verificarPin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptea.R;
import com.example.apptea.ui.usuario.UsuarioFragment;
import com.example.apptea.ui.usuario.UsuarioViewModel;
import com.example.apptea.ui.usuario.ValidarCodigo;
import com.example.apptea.utilidades.AdministarSesion;
import com.example.apptea.utilidades.EnviarCorreo;
import com.example.apptea.utilidades.GenerarNumAleatorio;
import com.example.apptea.utilidades.ValidarConexion;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Usuario;

import static android.view.View.VISIBLE;

public class AccesoPinInicio extends Fragment {

    EditText entradaPin;
    Button acceder, cancelar,contraseña;
    UsuarioViewModel usuarioViewModel;
    LiveData<List<Usuario>> usuario;
    int codigo;
    public LottieAnimationView sobre;
    FrameLayout contenedor;

    @Override
    public void onStart() {
        super.onStart();
        ocultarTeclado();
    }

    private void ocultarTeclado() {
        View vieww = getActivity().getCurrentFocus();
        if (vieww != null) {
            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

    public AccesoPinInicio() {
        //constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acceso_pin_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entradaPin = view.findViewById(R.id.edit_word);
        acceder = view.findViewById(R.id.boton_acceder);
        cancelar = view.findViewById(R.id.boton_cancelar);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        contraseña = view.findViewById(R.id.boton_olvidar_contra);
        contenedor = view.findViewById(R.id.contenedorAnimado);
        contenedor.setVisibility(View.GONE);
        sobre = view.findViewById(R.id.sobre);
        sobre.setVisibility(View.INVISIBLE);

        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" ");


        acceder.setOnClickListener(v -> {

            usuario = usuarioViewModel.getUsuarioAll();
            usuario.observe(getActivity(), usuarios -> {
                if (entradaPin.getText().toString().isEmpty()) {
                    entradaPin.setError(getResources().getString(R.string.campoVaciIngreseContra));
                } else if (usuarios.get(0).getContrasenia().equals(entradaPin.getText().toString())) {

                    //Se obtiene el usuario guardado se obtiene la primera fila.
                    UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getContext()).usuarioDao();
                    Usuario usuario = usuarioDao.obtenerUsuario();
                    //se inicializa la sesion
                    AdministarSesion administarSesion = new AdministarSesion(getContext());
                    administarSesion.guardarSesion(usuario);
                    Navigation.findNavController(getView()).navigate(R.id.action_accesoPinInicio_to_nav_home);
                } else {
                    entradaPin.setError(getResources().getString(R.string.contraseIncorecta));
                }
            });
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(R.id.action_accesoPinInicio_to_listadoInicioSesion);

            }
        });

        contraseña.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!ValidarConexion.compruebaConexion(getContext())) {
                    Toast.makeText(getContext(),getResources().getString(R.string.conexion_internet), Toast.LENGTH_SHORT).show();
                }else {
                    new progreso().execute();
                }
            }
        });


    }

    //metodo de llamada asincrona
    class progreso extends AsyncTask<Void, Integer,Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            contenedor.setVisibility(VISIBLE);
            contraseña.setVisibility(View.GONE);
            cancelar.setVisibility(View.GONE);
            sobre.setVisibility(View.VISIBLE);
            sobre.playAnimation();

        }

        @Override
        protected Void doInBackground(Void... voids) {


            //Se obtiene el usuario guardado se obtiene la primera fila.
            UsuarioDao usuarioDao = (UsuarioDao) appDatabase.getDatabase(getContext()).usuarioDao();
            Usuario usuario = usuarioDao.obtenerUsuario();

            //Se genera el codigo aleatorio y se guarda en variable int codigo
            codigo = GenerarNumAleatorio.getNumeroAleatorio();

            //Se inicializa el metodo para enviar correo
            EnviarCorreo enviarCorreo = new EnviarCorreo();
            enviarCorreo.Enviar(codigo,usuario.getCorreo());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(),getResources().getString(R.string.correoConExito),Toast.LENGTH_LONG).show();

            contenedor.setVisibility(View.GONE);
            sobre.setVisibility(View.INVISIBLE);
            contraseña.setVisibility(VISIBLE);
            cancelar.setVisibility(VISIBLE);
            sobre.cancelAnimation();

            Intent intent = new Intent(getActivity(), ValidarCodigo.class);
            intent.putExtra("codigo", codigo);
            startActivity(intent);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();
    }
}