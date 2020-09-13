/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package roomsqlite.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Opcion.TABLE_NAME,
        foreignKeys = {@ForeignKey(entity = Pregunta.class, parentColumns = "pregunta_id", childColumns = "pregunta_id", onDelete = CASCADE, onUpdate = CASCADE),
                @ForeignKey(entity = Pictograma.class, parentColumns = "pictograma_id", childColumns = "pictograma_id", onDelete = CASCADE, onUpdate = CASCADE)})

public class Opcion {
    public static final String TABLE_NAME = "opcion";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    private int opcion_id;
    @ColumnInfo(index = true)
    private int pregunta_id;
    @ColumnInfo(index = true)
    private int pictograma_id;

    private boolean opcion_respuesta;

    public Opcion(int opcion_id, int pregunta_id, int pictograma_id, boolean opcion_respuesta) {
        this.opcion_id = opcion_id;
        this.pregunta_id = pregunta_id;
        this.pictograma_id = pictograma_id;
        this.opcion_respuesta = opcion_respuesta;
    }

    @Ignore
    public Opcion() {

    }

    public int getOpcion_id() {
        return opcion_id;
    }

    public void setOpcion_id(int opcion_id) {
        this.opcion_id = opcion_id;
    }

    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public boolean isOpcion_respuesta() {
        return opcion_respuesta;
    }

    public void setOpcion_respuesta(boolean opcion_respuesta) {
        this.opcion_respuesta = opcion_respuesta;
    }

    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
    }
}
