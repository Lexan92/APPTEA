package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Faq;


@Dao
public interface FaqDao {

    //metodo donde se recuperan todas las categorias habilidades cotidianas
    @Query("SELECT * FROM faq ORDER BY faq_id ASC")
    LiveData<List<Faq>> getAllFaq();

    @Query("DELETE FROM faq")
    public void deleteAllFaq();

    @Update
    void updateFaq(Faq faq);

    //metodo donde para insertar una faq
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Faq faq);

    @Insert
    void insertAllFaq(Faq[] faq);

    //Metodo  eliminar para Faq
    @Delete
    void deleteFaq(Faq faq);
}
