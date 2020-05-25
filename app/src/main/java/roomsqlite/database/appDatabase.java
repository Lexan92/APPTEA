package roomsqlite.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roomsqlite.dao.CategoriaHabCotidianaDao;
import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.dao.DepartamentoDao;
import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.dao.MunicipioDao;
import roomsqlite.dao.PaisDao;
import roomsqlite.dao.PersonaTeaDao;
import roomsqlite.dao.UsuarioDao;

import roomsqlite.entidades.CategoriaHabCotidiana;


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

@Database(entities = {CategoriaHabCotidiana.class, HabilidadCotidiana.class, CatalogoPictograma.class, CategoriaJuego.class,
        Departamento.class,/* Municipio.class,*/ Pais.class, PersonaTea.class, Pictograma.class, Usuario.class}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //DECLARACION DE DAOS

    public abstract CategoriaHabCotidianaDao categoriaHabCotidianaDao();
    public abstract HabilidadCotidianaDao habilidadCotidianaDao();
    public abstract CategoriaJuegoDAO categoriaJuegoDAO();

    public abstract PaisDao paisDao();
    public abstract DepartamentoDao departamentoDao();
   /* public abstract MunicipioDao municipioDao();*/
    public abstract UsuarioDao usuarioDao();
    public  abstract PersonaTeaDao personaTeaDao();

    //OBTENER INSTANCIA DE LA BASE DE DATOS
    public static appDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (appDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    appDatabase.class,constantes.getBdName())
                            .addCallback(sRoomDatabaseCallback)
                    .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {

                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    CategoriaHabCotidianaDao dao = INSTANCE.categoriaHabCotidianaDao();
                    dao.deleteAll();
                    CategoriaHabCotidiana categoriaHabCotidiana = new CategoriaHabCotidiana(1, "categoria 1");
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(2, "categoria 2");
                    dao.insert(categoriaHabCotidiana);

                    PaisDao paisesdao = INSTANCE.paisDao();
                    paisesdao.deletePaisAll();
                    Pais pais = new Pais(1,"El Salvador");
                    paisesdao.insertPais(pais);
                    pais= new Pais(2,"Guatemala");
                    paisesdao.insertPais(pais);
                    pais= new Pais(3,"Honduras");
                    paisesdao.insertPais(pais);

                    DepartamentoDao deptodao =INSTANCE.departamentoDao();
                    deptodao.deleteDepartamentoAll();
                    Departamento departamento = new Departamento(1,1,"San Salvador");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(2,1,"La libertad");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(3,1,"Santa Ana");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(4,1,"Ahuachapan");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(5,1,"Sonsonate");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(6,1,"Cabañas");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(7,1,"Chalatenango");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(8,1,"Cuscatlan");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(9,1,"La Paz");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(10,1,"La Union");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(11,1,"Morazan");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(12,1,"San Miguel");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(13,1,"San Vicente");
                    deptodao.insertDepartamento(departamento);
                    departamento = new Departamento(14,1,"Usulutan");
                    deptodao.insertDepartamento(departamento);



            });
        }
    };


}
