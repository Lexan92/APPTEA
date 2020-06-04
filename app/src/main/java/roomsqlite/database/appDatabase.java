package roomsqlite.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roomsqlite.dao.CategoriaHabCotidianaDao;
import roomsqlite.dao.CategoriaJuegoDAO;
import roomsqlite.dao.CategoriaPictogramaDAO;
import roomsqlite.dao.DepartamentoDao;
import roomsqlite.dao.HabilidadCotidianaDao;
import roomsqlite.dao.MunicipioDao;
import roomsqlite.dao.PaisDao;
import roomsqlite.dao.PersonaTeaDao;
import roomsqlite.dao.PictogramaDAO;
import roomsqlite.dao.UsuarioDao;

import roomsqlite.entidades.CategoriaHabCotidiana;


import roomsqlite.config.constantes;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.Departamento;
import roomsqlite.entidades.HabilidadCotidiana;
import roomsqlite.entidades.Municipio;
import roomsqlite.entidades.Pais;
import roomsqlite.entidades.PersonaTea;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Usuario;

@Database(entities = {CategoriaHabCotidiana.class, HabilidadCotidiana.class, CategoriaPictograma.class, CategoriaJuego.class,
        /*Departamento.class, Municipio.class,*/ Pais.class, PersonaTea.class, Pictograma.class, Usuario.class}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //DECLARACION DE DAOS

    public abstract CategoriaHabCotidianaDao categoriaHabCotidianaDao();
    public abstract HabilidadCotidianaDao habilidadCotidianaDao();
    public abstract CategoriaJuegoDAO categoriaJuegoDAO();
    public abstract CategoriaPictogramaDAO categoriaPictogramaDAO();
    public abstract PictogramaDAO pictogramaDAO();

    public abstract PaisDao paisDao();
    /*public abstract DepartamentoDao departamentoDao();*/
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
        public void onCreate (SupportSQLiteDatabase db) {
            try {
                databaseWriteExecutor.execute(() -> {
                    System.out.println("registro inicial");


                    CategoriaHabCotidianaDao dao = INSTANCE.categoriaHabCotidianaDao();
                    System.out.println("categorias habilidades");

                    CategoriaHabCotidiana categoriaHabCotidiana = new CategoriaHabCotidiana(1, "Aseo Personal");
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(2, "Vestimenta");
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(3, "Alimentación");
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(4, "Locomotrices");
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(5, "Comunicativas");
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(6, "Festividades");
                    dao.insert(categoriaHabCotidiana);


                    PaisDao paisesdao = INSTANCE.paisDao();
                    //DepartamentoDao deptodao =INSTANCE.departamentoDao();
                    //primero de borran las entidades dependientes
                    //  deptodao.deleteDepartamentoAll();

                    //segundo se borran la entidades independientes
                    paisesdao.deletePaisAll();
                    System.out.println("paises");

                    Pais pais = new Pais(1, "El Salvador");
                    paisesdao.insertPais(pais);
                    pais = new Pais(2, "Guatemala");
                    paisesdao.insertPais(pais);
                    pais = new Pais(3, "Honduras");
                    paisesdao.insertPais(pais);


                    /*
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
                    deptodao.insertDepartamento(departamento);*/


                    CategoriaJuegoDAO categoriaJuegoDAO = INSTANCE.categoriaJuegoDAO();
                    categoriaJuegoDAO.deleteAllCategoriaJuegos();
                    System.out.println("categoria juego");

                    CategoriaJuego cate = new CategoriaJuego(1, "Juego Mental", true);
                    categoriaJuegoDAO.insertCategoriaJuego(cate);
                    CategoriaJuego cate1 = new CategoriaJuego(2, "Juego Memoria",true);
                    categoriaJuegoDAO.insertCategoriaJuego(cate1);
                    CategoriaJuego cate2 = new CategoriaJuego(3, "Juego Vocales",false);
                    categoriaJuegoDAO.insertCategoriaJuego(cate2);
                    CategoriaJuego cate3 = new CategoriaJuego(4, "Juego Consonantes",true);
                    categoriaJuegoDAO.insertCategoriaJuego(cate3);
                    CategoriaJuego cate4 = new CategoriaJuego(5, "Juego Repeticiones",true);
                    categoriaJuegoDAO.insertCategoriaJuego(cate4);
                    CategoriaJuego cate5 = new CategoriaJuego(6, "Juego Colores",false);
                    categoriaJuegoDAO.insertCategoriaJuego(cate5);




                   // CategoriaPictogramaDAO pict  = INSTANCE.categoriaPictogramaDAO();


                CategoriaPictogramaDAO categoriaPictogramaDAO = INSTANCE.categoriaPictogramaDAO();
                categoriaPictogramaDAO.deleteAllCategoriaPictogramas();
                    System.out.println("categorias pictogramas");

                CategoriaPictograma pic= new CategoriaPictograma(1,"Colores", false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic);
                CategoriaPictograma pic1= new CategoriaPictograma(2,"Frutas", false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic1);
               /* CategoriaPictograma pic2= new CategoriaPictograma(3,"Animales",false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic2);
                CategoriaPictograma pic3= new CategoriaPictograma(4,"Verduras",false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic3);
                CategoriaPictograma pic4 = new CategoriaPictograma(5,"Números",false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic4);
                CategoriaPictograma pic5= new CategoriaPictograma(6,"Emociones",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic5);*/


                PictogramaDAO pictogramaDAO = INSTANCE.pictogramaDAO();
                pictogramaDAO.deleteAllPictogramas();

                Pictograma picto= new Pictograma(1,  1,"Verde",true);
                pictogramaDAO.insert(picto);
                Pictograma picto1= new Pictograma(2, 1,"Azul",false);
                pictogramaDAO.insert(picto1);
                Pictograma picto2=new Pictograma(3,2,"Manzana",true);
                pictogramaDAO.insert(picto2);
                Pictograma picto3 = new Pictograma(4,2,"Pera",true);
                pictogramaDAO.insert(picto3);
                Pictograma picto4 = new Pictograma(5,2,"Uva",false);
                pictogramaDAO.insert(picto4);




                    UsuarioDao usuarioDao = INSTANCE.usuarioDao();
                    PersonaTeaDao personaTeaDao =INSTANCE.personaTeaDao();
                    //primero de borran las entidades dependientes
                      personaTeaDao.deletePersonaAll();

                    //segundo se borran la entidades independientes
                    usuarioDao.deleteUsuarioAll();


                    Usuario usuario = new Usuario(1,1,"juan","flores","123","juan@correo.com",12345678,"aqui",12);
                    usuarioDao.insertUsuario(usuario);

                    PersonaTea personaTea = new PersonaTea(1,1,"jose","flores",DateConverter.fromTimestamp("2000/05/12"),"Masculino","");
                    personaTeaDao.insertPersonaTea(personaTea);

                    System.out.println("registro inicial finalizado");
                });
            }
            catch(Exception ex){
                System.out.println("no se puede insertar porque ya esta instalada "+ex.getMessage());
            }
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };






}
