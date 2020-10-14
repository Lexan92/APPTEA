package roomsqlite.entidades;
/*
* @autor: Oscar Turish
* 20/05/2020
* Esta Clase define la entidad Categoria de Habilidades Cotidianas
* */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName= "cat_habilidad_cotidiana")
public class CategoriaHabCotidiana implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index=true, name="cat_hab_cotidiana_id")
    private int cat_hab_cotidiana_id;

    @NonNull
    private String cat_hab_cotidiana_nombre;
    @NonNull
    private boolean cat_predeterminado;


    @Ignore
    public CategoriaHabCotidiana(int cat_hab_cotidiana_id, @NonNull String cat_hab_cotidiana_nombre, boolean cat_predeterminado) {
        this.cat_hab_cotidiana_id = cat_hab_cotidiana_id;
        this.cat_hab_cotidiana_nombre = cat_hab_cotidiana_nombre;
        this.cat_predeterminado = cat_predeterminado;
    }

    public boolean isCat_predeterminado() {
        return cat_predeterminado;
    }

    public void setCat_predeterminado(boolean cat_predeterminado) {
        this.cat_predeterminado = cat_predeterminado;
    }

    public CategoriaHabCotidiana(String stringExtra) {
    }

    public CategoriaHabCotidiana() {

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

}
