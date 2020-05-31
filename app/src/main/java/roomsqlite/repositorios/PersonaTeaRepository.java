/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.PersonaTeaDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.PersonaTea;

public class PersonaTeaRepository {

    private PersonaTeaDao personaTeaDao;
    private LiveData<List<PersonaTea>> personaTeaAll;


    public PersonaTeaRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        personaTeaDao = db.personaTeaDao();
        personaTeaAll = personaTeaDao.getAllPersonaTea();
    }

    public LiveData<List<PersonaTea>> getTodasPersonas(){
        return personaTeaAll;
    }

    public void insert(PersonaTea personaTea){
        appDatabase.databaseWriteExecutor.execute(()-> personaTeaDao.insertPersonaTea(personaTea));
    }

}

