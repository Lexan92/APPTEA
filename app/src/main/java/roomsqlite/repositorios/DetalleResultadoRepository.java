package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.DetalleResultadoDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.DetalleResultado;

public class DetalleResultadoRepository {
    private DetalleResultadoDao detalleResultadoDao;
    private LiveData<List<DetalleResultado>> detalleResultadoAll;
    private DetalleResultado detalleResultado;

    public DetalleResultadoRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        detalleResultadoDao = db.detalleResultadoDao();
        detalleResultadoAll = detalleResultadoDao.getAllDetalleResultado();
    }

    public LiveData<List<DetalleResultado>> GetDetalleResultado(){return detalleResultadoAll;}

    public void insertDetalleResultado (DetalleResultado detalleResultado){detalleResultadoDao.insertDetalleResultado(detalleResultado);}
}
