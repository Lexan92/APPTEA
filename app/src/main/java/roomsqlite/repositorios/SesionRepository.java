package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.SesionDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Sesion;

public class SesionRepository {
    private final SesionDao sesionDao;


    public SesionRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        this.sesionDao = db.sesionDao();
    }

    public LiveData<List<Sesion>> obtenerSesionesPorPersona(int id){
        return sesionDao.obtenerPersonaTeaPorId(id);
    }
}
