package roomsqlite.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "catalogo_pictograma")
public class CatalogoPictograma {

    @PrimaryKey
    private int cat_pictograma_id;

    public int getCat_pictograma_id() {
        return cat_pictograma_id;
    }

    public void setCat_pictograma_id(int cat_pictograma_id) {
        this.cat_pictograma_id = cat_pictograma_id;
    }
}
