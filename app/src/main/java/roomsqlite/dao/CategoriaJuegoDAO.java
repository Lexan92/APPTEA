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
 * AUTOR: ALEX
 * 19/MAYO/2020
 * CLASE INTERFAZ DAO PARA LA CLASE CATEGORIA JUEGO
 * */
import roomsqlite.entidades.CategoriaJuego;

@Dao
public interface CategoriaJuegoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoriaJuego(CategoriaJuego categoriaJuego);

    @Update
    void updateCategoriaJuego(CategoriaJuego categoriaJuego);

    @Delete
    void deleteCategoriaJuego(CategoriaJuego categoriaJuego);

    @Query("SELECT * FROM " + CategoriaJuego.TABLE_NAME)
    LiveData<List<CategoriaJuego>> getAllCategoriasJuegos();

    @Query("DELETE FROM "+CategoriaJuego.TABLE_NAME)
    public void deleteAllCategoriaJuegos();
}
