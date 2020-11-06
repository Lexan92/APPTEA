package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import roomsqlite.database.DateConverter;

@Entity(tableName = Sesion.NOMBRE_TABLA)
@TypeConverters(DateConverter.class)
public class Sesion implements Serializable {
    public static final String NOMBRE_TABLA="sesion";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    @NonNull
    private int sesion_id;
    @NonNull
    @ColumnInfo(index = true)
    private int persona_id;
    @NonNull
    private Date inicio_sesion;
    @NonNull
    private Date fin_sesion;

    private String comentario;

    public Sesion() {
    }

    @Ignore
    public Sesion(int sesion_id, int persona_id, @NonNull Date fecha_sesion,Date fin_sesion ,String comentario) {
        this.sesion_id = sesion_id;
        this.persona_id = persona_id;
        this.inicio_sesion = fecha_sesion;
        this.fin_sesion = fin_sesion;
        this.comentario = comentario;

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
    public Date getInicio_sesion() {
        return inicio_sesion;
    }

    public void setInicio_sesion(@NonNull Date inicio_sesion) {
        this.inicio_sesion = inicio_sesion;
    }

    @NonNull
    public Date getFin_sesion() {
        return fin_sesion;
    }

    public void setFin_sesion(@NonNull Date fin_sesion) {
        this.fin_sesion = fin_sesion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
