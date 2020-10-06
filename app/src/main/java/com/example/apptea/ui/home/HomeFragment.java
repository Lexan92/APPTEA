package com.example.apptea.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.example.apptea.InterfaceDrawer;
import com.example.apptea.MainActivity;
import com.example.apptea.R;
import com.example.apptea.utilidades.AdministarSesion;
import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.Executor;

public class HomeFragment extends Fragment {
    MaterialCardView frases, vocabulario, habilidades, juegos;
    Executor executor;
    BiometricManager biometricManager;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    boolean bandera = true;



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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        frases = vista.findViewById(R.id.card_frases);
        vocabulario = vista.findViewById(R.id.card_vocabulario);
        habilidades = vista.findViewById(R.id.card_habilidades);
        juegos = vista.findViewById(R.id.card_juegos);


        biometricManager = BiometricManager.from(requireContext());
        verificarEstadoBiometrico(biometricManager);
        executor = ContextCompat.getMainExecutor(requireContext());
        biometricPrompt = new BiometricPrompt((FragmentActivity) requireContext(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Navigation.findNavController(getView()).navigate(R.id.menu_a_accesoPin);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Bundle bundle = new Bundle();
                bundle.putBoolean("bandera", bandera);
                Navigation.findNavController(getView()).navigate(R.id.action_nav_home_to_nav_gestion_juego, bundle);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Navigation.findNavController(getView()).navigate(R.id.menu_a_accesoPin);
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Acceso a Juegos Interactivos")
                .setDescription("Usa tu huella dactilar para ingresar a esta opción")
                .setNegativeButtonText("Usar Password")
                .build();


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
                Bundle bundle = new Bundle();
                bundle.putBoolean("ban", bandera);
                Navigation.findNavController(v).navigate(R.id.nav_gestion_habilidad, bundle);
            }
        });

        juegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });


        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getContext(), "Debe cerrar sesión", Toast.LENGTH_LONG).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);

        return vista;
    }

    private void verificarEstadoBiometrico(BiometricManager biometricManager) {
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e("MY_APP_TAG", "The user hasn't associated " +
                        "any biometric credentials with their account.");
                break;
        }
    }


}
