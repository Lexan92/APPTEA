package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.ResultadoDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Resultado;

public class ResultadoRepository {
    private ResultadoDao resultadoDao;
    private LiveData<List<Resultado>> resultadoAll;
    private Resultado resultado;

    public ResultadoRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        resultadoDao =db.resultadoDao();
        resultadoAll = resultadoDao.getAllResultado();
    }

    public LiveData<List<Resultado>> getResultado() { return resultadoAll;}

    public LiveData<List<Resultado>> findResultadoByJuegoSesion(int id_juego, int id_sesion) {
        return resultadoDao.findAllResultadoByJuegoSesion(id_juego, id_sesion);
    }

    public void insertResultado (Resultado resultado){resultadoDao.insertResultado(resultado);}



}
