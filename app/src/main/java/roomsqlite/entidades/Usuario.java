package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
@Entity(tableName= "usuario",
        foreignKeys = @ForeignKey(entity = Rol.class, parentColumns = "rol_id", childColumns = "rol_id"))
public class Usuario implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int usuario_id;
    @ColumnInfo(index = true)
    @NonNull
    private int rol_id;
    @NonNull
    private String usuario_nombre;
    @NonNull
    private String usuario_apellido;
    @NonNull
    private String contrasenia;
    @NonNull
    private String correo;

   /* @NonNull
    private int telefono;
    @NonNull
    private  String direccion;*/

    //CONSTRUCTOR
    @Ignore
    public Usuario(int usuario_id, int rol_id, @NonNull String usuario_nombre, @NonNull String usuario_apellido, @NonNull String contrasenia, @NonNull String correo) {
        this.usuario_id = usuario_id;
        this.rol_id = rol_id;
        this.usuario_nombre = usuario_nombre;
        this.usuario_apellido = usuario_apellido;
        this.contrasenia = contrasenia;
        this.correo = correo;
    }




    public Usuario(){}

//GET AND SETTER

   public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    @NonNull
    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(@NonNull String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    @NonNull
    public String getUsuario_apellido() {
        return usuario_apellido;
    }

    public void setUsuario_apellido(@NonNull String usuario_apellido) {
        this.usuario_apellido = usuario_apellido;
    }

    @NonNull
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(@NonNull String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @NonNull
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(@NonNull String correo) {
        this.correo = correo;
    }

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }
}
