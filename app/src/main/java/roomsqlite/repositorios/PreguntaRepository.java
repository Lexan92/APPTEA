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

import roomsqlite.dao.PreguntaDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Pregunta;

public class PreguntaRepository {

    private PreguntaDAO preguntaDAO;

    public PreguntaRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        preguntaDAO = db.preguntaDAO();
    }

    public void insert(Pregunta pregunta){
        preguntaDAO.insertPregunta(pregunta);
    }

    public void update(Pregunta pregunta){
        preguntaDAO.updatePregunta(pregunta);
    }

    public void delete(Pregunta pregunta){
        preguntaDAO.deletePregunta(pregunta);
    }

    public LiveData<List<Pregunta>> findPreguntasByIdJuego(int id){
        return preguntaDAO.getPreguntasByJuego(id);
    }

//    public LiveData<Pregunta> obtenerUltima(){
//        return preguntaDAO.obtenerUltimaPregunta();
//    }
}
