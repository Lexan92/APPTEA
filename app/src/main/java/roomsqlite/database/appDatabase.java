package roomsqlite.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import roomsqlite.config.constantes;

@Database(entities = {/*aqui van la entidades*/}, version = 1, exportSchema = false)
public abstract class appDatabase extends RoomDatabase {

    private static volatile appDatabase INSTANCE;

    //DECLARACION DE DAOS

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
