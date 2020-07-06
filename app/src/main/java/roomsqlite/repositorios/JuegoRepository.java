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

import roomsqlite.dao.JuegoDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Juego;

public class JuegoRepository {
    private JuegoDAO juegoDAO;
    private LiveData<List<Juego>> allJuegosByCategoria;

    public JuegoRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        juegoDAO = db.juegoDAO();

    }

    public void insert(Juego juego){
        juegoDAO.insertJuego(juego);
    }

    public void update(Juego juego){
        juegoDAO.updateJuego(juego);
    }

    public void delete(Juego juego){
        juegoDAO.deleteJuego(juego);
    }

    public LiveData<List<Juego>> findJuegosByCategoria(int id){
        return juegoDAO.findJuegosByCategoria(id);
    }

    public LiveData<Juego> obtenerUltimo(){
        return juegoDAO.obtenerUltimoJuego();
    }

    public LiveData<List<Juego>> obtenerTodosJuegos(){
        return juegoDAO.getAllJuegos();
    }
}
