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

import roomsqlite.dao.PaisDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Pais;

public class PaisRepository {
    private PaisDao paisDao;
    private LiveData<List<Pais>> paisAll;

    public PaisRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        paisDao =db.paisDao();
        paisAll = paisDao.getAllPais();
    }

    public LiveData<List<Pais>> getPais(){
        return paisAll;
    }

    public  void insert(Pais pais){
        paisDao.insertPais(pais);
    }
}
