package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import roomsqlite.entidades.Resultado;

@Dao
public interface ResultadoDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertResultado(Resultado resultado);


    @Query("SELECT * FROM resultado")
    LiveData<List<Resultado>> getAllResultado();

    //RESULTADO DE UN JUEGO EN UNA SESION
    @Query("SELECT * FROM resultado r " +
            "INNER JOIN pregunta p ON p.pregunta_id = r.pregunta_id " +
            "WHERE p.juego_id = :id_juego AND r.sesion_id = :id_sesion ")
    LiveData<List<Resultado>> findAllResultadoByJuegoSesion(int id_juego, int id_sesion);

}
