package roomsqlite.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
/*
 * AUTOR: GUADALUPE
 * CLASE INTERFAZ DAO PARA CATEGORIA PICTOGRAMA
 * */
import roomsqlite.entidades.CategoriaPictograma;

@Dao
public interface CategoriaPictogramaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoriaPictograma(CategoriaPictograma categoriaPictograma);

    @Update
    void updateCategoriaPictograma(CategoriaPictograma categoriaPictograma);

    @Delete
    void deleteCategoriaPictograma(CategoriaPictograma categoriaPictograma);

    @Query("SELECT * FROM " + CategoriaPictograma.TABLE_NAME)
    LiveData<List<CategoriaPictograma>> getAllCategoriasPictogramas();

    @Query("DELETE FROM "+CategoriaPictograma.TABLE_NAME)
    public void deleteAllCategoriaPictogramas();

    @Query("SELECT * FROM "+CategoriaPictograma.TABLE_NAME + " WHERE categoria_pictograma_id = :id")
    LiveData<CategoriaPictograma> findbyCategoriaPictogramaId(int id);
}




