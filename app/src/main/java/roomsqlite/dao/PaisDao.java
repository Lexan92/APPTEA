package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Pais;

@Dao
public interface PaisDao {

    @Query("SELECT * FROM pais")
    LiveData<List<Pais>> getAllPais();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPais(Pais pais);

    @Update
    void updatePais(Pais pais);

    @Delete
    void deletePais(Pais pais);
}
