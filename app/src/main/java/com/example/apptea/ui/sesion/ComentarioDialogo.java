package com.example.apptea.ui.sesion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.apptea.R;

import roomsqlite.dao.SesionDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Sesion;


public class ComentarioDialogo extends DialogFragment {

ImageButton cerrar;
Button guardar, cancelar;
EditText comentario;
Sesion sesion;


    public ComentarioDialogo() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return crearDialogComentario();
    }

    private AlertDialog crearDialogComentario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v =inflater.inflate(R.layout.fragment_comentario_dialogo,null);
        builder.setView(v);
        cerrar = v.findViewById(R.id.btn_cerrar_comentario);
        guardar = v.findViewById(R.id.btn_guardar_comentario);
        cancelar= v.findViewById(R.id.btn_cancelar_comentario);
        comentario = v.findViewById(R.id.editText_comentario);
        guardar.setVisibility(View.GONE);
        cancelar.setVisibility(View.GONE);
        Bundle objetoSesion = getArguments();
        sesion= (Sesion) objetoSesion.getSerializable("sesion");
        comentario.setText(sesion.getComentario());

        eventosBotones();
        return builder.create();
    }

    private void eventosBotones() {
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.VISIBLE);

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sesion.setComentario(comentario.getText().toString());
                SesionDao sesionDao = appDatabase.getDatabase(getActivity()).sesionDao();
                sesionDao.actualizarSesion(sesion);
                Toast.makeText(getActivity(),getResources().getString(R.string.comentarioActualizado),Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}