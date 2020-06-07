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

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.repositorios.PersonaTeaRepository;

public class PersonaTeaViewModel extends AndroidViewModel {

    private PersonaTeaRepository personaTeaRepository;
    private LiveData<List<PersonaTea>> personaTeaAll;

    public PersonaTeaViewModel(Application application){
        super(application);
        personaTeaRepository = new PersonaTeaRepository(application);
        personaTeaAll = personaTeaRepository.getTodasPersonas();
    }

    public LiveData<List<PersonaTea>> getPersonaTeaAll(){
        return personaTeaAll;
    }

    public void insert(PersonaTea personaTea){
        personaTeaRepository.insert(personaTea);
    }

    public void update(PersonaTea personaTea){personaTeaRepository.update(personaTea);}

    public void delete(PersonaTea personaTea){personaTeaRepository.delete(personaTea);}

}
