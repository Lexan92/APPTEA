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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Opcion.TABLE_NAME,foreignKeys = @ForeignKey(entity = Pregunta.class,parentColumns = "pregunta_id",childColumns = "pregunta_id",onDelete = CASCADE, onUpdate = CASCADE))
public class Opcion {
    public static final String TABLE_NAME = "opcion";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    @NonNull
    private int opcion_id;
    @NonNull
    private int pregunta_id;
    @NonNull
    private boolean opcion_respuesta;

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
}
