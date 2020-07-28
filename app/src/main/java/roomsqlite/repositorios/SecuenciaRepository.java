package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.SecuenciaDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Secuencia;

public class SecuenciaRepository {

    private SecuenciaDao secuenciaDao;
    private LiveData<List<Secuencia>> secuenciaAll;


    public SecuenciaRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        secuenciaDao = db.secuenciaDao();
        secuenciaAll = secuenciaDao.getSecuencia();
    }

    public LiveData<List<Secuencia>> getSecuenciaByHabCotidiana(){
        return secuenciaAll;
    }

    public LiveData<List<Secuencia>>getSecuenciaByHabilidadCotidiana(int id){
        return secuenciaDao.getSecuenciaByHabilidad(id);
    }

    public void insert(Secuencia secuencia){
        appDatabase.databaseWriteExecutor.execute(()-> secuenciaDao.insert(secuencia));
    }

    public void update(Secuencia secuencia){
        appDatabase.databaseWriteExecutor.execute(()-> secuenciaDao.updateSecuencia(secuencia));
    }

    public void delete(Secuencia secuencia){
        appDatabase.databaseWriteExecutor.execute(()-> secuenciaDao.deleteSecuencia(secuencia));
    }

    public int numeroPictogramaS(int id) {return secuenciaDao.numeroPictogramaS(id);}


}
