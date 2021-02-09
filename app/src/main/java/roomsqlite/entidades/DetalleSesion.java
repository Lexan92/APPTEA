package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import roomsqlite.database.DateConverter;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = DetalleSesion.NOMBRE_TABLA,foreignKeys = @ForeignKey(entity = Sesion.class, parentColumns = "sesion_id", childColumns = "sesion_id", onDelete = CASCADE, onUpdate = CASCADE))
@TypeConverters(DateConverter.class)
public class DetalleSesion {

    public static final String NOMBRE_TABLA = "detalle_sesion";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    private int detalle_sesion_id;
    @ColumnInfo(index = true)
    private int sesion_id;
    @NonNull
    private String nombre_opcion;
    @NonNull
    private Date hora_inicio;

    public DetalleSesion() {
        nombre_opcion = null;
        hora_inicio = null;
    }

    @Ignore
    public DetalleSesion(int detalle_sesion_id, int sesion_id, @NonNull String nombre_opcion, @NonNull Date hora_inicio) {
        this.detalle_sesion_id = detalle_sesion_id;
        this.sesion_id = sesion_id;
        this.nombre_opcion = nombre_opcion;
        this.hora_inicio = hora_inicio;
    }

    public int getDetalle_sesion_id() {
        return detalle_sesion_id;
    }

    public void setDetalle_sesion_id(int detalle_sesion_id) {
        this.detalle_sesion_id = detalle_sesion_id;
    }

    public int getSesion_id() {
        return sesion_id;
    }

    public void setSesion_id(int sesion_id) {
        this.sesion_id = sesion_id;
    }

    @NonNull
    public String getNombre_opcion() {
        return nombre_opcion;
    }

    public void setNombre_opcion(@NonNull String nombre_opcion) {
        this.nombre_opcion = nombre_opcion;
    }

    @NonNull
    public Date getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(@NonNull Date hora_inicio) {
        this.hora_inicio = hora_inicio;
    }
}
