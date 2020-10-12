package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity (tableName = "resultado", foreignKeys = @ForeignKey(entity = Pregunta.class, parentColumns = "pregunta_id", childColumns = "pregunta_id"))
public class Resultado implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int resulado_id;
    @ColumnInfo(index = true)
    @NonNull
    private int pregunta_id;
    @NonNull
    private int contador_fallo;

    public Resultado(){}
    @Ignore
    public Resultado(int resulado_id, int pregunta_id, int contador_fallo) {
        this.resulado_id = resulado_id;
        this.pregunta_id = pregunta_id;
        this.contador_fallo = contador_fallo;
    }

    public int getResulado_id() {
        return resulado_id;
    }

    public void setResulado_id(int resulado_id) {
        this.resulado_id = resulado_id;
    }

    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public int getContador_fallo() {
        return contador_fallo;
    }

    public void setContador_fallo(int contador_fallo) {
        this.contador_fallo = contador_fallo;
    }
}
