package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Sesion;

@Dao
public interface SesionDao {

    @Insert
    void insertarSesion(Sesion sesion);
    @Delete
    void borrarSesion(Sesion sesion);

    @Query("SELECT * FROM "+Sesion.NOMBRE_TABLA+" WHERE persona_id=:id")
    LiveData<List<Sesion>> obtenerPersonaTeaPorId(int id);
}
