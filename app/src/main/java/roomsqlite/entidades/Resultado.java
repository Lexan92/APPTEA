package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity (tableName = "resultado", foreignKeys =
            @ForeignKey(entity = Sesion.class, parentColumns = "sesion_id", childColumns = "sesion_id", onDelete=CASCADE, onUpdate = CASCADE))
public class Resultado implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int resultado_id;

    @ColumnInfo(index = true)
    @NonNull
    private int sesion_id;
    @NonNull
    private String nombre_juego;
    private Date hora_juego;

    public Resultado(){}

    @Ignore
    public Resultado(int resultado_id, int sesion_id, @NonNull String nombre_juego, Date hora_juego) {
        this.resultado_id = resultado_id;
        this.sesion_id = sesion_id;
        this.nombre_juego = nombre_juego;
        this.hora_juego = hora_juego;
    }

    public int getResultado_id() {
        return resultado_id;
    }

    public void setResultado_id(int resulado_id) {
        this.resultado_id = resultado_id;
    }

    public int getSesion_id() {
        return sesion_id;
    }

    public void setSesion_id(int sesion_id) {
        this.sesion_id = sesion_id;
    }

    @NonNull
    public String getNombre_juego() {
        return nombre_juego;
    }

    public void setNombre_juego(@NonNull String nombre_juego) {
        this.nombre_juego = nombre_juego;
    }

    public Date getHora_juego() {
        return hora_juego;
    }

    public void setHora_juego(Date hora_juego) {
        this.hora_juego = hora_juego;
    }
}
