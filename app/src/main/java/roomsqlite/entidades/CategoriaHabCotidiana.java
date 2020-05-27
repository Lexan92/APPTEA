package roomsqlite.entidades;
/*
* @autor: Oscar Turish
* 20/05/2020
* Esta Clase define la entidad Categoria de Habilidades Cotidianas
* */

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "cat_habilidad_cotidiana")
public class CategoriaHabCotidiana {
    @PrimaryKey(autoGenerate = true)
    private int cat_hab_cotidiana_id;

    @NonNull
    private String cat_hab_cotidiana_nombre;

   // private String path;//No va aca se mostrara imagen en la clase habilidad cotidiana


    public CategoriaHabCotidiana(int cat_hab_cotidiana_id, @NonNull String cat_hab_cotidiana_nombre/*, String path*/) {
        this.cat_hab_cotidiana_id = cat_hab_cotidiana_id;
        this.cat_hab_cotidiana_nombre = cat_hab_cotidiana_nombre;
       // this.path = path;
    }

    public CategoriaHabCotidiana(String stringExtra) {
    }

    public int getCat_hab_cotidiana_id() {
        return cat_hab_cotidiana_id;
    }

    public void setCat_hab_cotidiana_id(int cat_hab_cotidiana_id) {
        this.cat_hab_cotidiana_id = cat_hab_cotidiana_id;
    }

    @NonNull
    public String getCat_hab_cotidiana_nombre() {
        return cat_hab_cotidiana_nombre;
    }

    public void setCat_hab_cotidiana_nombre(@NonNull String cat_hab_cotidiana_nombre) {
        this.cat_hab_cotidiana_nombre = cat_hab_cotidiana_nombre;
    }

   /* public String getPath() { //Descomentar si al final se utilizara
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }*/
}
