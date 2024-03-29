package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Sesion;

@Dao
public interface SesionDao {


    @Insert
    void insertarSesion(Sesion sesion);

    @Update
    void actualizarSesion(Sesion sesion);

    @Delete
    void borrarSesion(Sesion sesion);

    @Query("SELECT * FROM "+Sesion.NOMBRE_TABLA+" WHERE persona_id=:id")
    LiveData<List<Sesion>> obtenerPersonaTeaPorId(int id);

    @Query("SELECT * FROM "+ Sesion.NOMBRE_TABLA+" WHERE sesion_id=:id")
    Sesion obtenerSesionPorId(int id);

    @Query("SELECT * FROM "+ Sesion.NOMBRE_TABLA+" ORDER BY sesion_id DESC LIMIT 1")
    Sesion obtenerUltimaSesion();

    @Query("SELECT * FROM "+ Sesion.NOMBRE_TABLA+" WHERE sesion_id=:id")
    Sesion borrarSesionPorId(int id);
}
