package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.DetalleSesionDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.DetalleSesion;

public class DetalleSesionRepository {
    private final DetalleSesionDao detalleSesionDao;

    public DetalleSesionRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        this.detalleSesionDao = db.detalleSesionDao();
    }

    public LiveData<List<DetalleSesion>> obtenerDetalleSesiones(int id){
        return detalleSesionDao.obtenerDetallesPorId(id);
    }
}
