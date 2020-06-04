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
        void Pictograma(Pictograma pictograma);


        @Query("SELECT * FROM " +Pictograma.TABLE_NAME)
        LiveData<List<Pictograma>> getAllPictogramas();

        @Query("DELETE FROM "+Pictograma.TABLE_NAME)
        public void deleteAllPictogramas();

        @Query("SELECT * FROM "+Pictograma.TABLE_NAME + " WHERE pictograma_id = :id")
        LiveData<Pictograma> findbyPictogramaId(int id);

        //metodo donde se recuperan todos los poctogramas
        @Query("SELECT * FROM pictograma ORDER BY pictograma_nombre ASC")
        LiveData<List<Pictograma>> getPictograma_nombre();

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        public void insert(Pictograma pictograma);

        //metodo que devuelve todos los pictogramas segun una categoria especifica
        @Query("SELECT * FROM " + Pictograma.TABLE_NAME + " WHERE cat_pictograma_id = :id")
        LiveData<List<Pictograma>> allPictogramaByCategoria(int id);


    }
