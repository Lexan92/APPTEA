package roomsqlite.database;

import android.app.Person;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.dao.DepartamentoDao;
import roomsqlite.dao.MunicipioDao;
import roomsqlite.dao.PaisDao;
import roomsqlite.dao.UsuarioDao;
import roomsqlite.entidades.CatalogoHabCotidiana;

import roomsqlite.config.constantes;
import roomsqlite.entidades.CatalogoPictograma;
import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Departamento;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Municipio;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Usuario;

@Database(entities = {CatalogoHabCotidiana.class, HabilidadCotidiana.class, CatalogoPictograma.class, CategoriaJuego.class,
        Departamento.class, Municipio.class, Pais.class, PersonaTea.class, Pictograma.class, Usuario.class}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;

    //DECLARACION DE DAOS
    public abstract CategoriaJuegoDAO categoriaJuegoDAO();

    public abstract PaisDao paisDao();
    public abstract DepartamentoDao departamentoDao();
    public abstract MunicipioDao municipioDao();
    public abstract UsuarioDao usuarioDao();

    //OBTENER INSTANCIA DE LA BASE DE DATOS
    static appDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (appDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    appDatabase.class,constantes.getBdName())
                    .build();
                }
            }
        }

        return INSTANCE;
    }
}
