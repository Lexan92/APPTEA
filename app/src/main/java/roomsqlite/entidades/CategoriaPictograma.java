package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;



@Entity(tableName = CategoriaPictograma.TABLE_NAME)

public class CategoriaPictograma implements Serializable {

    public static final String TABLE_NAME = "categoria_pictograma";

    /*@PrimaryKey (autoGenerate = true)
    @ColumnInfo (index = true, name="cat_pictograma_id")
    @NonNull*/
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(index = true,name = "cat_pictograma_id")
    @NonNull
    private int cat_pictograma_id;


    @NonNull
    private String cat_pictograma_nombre;

   // private int cat_pictorgrama_ban;

    @NonNull
    @ColumnInfo(name = "predeterminado")
    private boolean predeterminado;

    private int pictograma_id;

public CategoriaPictograma(){

}

    @Ignore
    public CategoriaPictograma(int cat_pictograma_id, @NonNull String cat_pictograma_nombre, boolean predeterminado) {
        this.cat_pictograma_id = cat_pictograma_id;
        this.cat_pictograma_nombre = cat_pictograma_nombre;
        this.predeterminado = predeterminado;
    }

    //CONSTRUCTOR
    @Ignore
    public CategoriaPictograma(int cat_pictograma_id, @NonNull String cat_pictograma_nombre, boolean predeterminado, int pictograma_id) {
        this.cat_pictograma_id = cat_pictograma_id;
        this.cat_pictograma_nombre = cat_pictograma_nombre;
        this.predeterminado = predeterminado;
        this.pictograma_id = pictograma_id;
    }

    //GET Y SET

    public int getCat_pictograma_id() {
        return cat_pictograma_id;
    }

    public void setCat_pictograma_id(int cat_pictograma_id) {
        this.cat_pictograma_id = cat_pictograma_id;
    }

    @NonNull
    public String getCat_pictograma_nombre() {
        return cat_pictograma_nombre;
    }

    public void setCat_pictograma_nombre(@NonNull String cat_pictograma_nombre) {
        this.cat_pictograma_nombre = cat_pictograma_nombre;
    }
    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
    }
}
