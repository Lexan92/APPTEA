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

import roomsqlite.entidades.Juego;

@Dao
public interface JuegoDAO {

    @Insert
    void insertAllJuego(Juego[] juegos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJuego(Juego juego);

    @Update
    void updateJuego(Juego juego);

    @Delete
    void deleteJuego(Juego juego);


    @Query("SELECT * FROM "+Juego.TABLE_NAME+ " WHERE categoria_juego_id=:id")
    LiveData<List<Juego>> findJuegosByCategoria(int id);

    @Query("SELECT * FROM " +Juego.TABLE_NAME + " ORDER BY juego_id DESC LIMIT 1")
    LiveData<Juego> obtenerUltimoJuego();

    @Query("SELECT * FROM "+Juego.TABLE_NAME +" ORDER BY juego_id ASC" )
    LiveData<List<Juego>> getAllJuegos();
}
