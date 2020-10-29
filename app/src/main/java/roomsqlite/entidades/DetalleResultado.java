package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="detalle_resultado",foreignKeys = @ForeignKey(entity = Resultado.class, parentColumns = "resultado_id",childColumns = "resultado_id", onDelete = CASCADE, onUpdate = CASCADE))
public class DetalleResultado implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int detalle_resultado_id;
    @ColumnInfo(index = true)
    @NonNull
    private int resultado_id;
    @NonNull
    private String nombre_pregunta;
    private int cantidad_fallos;

    public DetalleResultado(){};

    @Ignore
    public DetalleResultado(int detalle_resultado_id, int resultado_id, @NonNull String nombre_pregunta, int cantidad_fallos) {
        this.detalle_resultado_id = detalle_resultado_id;
        this.resultado_id = resultado_id;
        this.nombre_pregunta = nombre_pregunta;
        this.cantidad_fallos = cantidad_fallos;
    }

    public int getDetalle_resultado_id() {
        return detalle_resultado_id;
    }

    public void setDetalle_resultado_id(int detalle_resultado_id) {
        this.detalle_resultado_id = detalle_resultado_id;
    }

    public int getResultado_id() {
        return resultado_id;
    }

    public void setResultado_id(int resultado_id) {
        this.resultado_id = resultado_id;
    }

    @NonNull
    public String getNombre_pregunta() {
        return nombre_pregunta;
    }

    public void setNombre_pregunta(@NonNull String nombre_pregunta) {
        this.nombre_pregunta = nombre_pregunta;
    }

    public int getCantidad_fallos() {
        return cantidad_fallos;
    }

    public void setCantidad_fallos(int cantidad_fallos) {
        this.cantidad_fallos = cantidad_fallos;
    }
}
