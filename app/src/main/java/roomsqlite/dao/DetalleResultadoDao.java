package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import roomsqlite.entidades.DetalleResultado;
import roomsqlite.entidades.Pictograma;

@Dao
public interface DetalleResultadoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetalleResultado(DetalleResultado detalleResultado);

    @Query("SELECT * FROM detalle_resultado")
    LiveData<List<DetalleResultado>> getAllDetalleResultado();

    @Query("SELECT * FROM  detalle_resultado  WHERE resultado_id = :id")
    LiveData<List<DetalleResultado>> findbyResultadoId(int id);


}
