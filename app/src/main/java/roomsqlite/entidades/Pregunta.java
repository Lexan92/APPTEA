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

@Entity(tableName = Pregunta.TABLE_NAME,foreignKeys = @ForeignKey(entity = Juego.class, parentColumns = "juego_id",childColumns = "juego_id",onDelete = CASCADE,onUpdate = CASCADE))
public class Pregunta {
    public static final String TABLE_NAME = "pregunta";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    private int pregunta_id;
    @ColumnInfo(index = true)
    private int juego_id;
    @NonNull
    private String titulo_pregunta;

    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public int getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(int juego_id) {
        this.juego_id = juego_id;
    }

    @NonNull
    public String getTitulo_pregunta() {
        return titulo_pregunta;
    }

    public void setTitulo_pregunta(@NonNull String titulo_pregunta) {
        this.titulo_pregunta = titulo_pregunta;
    }
}
