package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import roomsqlite.database.DateConverter;

@Entity(tableName = "persona_tea", foreignKeys = @ForeignKey(entity = Usuario.class,parentColumns = "usuario_id",childColumns = "usuario_id"))
@TypeConverters(DateConverter.class)
public class PersonaTea {
    @PrimaryKey(autoGenerate = true)
    private int persona_id;
    @NonNull
    private int usuario_id;
    @NonNull
    private String persona_nombre;
    @NonNull
    private String persona_apellido;
   @NonNull
    private Date persona_fecha_nac;
    @NonNull
    private char persona_sexo;
    @NonNull
    private String persona_foto;

// CONSTRUCTOR

    public PersonaTea(int persona_id, int usuario_id, @NonNull String persona_nombre, @NonNull String persona_apellido, @NonNull Date persona_fecha_nac, char persona_sexo, @NonNull String persona_foto) {
        this.persona_id = persona_id;
        this.usuario_id = usuario_id;
        this.persona_nombre = persona_nombre;
        this.persona_apellido = persona_apellido;
        this.persona_fecha_nac = persona_fecha_nac;
        this.persona_sexo = persona_sexo;
        this.persona_foto = persona_foto;
    }


// GET AND SETTER

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    @NonNull
    public String getPersona_foto() {
        return persona_foto;
    }

    public void setPersona_foto(@NonNull String persona_foto) {
        this.persona_foto = persona_foto;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }

    @NonNull
    public String getPersona_nombre() {
        return persona_nombre;
    }

    public void setPersona_nombre(@NonNull String persona_nombre) {
        this.persona_nombre = persona_nombre;
    }

    @NonNull
    public String getPersona_apellido() {
        return persona_apellido;
    }

    public void setPersona_apellido(@NonNull String persona_apellido) {
        this.persona_apellido = persona_apellido;
    }

    @NonNull
    public Date getPersona_fecha_nac() {
        return persona_fecha_nac;
    }

    public void setPersona_fecha_nac(@NonNull Date persona_fecha_nac) {
        this.persona_fecha_nac = persona_fecha_nac;
    }

    public char getPersona_sexo() {
        return persona_sexo;
    }

    public void setPersona_sexo(char persona_sexo) {
        this.persona_sexo = persona_sexo;
    }
}
