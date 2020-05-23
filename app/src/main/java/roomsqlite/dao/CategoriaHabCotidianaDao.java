package roomsqlite.dao;

/*
 * @autor: Oscar Turish
 * 20/05/2020
 * Esta Clase define el DAO para Catalogo de Habilidades Cotidianas
 * */

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.HabilidadCotidiana;

@Dao
public interface CategoriaHabCotidianaDao {

    //metodo donde se recuperan todas las categorias habilidades cotidianas
    @Query("SELECT * FROM cat_habilidad_cotidiana ORDER BY cat_hab_cotidiana_nombre ASC")
    LiveData<List<CategoriaHabCotidiana>> getCatalogoHabCotidiana();

    //metodo donde se inserta una categoria de habilidades cotidianas
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(CategoriaHabCotidiana cathabilidades);

    //metodo donde se elimina una categoria de habilidades cotidianas junto con sus habilidades cotidianas..
     @Delete
     public void deleteCategoriaHabCotidiana(CategoriaHabCotidiana categoriaHabCotidiana, List<HabilidadCotidiana> habilidadesCotidianas);
}
