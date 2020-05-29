package roomsqlite.entidades;

/*
 * AUTOR: GUADALUPE
 * 19/MAYO/2020
 * CLASE ENTIDAD PARA PICTOGRAMA
 * */

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.NonParcelField;

@Entity(tableName= "pictograma")
public class Pictograma {

    @PrimaryKey (autoGenerate = true)
    private int pictograma_id;

    @NonNull
    private String pictograma_nombre;
    private String pictograma_imagen;
    private String pictograma_sonido;
    private int pictograma_ban;


//CONSTRUCTOR


    /*public Pictograma(int pictograma_id, @NonNull String pictograma_nombre, String pictograma_imagen, String pictograma_sonido) {
        this.pictograma_id = pictograma_id;
        this.pictograma_nombre = pictograma_nombre;
        this.pictograma_imagen = pictograma_imagen;
        this.pictograma_sonido = pictograma_sonido;
    }

     */

    public Pictograma(int pictograma_id, @NonNull String pictograma_nombre) {
        this.pictograma_id = pictograma_id;
        this.pictograma_nombre = pictograma_nombre;
    }


    //GET AND SET

    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
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

    public int getPictograma_ban() {
        return pictograma_ban;
    }

    public void setPictograma_ban(int pictograma_ban) {
        this.pictograma_ban = pictograma_ban;
    }
}
