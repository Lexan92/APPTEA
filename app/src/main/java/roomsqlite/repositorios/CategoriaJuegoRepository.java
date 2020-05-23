

package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaJuego;

public class CategoriaJuegoRepository {


    private CategoriaJuegoDAO categoriaJuegoDAO;
    private LiveData<List<CategoriaJuego>> allCategoriaJuego;

    public CategoriaJuegoRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        categoriaJuegoDAO = db.categoriaJuegoDAO();
        allCategoriaJuego = categoriaJuegoDAO.getAllCategoriasJuegos();
    }

    public LiveData<List<CategoriaJuego>> getAllCategoriaJuego(){
        return allCategoriaJuego;
    }

    public void insert (CategoriaJuego categoriaJuego){
       categoriaJuegoDAO.insertCategoriaJuego(categoriaJuego);
    }
}
