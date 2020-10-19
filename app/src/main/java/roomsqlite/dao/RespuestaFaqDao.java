package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.RespuestaFaq;

@Dao
public interface RespuestaFaqDao {

    //metodo donde se recuperan todas las rspuestas por id de FAQ ordenadas por el campo orden
    @Query("SELECT * FROM respuesta_faq WHERE faq_id = :id ORDER BY orden ASC")
    LiveData<List<RespuestaFaq>> getRespuestasByFaq(int id);

    @Query("DELETE FROM respuesta_faq")
    public void deleteAllRespuestasFaq();

    @Update
    void updateRespuestaFaq(RespuestaFaq respuestaFaq);

    //metodo donde para insertar una respuesta faq
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(RespuestaFaq respuestaFaq);

    @Insert
    void insertAllRespuestaFaq(RespuestaFaq[] respuestaFaq);

    //Metodo  eliminar para Respuestas Faq
    @Delete
    void deleteRespuestaFaq(RespuestaFaq respuestaFaq);
}
