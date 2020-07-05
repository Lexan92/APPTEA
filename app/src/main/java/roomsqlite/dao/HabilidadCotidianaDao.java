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

@Dao
public interface HabilidadCotidianaDao {

    //metodo donde se recuperan todas  habilidades cotidianas
    @Query("SELECT * FROM habilidad_cotidiana ORDER BY habilidad_cotidiana_nombre ASC")
    LiveData<List<HabilidadCotidiana>> getHabilidadCotidiana();

    @Query("DELETE FROM habilidad_cotidiana")
    public void deleteAll();

    @Update
    void updateHabilidadCotidiana(HabilidadCotidiana habilidadCotidiana);

    //metodo donde se inserta habilidades cotidianas
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(HabilidadCotidiana habilidadCotidiana);

    //Metodo de prueba de eliminar se ocupara el metodo deleteHabilidadCotidiana
    @Delete
    void deleteHabilidadCotidiana(HabilidadCotidiana habilidadCotidiana);
/*
    //metodo donde se elimina una categoria de habilidades cotidianas junto con sus habilidades cotidianas..
    @Delete
    public void deleteHabilidadCotidiana(HabilidadCotidiana habilidadCotidiana, List<HabilidadCotidiana> habilidadesCotidianas);
*/
}
