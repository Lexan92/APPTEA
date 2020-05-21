package roomsqlite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Departamento;

@Dao
public interface DepartamentoDao {
    @Query("SELECT * FROM departamento")
    List<Departamento> getAllDepartamento();

    @Insert
    void insertAllDepartamento(Departamento departamento);

    @Update
    void updateDepartamento(Departamento departamento);

    @Delete
    void deleteDepartamento (Departamento departamento);
}
