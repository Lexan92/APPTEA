package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "rol")
public class Rol {
    @PrimaryKey(autoGenerate = true)
    private int rol_id;
    @NonNull
    private String rol_nombre;
    private boolean rol_is_persona_tea;

    //CONSTRUCTOR
    @Ignore
    public Rol(int rol_id, @NonNull String rol_nombre, boolean rol_is_persona_tea) {
        this.rol_id = rol_id;
        this.rol_nombre = rol_nombre;
        this.rol_is_persona_tea = rol_is_persona_tea;
    }


    //constructor vacio
    public Rol(){};
    // GET AND SETTER


    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    @NonNull
    public String getRol_nombre() {
        return rol_nombre;
    }

    public void setRol_nombre(@NonNull String rol_nombre) {
        this.rol_nombre = rol_nombre;
    }

    public boolean isRol_is_persona_tea() {
        return rol_is_persona_tea;
    }

    public void setRol_is_persona_tea(boolean rol_is_persona_tea) {
        this.rol_is_persona_tea = rol_is_persona_tea;
    }
}
