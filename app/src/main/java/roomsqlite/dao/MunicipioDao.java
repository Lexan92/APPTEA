package roomsqlite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Municipio;

@Dao
public interface MunicipioDao {
/*
    @Query("SELECT * FROM municipio")
    List<Municipio> getAllMunicipio();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMunicipio(Municipio municipio);

    @Update
    void updateMunicipio(Municipio municipio);

    @Delete
    void deleteMunicipio(Municipio municipio);*/
}
