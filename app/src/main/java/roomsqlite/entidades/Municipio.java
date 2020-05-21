package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "municipio",
        foreignKeys = @ForeignKey(entity = Departamento.class, parentColumns = "departamento_id", childColumns = "departamento_id"))

public class Municipio {
    @PrimaryKey (autoGenerate = true)
    private int municipio_id;
    @NonNull
    private int departamento_id;
    @NonNull
    private String municipio_nombre;

    //CONSTRUCTOR

    public Municipio(int municipio_id, int departamento_id, @NonNull String municipio_nombre) {
        this.municipio_id = municipio_id;
        this.departamento_id = departamento_id;
        this.municipio_nombre = municipio_nombre;
    }

    //GET AND SETTER

    public int getMunicipio_id() {
        return municipio_id;
    }

    public void setMunicipio_id(int municipio_id) {
        this.municipio_id = municipio_id;
    }

    public int getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento_id(int departamento_id) {
        this.departamento_id = departamento_id;
    }

    @NonNull
    public String getMunicipio_nombre() {
        return municipio_nombre;
    }

    public void setMunicipio_nombre(@NonNull String municipio_nombre) {
        this.municipio_nombre = municipio_nombre;
    }
}
