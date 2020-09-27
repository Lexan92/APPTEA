package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Terapeuta;

@Dao
public interface TerapeutaDao {

    @Query("SELECT * FROM terapeuta")
    LiveData<List<Terapeuta>> getAllTerapeuta();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertTerapeuta(Terapeuta terapeuta);

    @Update
    void updateTerapeuta(Terapeuta terapeuta);

    @Query("DELETE FROM terapeuta")
    public void deleteTerapeutaAll();

    @Delete
    void deleteTerapeuta(Terapeuta terapeuta);

    @Query("SELECT * FROM terapeuta WHERE persona_id =:id")
    LiveData<List<Terapeuta>> allTerapeutaByPersona(int id);

}
