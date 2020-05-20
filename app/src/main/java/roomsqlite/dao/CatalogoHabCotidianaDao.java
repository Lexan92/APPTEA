package roomsqlite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.ListIterator;

import roomsqlite.entidades.CatalogoHabCotidiana;

@Dao
public interface CatalogoHabCotidianaDao {

    @Query("SELECT * FROM cat_habilidad_cotidiana")
    List<CatalogoHabCotidiana> getAll();

    @Insert
    void insertAll(CatalogoHabCotidiana...cathabilidades);

    @Delete
    void delete(CatalogoHabCotidiana catalogoHabCotidiana);
}
