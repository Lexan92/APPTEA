/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.UsuarioDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Usuario;

public class UsuarioRepository {
    private UsuarioDao usuarioDao;
    private LiveData<List<Usuario>> usuarioAll;

    public UsuarioRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        usuarioDao =db.usuarioDao();
        usuarioAll = usuarioDao.getAllUsuario();
    }

    public LiveData<List<Usuario>> getUsuario(){
        return usuarioAll;
    }

    public  void insert(Usuario usuario){
       appDatabase.databaseWriteExecutor.execute(()-> usuarioDao.insertUsuario(usuario));

    }

    public void update(Usuario usuario){
        appDatabase.databaseWriteExecutor.execute(()-> usuarioDao.updateUsuario(usuario));
    }
}
