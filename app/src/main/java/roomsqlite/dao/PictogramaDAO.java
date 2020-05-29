/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

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
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Pictograma;

public interface PictogramaDAO {

    @Query("SELECT * FROM pictograma")
    LiveData<List<Pictograma>> getAllPictograma();

    @Query("DELETE FROM pictograma")
    public void deletePictogramaAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPictograma(Pictograma pictograma);

    @Update
    void updatePictograma(Pictograma pictograma);

    @Delete
    void deletePictograma(Pictograma pictograma);

}
