package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pregunta;

@Dao
public interface HabilidadCotidianaDao {

    @Insert
    void insertAllHabilidadCotidiana (HabilidadCotidiana[] habilidadCotidianas);

    //metodo donde se inserta habilidades cotidianas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHabilidad(HabilidadCotidiana habilidadCotidiana);

    @Update
    void updateHabilidadCotidiana(HabilidadCotidiana habilidadCotidiana);

    @Query("DELETE FROM habilidad_cotidiana")
    public void deleteAll();

    //metodo donde se recuperan todas  habilidades cotidianas
    @Query("SELECT * FROM habilidad_cotidiana ORDER BY habilidad_cotidiana_nombre ASC")
    LiveData<List<HabilidadCotidiana>> getHabilidadCotidiana();

    //metodo donde se recuperan todas  habilidades cotidianas por ID de catgoria de habilidad
    @Query("SELECT * FROM habilidad_cotidiana WHERE cat_hab_cotidiana_id = :id ORDER BY habilidad_cotidiana_nombre ASC")
    LiveData<List<HabilidadCotidiana>> getHabilidadCotidianaByCatHabilidad(int id);

    @Query("SELECT * FROM habilidad_cotidiana ORDER BY habilidad_cotidiana_id DESC LIMIT 1")
    HabilidadCotidiana obtenerHabilidadCotidiana();

    @Query("SELECT * FROM habilidad_cotidiana WHERE habilidad_cotidiana_id=:id")
    HabilidadCotidiana obtenerUnaHabilidadCotidiana(int id);

    //Metodo de prueba de eliminar se ocupara el metodo deleteHabilidadCotidiana
    @Delete
    void deleteHabilidadCotidiana(HabilidadCotidiana habilidadCotidiana);
/*
    //metodo donde se elimina una categoria de habilidades cotidianas junto con sus habilidades cotidianas..
    @Delete
    public void deleteHabilidadCotidiana(HabilidadCotidiana habilidadCotidiana, List<HabilidadCotidiana> habilidadesCotidianas);
*/
}
