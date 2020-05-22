package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "habilidad_cotidiana")
public class HabilidadCotidiana {
    @PrimaryKey
    private int habilidad_cotidiana_id;

    @NonNull
    private String habilidad_cotidiana_nombre;


    public int getHabilidad_cotidiana_id() {
        return habilidad_cotidiana_id;
    }

    public void setHabilidad_cotidiana_id(int habilidad_cotidiana_id) {
        this.habilidad_cotidiana_id = habilidad_cotidiana_id;
    }

    @NonNull
    public String getHabilidad_cotidiana_nombre() {
        return habilidad_cotidiana_nombre;
    }

    public void setHabilidad_cotidiana_nombre(@NonNull String habilidad_cotidiana_nombre) {
        this.habilidad_cotidiana_nombre = habilidad_cotidiana_nombre;
    }
}
