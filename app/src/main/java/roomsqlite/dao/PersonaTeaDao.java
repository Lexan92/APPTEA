/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.PersonaTea;

@Dao
public interface PersonaTeaDao {
    @Query("SELECT * FROM persona_tea")
    LiveData<List<PersonaTea>> getAllPersonaTea();

    @Insert
    void insertPersonaTea(PersonaTea personaTea);

    @Update
    void updatePersonaTea(PersonaTea personaTea);

    @Delete
    void deletePersonaTea(PersonaTea personaTea);
}
