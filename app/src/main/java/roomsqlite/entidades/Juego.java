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

@Entity(tableName =Juego.TABLE_NAME,foreignKeys = @ForeignKey(entity = CategoriaJuego.class, parentColumns = "categoriaJuegoId", childColumns = "categoria_juego_id", onDelete = CASCADE,onUpdate = CASCADE))
public class Juego {
    public static final String TABLE_NAME = "juego";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    @NonNull
    private int juego_id;
    @NonNull
    private int categoria_juego_id;
    @NonNull
    private String juego_nombre;
    @NonNull
    private Boolean juego_predeterminado;


    public int getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(int juego_id) {
        this.juego_id = juego_id;
    }

    public int getCategoria_juego_id() {
        return categoria_juego_id;
    }

    public void setCategoria_juego_id(int categoria_juego_id) {
        this.categoria_juego_id = categoria_juego_id;
    }

    @NonNull
    public String getJuego_nombre() {
        return juego_nombre;
    }

    public void setJuego_nombre(@NonNull String juego_nombre) {
        this.juego_nombre = juego_nombre;
    }

    @NonNull
    public Boolean getJuego_predeterminado() {
        return juego_predeterminado;
    }

    public void setJuego_predeterminado(@NonNull Boolean juego_predeterminado) {
        this.juego_predeterminado = juego_predeterminado;
    }
}