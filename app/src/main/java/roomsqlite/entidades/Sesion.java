package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import roomsqlite.database.DateConverter;

@Entity(tableName = Sesion.NOMBRE_TABLA)
@TypeConverters(DateConverter.class)
public class Sesion {
    public static final String NOMBRE_TABLA="sesion";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    @NonNull
    private int sesion_id;
    @NonNull
    @ColumnInfo(index = true)
    private int persona_id;
    @NonNull
    private Date fecha_sesion;

    public Sesion() {
    }

    @Ignore
    public Sesion(int sesion_id, int persona_id, @NonNull Date fecha_sesion) {
        this.sesion_id = sesion_id;
        this.persona_id = persona_id;
        this.fecha_sesion = fecha_sesion;

    }




    public int getSesion_id() {
        return sesion_id;
    }

    public void setSesion_id(int sesion_id) {
        this.sesion_id = sesion_id;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }

    @NonNull
    public Date getFecha_sesion() {
        return fecha_sesion;
    }

    public void setFecha_sesion(@NonNull Date fecha_sesion) {
        this.fecha_sesion = fecha_sesion;
    }


}
