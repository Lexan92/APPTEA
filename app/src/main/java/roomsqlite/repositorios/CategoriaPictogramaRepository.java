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

import roomsqlite.dao.CategoriaPictogramaDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Pictograma;


public class CategoriaPictogramaRepository {


    private CategoriaPictogramaDAO categoriaPictogramaDAO;
    private LiveData<List<CategoriaPictograma>> allCategoriaPictograma;

    public CategoriaPictogramaRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        categoriaPictogramaDAO = db.categoriaPictogramaDAO();
        allCategoriaPictograma = categoriaPictogramaDAO.getAllCategoriasPictogramas();
    }

    public LiveData<List<CategoriaPictograma>> getAllCategoriaPictograma(){
        return allCategoriaPictograma;
    }

   public void insert (CategoriaPictograma categoriaPictograma){

        categoriaPictogramaDAO.insertCategoriaPictograma(categoriaPictograma);
    }

    public LiveData<CategoriaPictograma> findByIdCategoria(int id){
        return categoriaPictogramaDAO.findbyCategoriaPictograma(id);
    }

   /* public void insert(CategoriaPictograma categoriaPictograma){
        appDatabase.databaseWriteExecutor.execute(()-> categoriaPictogramaDAO.insert(categoriaPictograma));
    }*/

    public void update(CategoriaPictograma categoriaPictograma){
        appDatabase.databaseWriteExecutor.execute(()-> categoriaPictogramaDAO.updateCategoriaPictograma(categoriaPictograma));
    }

    public void delete(CategoriaPictograma categoriaPictograma){
        appDatabase.databaseWriteExecutor.execute(()-> categoriaPictogramaDAO.deleteCategoriaPictograma(categoriaPictograma));
    }

    public int numPictoJuego(int id) {return categoriaPictogramaDAO.numPictoJuego(id);}

    public int numPictoHabilidad(int id) {return categoriaPictogramaDAO.numPictoHabilidad(id);}




}
