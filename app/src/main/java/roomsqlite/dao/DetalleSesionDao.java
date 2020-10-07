package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomsqlite.entidades.DetalleSesion;

@Dao
public interface DetalleSesionDao {
    @Insert
    void insertarDetalleSesion(DetalleSesion detalleSesion);

    @Query("SELECT * FROM "+ DetalleSesion.NOMBRE_TABLA+" WHERE sesion_id=:id")
    LiveData<List<DetalleSesion>> obtenerDetallesPorId(int id);
}


