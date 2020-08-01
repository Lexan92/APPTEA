package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pregunta;
import roomsqlite.entidades.Secuencia;

@Dao
public interface SecuenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSecuencia (Secuencia[] secuencias);

    //metodo donde se inserta secuencia
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Secuencia secuencia);

    @Update
    void updateSecuencia(Secuencia secuencia);

    @Query("DELETE FROM secuencia")
    public void deleteAll();

    //Metodo para eliminar
    @Delete
    void deleteSecuencia(Secuencia secuencia);

    //metodo donde se recuperan todas  habilidades cotidianas
    @Query("SELECT * FROM secuencia ORDER BY secuencia_orden ASC")
    LiveData<List<Secuencia>> getSecuencia();

    //metodo donde se recuperan todas  secuencias por IDde de habilidad cotidiana
    @Query("SELECT * FROM secuencia WHERE habilidad_cotidiana_id = :id ORDER BY secuencia_orden ASC")
    List<Secuencia> getSecuenciaByHabilidad(int id);

    @Query("SELECT COUNT ( pictograma_id ) FROM  secuencia WHERE pictograma_id =:id ")
    int numeroPictogramaS(int id);

}
