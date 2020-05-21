package roomsqlite.dao;

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
    public void insertCategoriaJuego(CategoriaJuego categoriaJuego);

    @Update
    public void updateCategoriaJuego(CategoriaJuego categoriaJuego);

    @Delete
    public void deleteCategoriaJuego(CategoriaJuego categoriaJuego);

    @Query("SELECT * FROM " + CategoriaJuego.TABLE_NAME)
    List<CategoriaJuego> getAllCategoriasJuegos();
}
