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

    @Query("SELECT COUNT (pregunta_id) FROM "+ Opcion.TABLE_NAME+ " WHERE pregunta_id=:id")
    int numeroOpciones(int id);

    /*@Query("SELECT COUNT ( pictograma_id ) FROM " + Opcion.TABLE_NAME + " WHERE pictograma_id =:id ")
    int numeroPictogramaO(int id);*/

}
