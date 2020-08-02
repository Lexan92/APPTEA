package roomsqlite.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roomsqlite.config.constantes;
import roomsqlite.dao.CategoriaHabCotidianaDao;
import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.dao.CategoriaPictogramaDAO;
import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.dao.JuegoDAO;
import roomsqlite.dao.OpcionDAO;
import roomsqlite.dao.PaisDao;
import roomsqlite.dao.PersonaTeaDao;
import roomsqlite.dao.PictogramaDAO;
import roomsqlite.dao.PreguntaDAO;
import roomsqlite.dao.SecuenciaDao;
import roomsqlite.dao.UsuarioDao;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;
import roomsqlite.entidades.Secuencia;
import roomsqlite.entidades.Usuario;

@Database(entities = {CategoriaHabCotidiana.class, HabilidadCotidiana.class, CategoriaPictograma.class, CategoriaJuego.class,
        Pais.class, PersonaTea.class, Pictograma.class, Usuario.class, Juego.class, Pregunta.class, Opcion.class, Secuencia.class}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static String[] picto;
    public static DataTea dataTea;
    //DECLARACION DE DAOS

    public abstract CategoriaHabCotidianaDao categoriaHabCotidianaDao();

    public abstract HabilidadCotidianaDao habilidadCotidianaDao();

    public abstract CategoriaJuegoDAO categoriaJuegoDAO();

    public abstract CategoriaPictogramaDAO categoriaPictogramaDAO();

    public abstract PictogramaDAO pictogramaDAO();

    public abstract JuegoDAO juegoDAO();

    public abstract OpcionDAO opcionDAO();

    public abstract PaisDao paisDao();

    public abstract UsuarioDao usuarioDao();

    public abstract PersonaTeaDao personaTeaDao();

    public abstract SecuenciaDao secuenciaDao();

    public abstract PreguntaDAO preguntaDao();

    //OBTENER INSTANCIA DE LA BASE DE DATOS
    public static appDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (appDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            appDatabase.class, constantes.getBdName())
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            try {
                databaseWriteExecutor.execute(() -> {
                    System.out.println("registro inicial");

                    //INSTANCIAS
                    CategoriaHabCotidianaDao dao = INSTANCE.categoriaHabCotidianaDao();
                    PaisDao paisesdao = INSTANCE.paisDao();
                    CategoriaJuegoDAO categoriaJuegoDAO = INSTANCE.categoriaJuegoDAO();
                    JuegoDAO juegoDAO = INSTANCE.juegoDAO();
                    CategoriaPictogramaDAO categoriaPictogramaDAO = INSTANCE.categoriaPictogramaDAO();
                    PictogramaDAO pictogramaDAO = INSTANCE.pictogramaDAO();
                    UsuarioDao usuarioDao = INSTANCE.usuarioDao();
                    PersonaTeaDao personaTeaDao = INSTANCE.personaTeaDao();
                    HabilidadCotidianaDao habilidadCotidianaDao = INSTANCE.habilidadCotidianaDao();
                    SecuenciaDao secuenciaDao = INSTANCE.secuenciaDao();
                    PreguntaDAO preguntaDao = INSTANCE.preguntaDao();
                    OpcionDAO opcionDAO = INSTANCE.opcionDAO();


                    paisesdao.deletePaisAll();
                    categoriaJuegoDAO.deleteAllCategoriaJuegos();
                    pictogramaDAO.deleteAllPictogramas();
                    categoriaPictogramaDAO.deleteAllCategoriaPictogramas();
                    personaTeaDao.deletePersonaAll();
                    usuarioDao.deleteUsuarioAll();

                    //INSERT
                    System.out.println("categorias habilidades");
                    dao.insertAllCatHabCotidiana(DataTea.catHabCotidianasData());


                    System.out.println("paises");
                    paisesdao.insertAllPais(DataTea.paises());


                    //CATEGORIAS JUEGOS
                    categoriaJuegoDAO.insertAllCategoriaJuego(DataTea.categoriaJuegos());


                    //LISTADO DE JUEGOS
                    juegoDAO.insertAllJuego(DataTea.juegos());


                    //LISTADO DE PREGUNTAS
                    preguntaDao.insertAllPreguntas(DataTea.preguntas());


                    System.out.println("categorias pictogramas");
                    categoriaPictogramaDAO.insertAllCategoriaPictograma(DataTea.categoriaPictogramas());


                    //PICTOGRAMA
                    picto = DataTea.getPictogramaData();
                    for (int i = 0; i < picto.length; i++) {
                        db.execSQL(picto[i]);
                    }

                    //LISTADO DE OPCIONES
                    opcionDAO.insertAllOpciones(DataTea.Opcion());

                    System.out.println(" habilidades");
                    habilidadCotidianaDao.insertAllHabilidadCotidiana(DataTea.habilidadCotidianas());
                    secuenciaDao.insertAllSecuencia(DataTea.secuencias());

                    System.out.println("registro inicial finalizado");
                });
            } catch (Exception ex) {
                System.out.println("no se puede insertar porque ya esta instalada " + ex.getMessage());
            }
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


}
