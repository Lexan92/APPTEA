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
    public static final String TABLE_NAME = "CategoriaPictograma";

    @Insert
    void insertAllCategoriaPictograma(CategoriaPictograma[] categoriaPictogramas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoriaPictograma(CategoriaPictograma categoriaPictograma);

    @Update
    void updateCategoriaPictograma(CategoriaPictograma categoriaPictograma);

    @Delete
    void deleteCategoriaPictograma(CategoriaPictograma categoriaPictograma);


    //recupera todas las categorias de pictogramas
    @Query("SELECT * FROM categoria_pictograma ORDER BY cat_pictograma_nombre ASC" )
    LiveData<List<CategoriaPictograma>> getAllCategoriasPictogramas();

    @Query("DELETE FROM " + CategoriaPictograma.TABLE_NAME)
    public void deleteAllCategoriaPictogramas();

    @Query("SELECT * FROM " + CategoriaPictograma.TABLE_NAME + " WHERE cat_pictograma_id = :id")
    LiveData<CategoriaPictograma> findbyCategoriaPictograma(int id);

    //CUANTOS PICTOGRAMAS DE UNA CATEGORIA ESPECIFICA SE USAN EN JUEGOS
    @Query("SELECT COUNT(p.pictograma_id) FROM categoria_pictograma cp " +
            "INNER JOIN pictograma p ON p.cat_pictograma_id = cp.cat_pictograma_id "+
            " WHERE (SELECT p.pictograma_id FROM pictograma INNER JOIN opcion o ON o.pictograma_id=p.pictograma_id )" +
            "AND cp.cat_pictograma_id = :id")
    int numPictoJuego (int id);

    //CUANTOS PICTOGRAMAS DE UNA CATEGORIA ESPECIFICA SE USAN EN HABILIDADES
    @Query("SELECT COUNT(p.pictograma_id) FROM categoria_pictograma cp " +
            "INNER JOIN pictograma p ON p.cat_pictograma_id = cp.cat_pictograma_id "+
            " WHERE (SELECT p.pictograma_id FROM pictograma INNER JOIN secuencia s ON s.pictograma_id=p.pictograma_id )" +
            "AND cp.cat_pictograma_id = :id")
    int numPictoHabilidad (int id);

}



