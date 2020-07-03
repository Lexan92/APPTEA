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
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Opcion;

@Dao
public interface OpcionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOpcion(Opcion opcion);
    @Update
    void updateOpcion(Opcion opcion);
    @Query("select * from "+Opcion.TABLE_NAME+ " where pregunta_id=:id")
    LiveData<List<Opcion>> getOpcionesByPregunta(int id);
}
