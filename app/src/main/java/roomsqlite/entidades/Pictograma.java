package roomsqlite.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "pictograma")
public class Pictograma {

    @PrimaryKey
    private int pictograma_id;

    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
    }
}
