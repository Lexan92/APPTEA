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

import java.io.Serializable;


@Entity(tableName= "pictograma",
        foreignKeys = @ForeignKey(entity = CategoriaPictograma.class, parentColumns = "cat_pictograma_id", childColumns = "cat_pictograma_id"))



//@Entity(tableName = Pictograma.TABLE_NAME)

public class Pictograma implements Serializable {
    public static final String TABLE_NAME="pictograma";

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (index = true, name="pictograma_id")
    @NonNull
    private int pictograma_id;
    @NonNull
    private int cat_pictograma_id;

    @ColumnInfo (name="pictograma_nombre")
    @NonNull
    private String pictograma_nombre;

    private String pictograma_imagen;
    private String pictograma_sonido;
    private boolean predeterminado;

//CONSTRUCTOR

/*
    public Pictograma(int pictograma_id, int categoria_pictograma_id, @NonNull String pictograma_nombre, String pictograma_imagen, String pictograma_sonido, int pictograma_ban) {
        this.pictograma_id = pictograma_id;
        this.categoria_pictograma_id = categoria_pictograma_id;
        this.pictograma_nombre = pictograma_nombre;
        this.pictograma_imagen = pictograma_imagen;
        this.pictograma_sonido = pictograma_sonido;
        this.pictograma_ban = pictograma_ban;
    }*/


    public Pictograma(@NonNull int pictograma_id, int cat_pictograma_id, @NonNull String pictograma_nombre, @NonNull boolean predeterminado) {
        this.pictograma_id = pictograma_id;
        this.cat_pictograma_id = cat_pictograma_id;
        this.pictograma_nombre = pictograma_nombre;
        this.predeterminado = predeterminado;
    }


    //GETE Y SET


    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
    }

    public int getCat_pictograma_id() {
        return cat_pictograma_id;
    }

    public void setCat_pictograma_id(int cat_pictograma_id) {
        this.cat_pictograma_id = cat_pictograma_id;
    }

    @NonNull
    public String getPictograma_nombre() {
        return pictograma_nombre;
    }

    public void setPictograma_nombre(@NonNull String pictograma_nombre) {
        this.pictograma_nombre = pictograma_nombre;
    }

    public String getPictograma_imagen() {
        return pictograma_imagen;
    }

    public void setPictograma_imagen(String pictograma_imagen) {
        this.pictograma_imagen = pictograma_imagen;
    }

    public String getPictograma_sonido() {
        return pictograma_sonido;
    }

    public void setPictograma_sonido(String pictograma_sonido) {
        this.pictograma_sonido = pictograma_sonido;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}



