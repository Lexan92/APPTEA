package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/*@Entity(tableName = "departamento",
        foreignKeys = @ForeignKey(entity = Pais.class, parentColumns = "pais_id", childColumns = "pais_id"))*/

public class Departamento {

   /* @PrimaryKey(autoGenerate = true)
    private int departamento_id;
    @NonNull
    private int pais_id;
    @NonNull
    private String departamento_nombre;

    //CONSTRUCTOR

    public Departamento(int departamento_id, int pais_id, @NonNull String departamento_nombre) {
        this.departamento_id = departamento_id;
        this.pais_id = pais_id;
        this.departamento_nombre = departamento_nombre;
    }

    //GET AND SETTER
    public int getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento_id(int departamento_id) {
        this.departamento_id = departamento_id;
    }

    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }

    @NonNull
    public String getDepartamento_nombre() {
        return departamento_nombre;
    }

    public void setDepartamento_nombre(@NonNull String departamento_nombre) {
        this.departamento_nombre = departamento_nombre;
    }*/
}
