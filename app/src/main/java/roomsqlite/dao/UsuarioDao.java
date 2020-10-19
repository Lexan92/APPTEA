package roomsqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import roomsqlite.entidades.Usuario;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario LIMIT 1")
    LiveData<List<Usuario>> getAllUsuario();

    @Query("DELETE FROM usuario")
    public void deleteUsuarioAll();

    @Query("SELECT * FROM usuario LIMIT 1")
    public Usuario obtenerUsuario();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsuario(Usuario usuario);

    @Update
    void updateUsuario(Usuario usuario);

    @Delete
    void deleteUsuario(Usuario usuario);


}
