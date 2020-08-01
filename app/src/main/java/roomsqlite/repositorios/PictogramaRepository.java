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
import roomsqlite.dao.PictogramaDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.Pictograma;


public class PictogramaRepository {
    private PictogramaDAO pictogramaDAO;
    private LiveData<List<Pictograma>>allPictograma;


    public PictogramaRepository(Application application){
        appDatabase db=appDatabase.getDatabase(application);
        pictogramaDAO=db.pictogramaDAO();
        allPictograma=pictogramaDAO.getAllPictogramas();

    }

    public LiveData<List<Pictograma>> getAllPictograma() {
        return allPictograma;
    }
    public void insert(Pictograma pictograma){
        pictogramaDAO.insertPictograma(pictograma);
    }

    public LiveData<Pictograma>finfByIdPictograma(int id){
        return pictogramaDAO.findbyPictogramaId(id);
    }

    public LiveData<List<Pictograma>> findPictogramasByCategoria(int id){
        return pictogramaDAO.allPictogramaByCategoria(id);
    }

    public void deletePictograma(Pictograma pictograma){
        pictogramaDAO.deletePictograma(pictograma);
    }
    public void update(Pictograma pictograma){
        appDatabase.databaseWriteExecutor.execute(()-> pictogramaDAO.updatePictograma(pictograma));
    }

    public int numHabPicto(int id) {return pictogramaDAO.numHabPicto(id);}

    public  int numJuegoPicto (int id) {return pictogramaDAO.numJuegoPicto(id);}
}
