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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apptea.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.PersonaTea;

public class PersonaListadoInicioAdapter extends RecyclerView.Adapter<PersonaListadoInicioAdapter.PersonaTeaHolder> implements View.OnClickListener {

    private List<PersonaTea> personaTeaList;
    private View.OnClickListener listener;
    private PersonaListadoInicioAdapter.ButtonClikedPersona buttonClikedPersona;


    public interface ButtonClikedPersona {
        void itemPersona(PersonaTea personaTea, View v);
    }

    public void setButtonClikedPersona(ButtonClikedPersona buttonClikedPersona) {
        this.buttonClikedPersona = buttonClikedPersona;
    }

    public class PersonaTeaHolder extends RecyclerView.ViewHolder {
        private final TextView personaItemNombre;
        private final TextView personaItemApellido;
        private final TextView personaItemEdad;
        private final ImageView personaItemFoto;


        private PersonaTeaHolder(View itemView) {
            super(itemView);
            personaItemNombre = itemView.findViewById((R.id.nombre_persona_tea));
            personaItemApellido = itemView.findViewById((R.id.apellido_persona_tea));
            personaItemEdad = itemView.findViewById((R.id.fecha_tea));
            personaItemFoto = itemView.findViewById(R.id.foto_persona);


        }
    }


    public PersonaListadoInicioAdapter() {
    }

    @Override
    public PersonaTeaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_iniciar_sesion, parent, false);
        return new PersonaTeaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PersonaListadoInicioAdapter.PersonaTeaHolder holder, int position) {
        if (personaTeaList != null) {
            PersonaTea current = personaTeaList.get(position);
            holder.personaItemNombre.setText(current.getPersona_nombre());
            holder.personaItemApellido.setText(current.getPersona_apellido());
            String edad = getAge(current.getPersona_fecha_nac());
            holder.personaItemEdad.setText(edad);
            String sexo = current.getPersona_sexo();
            byte[] foto = current.getPersona_foto();

            if (foto == null) {

                if (sexo.equals("Femenino")) {
                    holder.personaItemFoto.setImageResource(R.drawable.ic_linda);
                } else {
                    holder.personaItemFoto.setImageResource(R.drawable.ic_smile);
                }

            } else {
                Glide.with(holder.itemView.getContext())
                        .load(ImageConverter.convertirByteArrayAImagen(current.getPersona_foto()))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(true)
                        .into(holder.personaItemFoto);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClikedPersona.itemPersona(current, v);
                }
            });


        } else {
            // Covers the case of data not being ready yet.
            holder.personaItemNombre.setText("No se han ingresado personas a la lista");
        }


    }

    public void setPersonas(List<PersonaTea> personaTeas) {
        personaTeaList = personaTeas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (personaTeaList != null)
            return personaTeaList.size();
        else
            return 0;
    }

    private String getAge(Date fecha) {
        Calendar ffecha = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        ffecha.setTime(fecha);

        int age = today.get(Calendar.YEAR) - ffecha.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < ffecha.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
}
