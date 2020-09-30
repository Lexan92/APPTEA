package roomsqlite.dao;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Rol;

@Dao
public interface RolDao {

    @Insert
    void insertAllRol(Rol[] roles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRol(Rol rol);

    @Update
    void updateRol(Rol rol);

    @Delete
    void deleteRol(Rol rol);

    @Query("SELECT * FROM rol")
    LiveData<List<Rol>> getAllRol();

    @Query("DELETE FROM rol")
    public void deleteRolAll();

    @Query("SELECT * FROM rol WHERE rol_id = :id")
    LiveData<Rol> findRolById(int id);

}
