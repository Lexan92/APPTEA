package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.RespuestaFaqDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.RespuestaFaq;

public class RespuestaFaqRepository {

    private RespuestaFaqDao respuestaFaqDao;


    public RespuestaFaqRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        respuestaFaqDao = db.respuestaFaqDao();
    }

    public LiveData<List<RespuestaFaq>> findRespuestaByFaq(int id){
        return respuestaFaqDao.getRespuestasByFaq(id);
    }

    public void insert(RespuestaFaq respuestaFaq){
       respuestaFaqDao.insert(respuestaFaq);
    }

    public void update(RespuestaFaq respuestaFaq){
        respuestaFaqDao.updateRespuestaFaq(respuestaFaq);
    }

    public void delete(RespuestaFaq respuestaFaq){
        respuestaFaqDao.deleteRespuestaFaq(respuestaFaq);
    }

}
