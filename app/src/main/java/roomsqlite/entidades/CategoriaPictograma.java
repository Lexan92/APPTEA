package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;



@Entity(tableName = CategoriaPictograma.TABLE_NAME)

public class CategoriaPictograma implements Serializable {

    public static final String TABLE_NAME = "CategoriaPictograma";

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

public CategoriaPictograma(){

}

//CONSTRUCTOR

    @Ignore
    public CategoriaPictograma(@NonNull int cat_pictograma_id, @NonNull String cat_pictograma_nombre, boolean predeterminado) {
        this.cat_pictograma_id = cat_pictograma_id;
        this.cat_pictograma_nombre = cat_pictograma_nombre;
        this.predeterminado = predeterminado;
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


    /*public int getCat_pictorgrama_ban() {
        return cat_pictorgrama_ban;
    }

    public void setCat_pictorgrama_ban(int cat_pictorgrama_ban) {
        this.cat_pictorgrama_ban = cat_pictorgrama_ban;
    }*/
}
