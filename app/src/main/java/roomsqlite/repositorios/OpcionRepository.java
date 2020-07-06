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

import roomsqlite.dao.OpcionDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Opcion;

public class OpcionRepository {
    private OpcionDAO opcionDAO;

    public OpcionRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        opcionDAO = db.opcionDAO();
    }

    public void insert(Opcion opcion){
        opcionDAO.insertOpcion(opcion);
    }

    public  void update(Opcion opcion){
        opcionDAO.updateOpcion(opcion);
    }

    public LiveData<List<Opcion>> findOpcionesByIdPregunta(int id){
        return opcionDAO.getOpcionesByPregunta(id);
    }

    public int numeroOpciones(int id){
        return opcionDAO.numeroOpciones(id);
    }
}
