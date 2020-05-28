package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName= "usuario",
        foreignKeys = @ForeignKey(entity = Pais.class, parentColumns = "pais_id", childColumns = "pais_id"))
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int usuario_id;
    @NonNull
    private int pais_id;
    @NonNull
    private String usuario_nombre;
    @NonNull
    private String usuario_apellido;
    @NonNull
    private String contrasenia;
    @NonNull
    private String correo;
    @NonNull
    private int telefono;
    @NonNull
    private  String direccion;
    @NonNull
    private int codigo_verificacion;

//CONSTRUCTOR

    public Usuario(int usuario_id, int pais_id, @NonNull String usuario_nombre, @NonNull String usuario_apellido, @NonNull String contrasenia, @NonNull String correo, int telefono, @NonNull String direccion, int codigo_verificacion) {
        this.usuario_id = usuario_id;
        this.pais_id = pais_id;
        this.usuario_nombre = usuario_nombre;
        this.usuario_apellido = usuario_apellido;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.codigo_verificacion = codigo_verificacion;
    }

    public Usuario(){}

//GET AND SETTER

    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }

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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NonNull String direccion) {
        this.direccion = direccion;
    }

    public int getCodigo_verificacion() {
        return codigo_verificacion;
    }

    public void setCodigo_verificacion(int codigo_verificacion) {
        this.codigo_verificacion = codigo_verificacion;
    }


}
