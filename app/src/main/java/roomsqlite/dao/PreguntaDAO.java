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
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Pregunta;

@Dao
public interface PreguntaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPregunta(Pregunta pregunta);

    @Update
    void updatePregunta(Pregunta pregunta);

    @Delete
    void deletePregunta(Pregunta pregunta);

    @Query("SELECT * FROM "+Pregunta.TABLE_NAME+" WHERE juego_id=:id")
    LiveData<List<Pregunta>> getPreguntasByJuego(int id);
}