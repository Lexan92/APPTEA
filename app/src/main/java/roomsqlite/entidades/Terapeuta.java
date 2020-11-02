package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "terapeuta", foreignKeys = @ForeignKey(entity = PersonaTea.class,parentColumns = "persona_id",childColumns = "persona_id" ,onDelete=CASCADE, onUpdate = CASCADE))
public class Terapeuta implements Serializable {
    @PrimaryKey (autoGenerate = true)
    private int terapeuta_id;
    @ColumnInfo(index = true)
    @NonNull
    private int persona_id;
    @NonNull
    private String terapeuta_nombre;
    @NonNull
    private String terapeuta_apellido;
    private String terapeuta_telefono;
    private String terapeuta_correo;

    //CONTRUCTOR
    public Terapeuta(){}

    @Ignore
    public Terapeuta(int terapeuta_id, int persona_id, @NonNull String terapeuta_nombre, @NonNull String terapeuta_apellido, String terapeuta_telefono, String terapeuta_correo) {
        this.terapeuta_id = terapeuta_id;
        this.persona_id = persona_id;
        this.terapeuta_nombre = terapeuta_nombre;
        this.terapeuta_apellido = terapeuta_apellido;
        this.terapeuta_telefono = terapeuta_telefono;
        this.terapeuta_correo = terapeuta_correo;
    }

    //GET AND SETTER

    public int getTerapeuta_id() {
        return terapeuta_id;
    }

    public void setTerapeuta_id(int terapeuta_id) {
        this.terapeuta_id = terapeuta_id;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }

    @NonNull
    public String getTerapeuta_nombre() {
        return terapeuta_nombre;
    }

    public void setTerapeuta_nombre(@NonNull String terapeuta_nombre) {
        this.terapeuta_nombre = terapeuta_nombre;
    }

    @NonNull
    public String getTerapeuta_apellido() {
        return terapeuta_apellido;
    }

    public void setTerapeuta_apellido(@NonNull String terapeuta_apellido) {
        this.terapeuta_apellido = terapeuta_apellido;
    }

    public String getTerapeuta_telefono() {
        return terapeuta_telefono;
    }

    public void setTerapeuta_telefono(String terapeuta_telefono) {
        this.terapeuta_telefono = terapeuta_telefono;
    }

    public String getTerapeuta_correo() {
        return terapeuta_correo;
    }

    public void setTerapeuta_correo(String terapeuta_correo) {
        this.terapeuta_correo = terapeuta_correo;
    }
}
