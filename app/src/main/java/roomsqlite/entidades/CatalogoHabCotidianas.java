package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "cat_habilidad_cotidiana")
public class CatalogoHabCotidianas {
    @PrimaryKey(autoGenerate = true)
    private int cat_hab_cotidiana_id;

    @NonNull
    private String cat_hab_cotidiana_nombre;


}
