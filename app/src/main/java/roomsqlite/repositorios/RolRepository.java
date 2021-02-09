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

import roomsqlite.dao.RolDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Rol;

public class RolRepository {
    private RolDao rolDao;
    private LiveData<List<Rol>> rolAll;
    private Rol rol;

    public RolRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        rolDao =db.rolDao();
        rolAll = rolDao.getAllRol();
    }

    public LiveData<List<Rol>> getRol(){
        return rolAll;
    }

    public  void insertRol(Rol rol){
        rolDao.insertRol(rol);
    }
    public  void deleteRol(Rol rol){rolDao.deleteRol(rol);}

    public LiveData<Rol>findRolById(int id){
        return rolDao.findRolById(id);
    }
}
