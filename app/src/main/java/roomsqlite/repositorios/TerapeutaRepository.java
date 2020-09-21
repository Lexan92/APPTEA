package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.TerapeutaDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Terapeuta;

public class TerapeutaRepository {
    private TerapeutaDao terapeutaDao;
    private LiveData<List<Terapeuta>>terapeutaAll;

    public TerapeutaRepository(Application application){
        appDatabase db=appDatabase.getDatabase(application);
        terapeutaDao=db.terapeutaDao();
        terapeutaAll=terapeutaDao.getAllTerapeuta();
    }

    public LiveData<List<Terapeuta>> getTodosTerapeutas(){
        return terapeutaAll;
    }

    public void insert(Terapeuta terapeuta){
        appDatabase.databaseWriteExecutor.execute(()-> terapeutaDao.insertTerapeuta(terapeuta));
    }

    public void update(Terapeuta  terapeuta){
        appDatabase.databaseWriteExecutor.execute(()-> terapeutaDao.updateTerapeuta(terapeuta));
    }

    public void delete(Terapeuta terapeuta){
        appDatabase.databaseWriteExecutor.execute(()-> terapeutaDao.deleteTerapeuta(terapeuta));
    }

    public LiveData<List<Terapeuta>> findTerapeutaByPersona(int id){
        return terapeutaDao.allTerapeutaByPersona(id);
    }

}
