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

import roomsqlite.dao.CategoriaHabCotidianaDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;

public class CategoriaHabCotidianaRepository {

    private CategoriaHabCotidianaDao categoriaHabCotidianaDao;
    private LiveData<List<CategoriaHabCotidiana>> categoriaHabCotidianaAll;


    public CategoriaHabCotidianaRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        categoriaHabCotidianaDao = db.categoriaHabCotidianaDao();
        categoriaHabCotidianaAll = categoriaHabCotidianaDao.getCatalogoHabCotidiana();
    }

    public LiveData<List<CategoriaHabCotidiana>> getTodasCategoriaHabCotidiana(){
        return categoriaHabCotidianaAll;
    }

    public void insert(CategoriaHabCotidiana categoriaHabCotidiana){
        appDatabase.databaseWriteExecutor.execute(()->{
                    categoriaHabCotidianaDao.insert(categoriaHabCotidiana);
                });
        }





}
