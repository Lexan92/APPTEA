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

    /*pendiente
    @Query("DELETE FROM resultado r " +
            " INNER JOIN pregunta p ON p.pregunta_id = r.pregunta_id" +
            " INNER JOIN juego j ON j.juego_id = p.juego_id " +
            " INNER JOIN sesion_juego sj ON sj.sesion_id = j.sesion_id " +
            " WHERE sj.sesion_id = :id")
    void deleteResultadoBySesion(Resultado resultado);*/

    //CONSULTA MOSTRARA AL FINAL DE CADA JUEGO
    @Query("SELECT * FROM resultado")
    LiveData<List<Resultado>> getAllResultado();

}
