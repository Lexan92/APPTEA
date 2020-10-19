package roomsqlite.dao;

import android.widget.LinearLayout;

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

    @Query("DELETE FROM secuencia WHERE habilidad_cotidiana_id = :id")
    void deleteSecuenciaByHabilidad(int id);

    //metodo donde se recuperan todas  habilidades cotidianas
    @Query("SELECT * FROM secuencia ORDER BY secuencia_orden ASC")
    LiveData<List<Secuencia>> getSecuencia();

    //metodo donde se recuperan todas  secuencias por IDde de habilidad cotidiana
    @Query("SELECT * FROM secuencia WHERE habilidad_cotidiana_id = :id ORDER BY secuencia_orden ASC")
    List<Secuencia> getSecuenciaByHabilidad(int id);

    //metodo donde se recuperan una secuencia segun habilidad y si es predeterminado
    @Query("SELECT * FROM secuencia WHERE habilidad_cotidiana_id = :id AND sec_predeterminado=1")
    LiveData<Secuencia> getSecuenciaForImagen(int id);

    //metodo donde se recuperan una secuencia segun habilidad y si es predeterminado
    @Query("SELECT * FROM secuencia WHERE habilidad_cotidiana_id = :id AND sec_predeterminado = 1")
    Secuencia getSecuenciaForImagenSec(int id);



   /* @Query("SELECT COUNT ( pictograma_id ) FROM  secuencia WHERE pictograma_id =:id ")
    int numeroPictogramaS(int id);*/

}
