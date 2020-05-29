package roomsqlite.entidades;
/*
 * AUTOR: GUADALUPE
 * CLASE ENTIDAD PARACTEGORIA DE PICTOGRAMA
 * */
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= CategoriaPictograma.TABLE_NAME)
public class CategoriaPictograma {

    public static final String TABLE_NAME="categoria_pictograma";

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (index = true, name="categoria_pictograma_id")
    @NonNull
    private int cat_pictograma_id;

    @ColumnInfo(name="categoria_pictograma_nombre")
    @NonNull
    private String cat_pictograma_nombre;

   // private int cat_pictorgrama_ban;



//CONSTRUCTOR

    public CategoriaPictograma(@NonNull int cat_pictograma_id, @NonNull String cat_pictograma_nombre) {
        this.cat_pictograma_id = cat_pictograma_id;
        this.cat_pictograma_nombre = cat_pictograma_nombre;
    }


    //GET Y SET
    @NonNull
    public int getCat_pictograma_id() {
        return cat_pictograma_id;
    }

    public void setCat_pictograma_id(@NonNull int cat_pictograma_id) {
        this.cat_pictograma_id = cat_pictograma_id;
    }

    @NonNull
    public String getCat_pictograma_nombre() {
        return cat_pictograma_nombre;
    }

    public void setCat_pictograma_nombre(@NonNull String cat_pictograma_nombre) {
        this.cat_pictograma_nombre = cat_pictograma_nombre;
    }



    /*public int getCat_pictorgrama_ban() {
        return cat_pictorgrama_ban;
    }

    public void setCat_pictorgrama_ban(int cat_pictorgrama_ban) {
        this.cat_pictorgrama_ban = cat_pictorgrama_ban;
    }*/
}
