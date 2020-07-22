package roomsqlite.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
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
    private static String [] picto;
    //DECLARACION DE DAOS

    public abstract CategoriaHabCotidianaDao categoriaHabCotidianaDao();
    public abstract HabilidadCotidianaDao habilidadCotidianaDao();
    public abstract CategoriaJuegoDAO categoriaJuegoDAO();
    public abstract CategoriaPictogramaDAO categoriaPictogramaDAO();
    public abstract PictogramaDAO pictogramaDAO();
    public abstract JuegoDAO juegoDAO();
    public abstract PreguntaDAO preguntaDAO();
    public abstract OpcionDAO opcionDAO();

    public abstract PaisDao paisDao();
    public abstract UsuarioDao usuarioDao();
    public  abstract PersonaTeaDao personaTeaDao();
    public abstract SecuenciaDao secuenciaDao();

    //OBTENER INSTANCIA DE LA BASE DE DATOS
    public static appDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (appDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    appDatabase.class,constantes.getBdName())
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
        public void onCreate (SupportSQLiteDatabase db) {
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
                    PersonaTeaDao personaTeaDao =INSTANCE.personaTeaDao();

                    //DELETEALL - primero de borran las entidades dependientes - segundo se borran la entidades independientes
                    paisesdao.deletePaisAll();
                    categoriaJuegoDAO.deleteAllCategoriaJuegos();
                    pictogramaDAO.deleteAllPictogramas();
                    categoriaPictogramaDAO.deleteAllCategoriaPictogramas();
                    personaTeaDao.deletePersonaAll();
                    usuarioDao.deleteUsuarioAll();

                    //INSERT
                    System.out.println("categorias habilidades");
                    dao.insertAllCatHabCotidiana(Data.catHabCotidianasData());

                    /*CategoriaHabCotidiana categoriaHabCotidiana = new CategoriaHabCotidiana(1, "Aseo personal",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(2, "El baño",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(3, "Agenda diaria",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(4, "Salud",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(5, "Comer",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(6, "Fiestas",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(7, "Vacaciones",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(8, "Cuidado Personal",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(9, "Paseos",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(10, "Amigos",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(11, "La cuidad",true);
                    dao.insert(categoriaHabCotidiana);
                    categoriaHabCotidiana = new CategoriaHabCotidiana(12, "La escuela",true);
                    dao.insert(categoriaHabCotidiana);*/

                    System.out.println("paises");
                    paisesdao.insertAllPais(Data.paises());

                    /*Pais pais = new Pais(1, "El Salvador");
                    paisesdao.insertPais(pais);
                    pais = new Pais(2, "Guatemala");
                    paisesdao.insertPais(pais);
                    pais = new Pais(3, "Honduras");
                    paisesdao.insertPais(pais);*/


                    //CATEGORIAS JUEGOS
                    categoriaJuegoDAO.insertAllCategoriaJuego(Data.categoriaJuegos());

                    /*CategoriaJuego cate = new CategoriaJuego(1, "Juegos de Selección", true);
                    categoriaJuegoDAO.insertCategoriaJuego(cate);*/

                    //LISTADO DE JUEGOS
                    juegoDAO.insertAllJuego(Data.juegos());

                    /*Juego juego = new Juego(1,1,"Juego Vocales",true);
                    juegoDAO.insertJuego(juego);
                    Juego juego1 = new Juego(2,1,"Juego Numeros",true);
                    juegoDAO.insertJuego(juego1);
                    Juego juego2 = new Juego(3,1,"Juego de la Frutas", true);
                    juegoDAO.insertJuego(juego2);
                    Juego juego3 = new Juego(4,1,"Juego de las Verduras", false);
                    juegoDAO.insertJuego(juego3);*/


                    System.out.println("categorias pictogramas");
                    categoriaPictogramaDAO.insertAllCategoriaPictograma(Data.categoriaPictogramas());

                /*CategoriaPictograma pic= new CategoriaPictograma(1,"Colores", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic);
                CategoriaPictograma pic1= new CategoriaPictograma(2,"Frutas", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic1);
                CategoriaPictograma pic2= new CategoriaPictograma(3,"Animales",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic2);
                CategoriaPictograma pic3= new CategoriaPictograma(4,"Verduras",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic3);
                CategoriaPictograma pic4 = new CategoriaPictograma(5,"Números",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic4);
                CategoriaPictograma pic5= new CategoriaPictograma(6,"Emociones",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic5);
                CategoriaPictograma pic6= new CategoriaPictograma(7,"Datos Generales", false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic6);
                CategoriaPictograma pic7= new CategoriaPictograma(8,"Teléfono", false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic7);
                CategoriaPictograma pic8= new CategoriaPictograma(9,"Direcciones Importantes",false);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic8);
                CategoriaPictograma pic9= new CategoriaPictograma(10,"Acciones",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic9);
                CategoriaPictograma pic10 = new CategoriaPictograma(11,"Enfermedades",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic10);
                CategoriaPictograma pic11= new CategoriaPictograma(12,"Sentimientos",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic11);
                CategoriaPictograma pic12= new CategoriaPictograma(13,"Bebidas", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic12);
                CategoriaPictograma pic13= new CategoriaPictograma(14,"Aseo Personal", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic13);
                CategoriaPictograma pic14= new CategoriaPictograma(15,"Accesorios",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic14);
                CategoriaPictograma pic15= new CategoriaPictograma(16,"Prendas de Vestir",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic15);
                CategoriaPictograma pic16 = new CategoriaPictograma(17,"Lugares",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic16);
                CategoriaPictograma pic17= new CategoriaPictograma(18,"Juguetes",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic17);
                CategoriaPictograma pic18= new CategoriaPictograma(19,"Abecedario", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic18);
                CategoriaPictograma pic19= new CategoriaPictograma(20,"Acontecimientos", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic19);
                CategoriaPictograma pic20= new CategoriaPictograma(21,"Casa",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic20);
                CategoriaPictograma pic21= new CategoriaPictograma(22,"Colegio",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic21);
                CategoriaPictograma pic22 = new CategoriaPictograma(23,"Cuerpo",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic22);
                CategoriaPictograma pic23= new CategoriaPictograma(24,"Normas de Cortesía",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic23);
                CategoriaPictograma pic24= new CategoriaPictograma(25,"Tiempo", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic24);
                CategoriaPictograma pic25= new CategoriaPictograma(26,"Medios de Transporte", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic25);
                CategoriaPictograma pic26= new CategoriaPictograma(27,"Medios de Comunicación",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic26);
                CategoriaPictograma pic27= new CategoriaPictograma(28,"Deporte",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic27);
                CategoriaPictograma pic28 = new CategoriaPictograma(29,"Pronombres",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic28);
                CategoriaPictograma pic29= new CategoriaPictograma(30,"Preposiciones",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic29);
                CategoriaPictograma pic30= new CategoriaPictograma(31,"Adjetivos", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic30);
                CategoriaPictograma pic31= new CategoriaPictograma(32,"Adverbios", true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic31);
                CategoriaPictograma pic32= new CategoriaPictograma(33,"Verbos",true);
                categoriaPictogramaDAO.insertCategoriaPictograma(pic32);
                */

                //PICTOGRAMA
                picto= Data.getPictogramaData();
                for(int i=0; i<picto.length;i++){
                        db.execSQL(picto[i]);
                }

                /*Pictograma picto= new Pictograma(1,  1,"Verde",true);
                pictogramaDAO.insert(picto);
                Pictograma picto1= new Pictograma(2, 1,"Azul",true);
                pictogramaDAO.insert(picto1);
                Pictograma picto2=new Pictograma(3,2,"Manzana",true);
                pictogramaDAO.insert(picto2);
                Pictograma picto3 = new Pictograma(4,2,"Pera",true);
                pictogramaDAO.insert(picto3);
                Pictograma picto4 = new Pictograma(5,2,"Uva",false);
                pictogramaDAO.insert(picto4);
                */

                /*
                 Usuario usuario = new Usuario(1,1,"juan","flores","123","juan@correo.com",12345678,"aqui",12);
                 usuarioDao.insertUsuario(usuario);

                 PersonaTea personaTea = new PersonaTea(1,1,"jose","flores",DateConverter.fromTimestamp("2000/05/12"),"Masculino","");
                 personaTeaDao.insertPersonaTea(personaTea);
                 */

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
