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


    @Query("DELETE FROM resultado WHERE sesion_id=:id")
    void borrarResultadoPorId(int id);

    //OBTIENE EL ULTIMO RESULTADO INGRESADO
    @Query("SELECT * FROM resultado ORDER BY resultado_id DESC LIMIT 1")
    Resultado obtenerResultado();

}
