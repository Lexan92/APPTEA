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
import roomsqlite.entidades.CategoriaPictograma;


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
        return categoriaPictogramaDAO.findbyCategoriaPictogramaId(id);
    }






}
