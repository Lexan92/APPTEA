package roomsqlite.database;

import android.app.Person;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roomsqlite.dao.CatalogoHabCotidianaDao;
import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.dao.DepartamentoDao;
import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.dao.MunicipioDao;
import roomsqlite.dao.PaisDao;
import roomsqlite.dao.UsuarioDao;

import roomsqlite.entidades.CatalogoHabCotidiana;
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
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //DECLARACION DE DAOS

    public abstract CatalogoHabCotidianaDao catalogoHabCotidianaDao();
    public abstract HabilidadCotidianaDao habilidadCotidianaDao();
    public abstract CategoriaJuegoDAO categoriaJuegoDAO();

    public abstract PaisDao paisDao();
    public abstract DepartamentoDao departamentoDao();
    public abstract MunicipioDao municipioDao();
    public abstract UsuarioDao usuarioDao();


    //OBTENER INSTANCIA DE LA BASE DE DATOS
    public static appDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (appDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    appDatabase.class,constantes.getBdName())
                            .addCallback(categoriaJuegoCallback)
                    .build();
                }
            }
        }

        return INSTANCE;
    }


    private static RoomDatabase.Callback categoriaJuegoCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                //WordDao dao = INSTANCE.wordDao();
                CategoriaJuegoDAO dao = INSTANCE.categoriaJuegoDAO();

                CategoriaJuego cate= new CategoriaJuego(1,"Juego Mental");
                dao.insertCategoriaJuego(cate);
                CategoriaJuego cate1= new CategoriaJuego(2,"Juego Memoria");
                dao.insertCategoriaJuego(cate1);
                CategoriaJuego cate2= new CategoriaJuego(3,"Juego Vocales");
                dao.insertCategoriaJuego(cate2);
                CategoriaJuego cate3= new CategoriaJuego(4,"Juego Consonantes");
                dao.insertCategoriaJuego(cate3);
                CategoriaJuego cate4= new CategoriaJuego(5,"Juego Repeticiones");
                dao.insertCategoriaJuego(cate4);
                CategoriaJuego cate5= new CategoriaJuego(6,"Juego Colores");
                dao.insertCategoriaJuego(cate5);

            });
        }
    };
}
