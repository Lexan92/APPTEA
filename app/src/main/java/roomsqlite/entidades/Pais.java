package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pais")
public class Pais {
    @PrimaryKey(autoGenerate = true)
    private int pais_id;
    @NonNull
    private String pais_nombre;

    //CONSTRUCTOR

    public Pais(int pais_id, @NonNull String pais_nombre) {
        this.pais_id = pais_id;
        this.pais_nombre = pais_nombre;
    }

    //constructor vacio
    public Pais(){};
    // GET AND SETTER
    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }

    @NonNull
    public String getPais_nombre() {
        return pais_nombre;
    }

    public void setPais_nombre(@NonNull String pais_nombre) {
        this.pais_nombre = pais_nombre;
    }

    @Override
    public String toString() {
        return pais_nombre;
    }


}
