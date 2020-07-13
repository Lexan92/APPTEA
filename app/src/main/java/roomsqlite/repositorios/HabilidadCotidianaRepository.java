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

import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.HabilidadCotidiana;

public class HabilidadCotidianaRepository {

    private HabilidadCotidianaDao habilidadCotidianaDao;
    private LiveData<List<HabilidadCotidiana>> habilidadCotidianaAll;


    public HabilidadCotidianaRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        habilidadCotidianaDao = db.habilidadCotidianaDao();
        habilidadCotidianaAll = habilidadCotidianaDao.getHabilidadCotidiana();
    }

    public LiveData<List<HabilidadCotidiana>> getTodasHabilidadCotidiana(){
        return habilidadCotidianaAll;
    }

    public LiveData<List<HabilidadCotidiana>> findHabilidadCotidianaByCategoriaHab(int id){
        return habilidadCotidianaDao.getHabilidadCotidianaByCatHabilidad(id);
    }

    public void insert(HabilidadCotidiana habilidadCotidiana){
        appDatabase.databaseWriteExecutor.execute(()-> habilidadCotidianaDao.insert(habilidadCotidiana));
    }

    public void update(HabilidadCotidiana habilidadCotidiana){
        appDatabase.databaseWriteExecutor.execute(()-> habilidadCotidianaDao.updateHabilidadCotidiana(habilidadCotidiana));
    }

    public void delete(HabilidadCotidiana habilidadCotidiana){
        appDatabase.databaseWriteExecutor.execute(()-> habilidadCotidianaDao.deleteHabilidadCotidiana(habilidadCotidiana));
    }


}
