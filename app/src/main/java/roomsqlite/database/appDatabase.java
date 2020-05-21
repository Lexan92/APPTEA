package roomsqlite.database;

import android.app.Person;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.entidades.CatalogoHabCotidianas;

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

@Database(entities = {CatalogoHabCotidianas.class, HabilidadCotidiana.class, CatalogoPictograma.class, CategoriaJuego.class,
        Departamento.class, Municipio.class, Pais.class, PersonaTea.class, Pictograma.class, Usuario.class}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;

    //DECLARACION DE DAOS
    public abstract CategoriaJuegoDAO categoriaJuegoDAO();

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
