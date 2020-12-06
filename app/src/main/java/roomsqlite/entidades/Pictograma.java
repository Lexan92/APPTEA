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
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName= "pictograma",
        foreignKeys = @ForeignKey(entity = CategoriaPictograma.class, parentColumns = "cat_pictograma_id", childColumns = "cat_pictograma_id", onDelete = CASCADE, onUpdate = CASCADE))



//@Entity(tableName = Pictograma.TABLE_NAME)

public class Pictograma implements Serializable {
    public static final String TABLE_NAME="pictograma";

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (index = true, name="pictograma_id")
    @NonNull
    private int pictograma_id;
    @ColumnInfo(index = true)
    @NonNull
    private int cat_pictograma_id;

    @ColumnInfo (name="pictograma_nombre")
    @NonNull
    private String pictograma_nombre;
    private String pictograma_name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] pictograma_imagen;
    private boolean predeterminado;

//CONSTRUCTOR

    @Ignore
    public Pictograma(int pictograma_id, int cat_pictograma_id, @NonNull String pictograma_nombre, String pictograma_name, byte[] pictograma_imagen, boolean predeterminado) {
        this.pictograma_id = pictograma_id;
        this.cat_pictograma_id = cat_pictograma_id;
        this.pictograma_nombre = pictograma_nombre;
        this.pictograma_name = pictograma_name;
        this.pictograma_imagen = pictograma_imagen;
        this.predeterminado = predeterminado;
    }

    public Pictograma() {

    }


    //GET Y SET


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

    public byte[] getPictograma_imagen() {
        return pictograma_imagen;
    }

    public void setPictograma_imagen(byte[] pictograma_imagen) {
        this.pictograma_imagen = pictograma_imagen;
    }


    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public String getPictograma_name() {
        return pictograma_name;
    }

    public void setPictograma_name(String pictograma_name) {
        this.pictograma_name = pictograma_name;
    }
}



