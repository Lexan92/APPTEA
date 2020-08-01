package roomsqlite.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



import roomsqlite.entidades.Pictograma;


    @Dao
    public interface PictogramaDAO {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertPictograma(Pictograma pictograma);

        @Update
        void updatePictograma(Pictograma pictograma);

        @Delete
        void deletePictograma(Pictograma pictograma);

        @Query("DELETE FROM "+Pictograma.TABLE_NAME)
        public void deleteAllPictogramas();

        @Query("SELECT * FROM " +Pictograma.TABLE_NAME)
        LiveData<List<Pictograma>> getAllPictogramas();

        @Query("SELECT * FROM "+Pictograma.TABLE_NAME + " WHERE pictograma_id = :id")
        LiveData<Pictograma> findbyPictogramaId(int id);

<<<<<<< HEAD
        //metodo donde se recuperan todos los pictogramas
=======
        @Query("SELECT * FROM "+Pictograma.TABLE_NAME + " WHERE pictograma_id = :id")
        Pictograma findbyPictoId(int id);

        //metodo donde se recuperan todos los poctogramas
>>>>>>> d86f08c6f596f2b73576b0bb2bfdfd289909708f
        @Query("SELECT * FROM pictograma ORDER BY pictograma_nombre ASC")
        LiveData<List<Pictograma>> getPictograma_nombre();

       /* @Insert(onConflict = OnConflictStrategy.IGNORE)
        public void insert(Pictograma pictograma);*/

        //metodo que devuelve todos los pictogramas segun una categoria especifica
        @Query("SELECT * FROM " + Pictograma.TABLE_NAME + " WHERE cat_pictograma_id = :id")
        LiveData<List<Pictograma>> allPictogramaByCategoria(int id);

        //EN CUANTAS HABILIDADES SE USA UN PICTOGRAMA ESPECIFICO
        @Query("SELECT COUNT(DISTINCT s.habilidad_cotidiana_id) FROM pictograma p " +
                "INNER JOIN secuencia s on s.pictograma_id = p.pictograma_id  WHERE p.pictograma_id = :id")
        int numHabPicto (int id);

        //EN CUANTOS JUEGOS SE USA UN PICTOGRAMA ESPECIFICO
        @Query("SELECT COUNT (DISTINCT pr.juego_id ) FROM pictograma p " +
                "INNER JOIN opcion o ON o.pictograma_id = p.pictograma_id " +
                "INNER JOIN pregunta pr ON pr.pregunta_id = o.pregunta_id " +
                "WHERE p.pictograma_id = :id")
        int numJuegoPicto (int id);

    }
