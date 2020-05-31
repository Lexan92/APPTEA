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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apptea.R;

import java.util.List;

import roomsqlite.entidades.PersonaTea;

public class PersonaTeaAdapter  extends RecyclerView.Adapter<PersonaTeaAdapter.PersonaTeaHolder> {


    class PersonaTeaHolder extends RecyclerView.ViewHolder{
        private final TextView personaItemView;

        private PersonaTeaHolder(View itemView){
            super(itemView);
            personaItemView = itemView.findViewById((R.id.nombre_persona_tea));
        }
    }

    private final LayoutInflater mInflater;
    private List<PersonaTea> personaTeaList;

    PersonaTeaAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public PersonaTeaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.fragment_item_persona_tea,parent, false);



        return new PersonaTeaHolder(itemview);
    }

    @Override
    public void onBindViewHolder(PersonaTeaAdapter.PersonaTeaHolder holder, int position) {
        if (personaTeaList != null) {
            PersonaTea current = personaTeaList.get(position);
            holder.personaItemView.setText(current.getPersona_nombre());
        } else {
            // Covers the case of data not being ready yet.
            holder.personaItemView.setText("No se han ingresado personas a la lista");
        }
    }

    void setPersonas(List<PersonaTea> personaTeas){
        personaTeaList = personaTeas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(personaTeaList != null)
            return personaTeaList.size();
        else
            return 0;
    }
}
