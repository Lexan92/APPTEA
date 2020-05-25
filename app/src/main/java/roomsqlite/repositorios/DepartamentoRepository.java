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

import roomsqlite.dao.DepartamentoDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Departamento;

public class DepartamentoRepository {
    private DepartamentoDao departamentoDao;
    private LiveData<List<Departamento>> departamentoAll;

    public DepartamentoRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        departamentoDao =db.departamentoDao();
        departamentoAll = departamentoDao.getAllDepartamento();
    }

    public LiveData<List<Departamento>> getDepartamento(){
        return departamentoAll;
    }

    public  void insert(Departamento departamento){
        departamentoDao.insertDepartamento(departamento);
    }
}
