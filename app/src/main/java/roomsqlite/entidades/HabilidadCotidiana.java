package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName= "habilidad_cotidiana",
        foreignKeys = @ForeignKey(entity = CategoriaHabCotidiana.class, parentColumns = "cat_hab_cotidiana_id",
                childColumns = "cat_hab_cotidiana_id"))

public class HabilidadCotidiana implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index=true, name="habilidad_cotidiana_id")
    private int habilidad_cotidiana_id;
    @ColumnInfo(index = true)
    @NonNull
    private int cat_hab_cotidiana_id;
    @NonNull
    private String habilidad_cotidiana_nombre;

    private boolean predeterminado;

    @NonNull
    private boolean hab_predeterminado;
    private int pictograma_id;


    public HabilidadCotidiana() {

    }

    @Ignore
    public HabilidadCotidiana(int habilidad_cotidiana_id, int cat_hab_cotidiana_id, @NonNull String habilidad_cotidiana_nombre, boolean hab_predeterminado, int pictograma_id) {
        this.habilidad_cotidiana_id = habilidad_cotidiana_id;
        this.cat_hab_cotidiana_id = cat_hab_cotidiana_id;
        this.habilidad_cotidiana_nombre = habilidad_cotidiana_nombre;
        this.hab_predeterminado = hab_predeterminado;
        this.pictograma_id = pictograma_id;
    }

    public int getHabilidad_cotidiana_id() {
        return habilidad_cotidiana_id;
    }

    public void setHabilidad_cotidiana_id(int habilidad_cotidiana_id) {
        this.habilidad_cotidiana_id = habilidad_cotidiana_id;
    }

    public int getCat_hab_cotidiana_id() {
        return cat_hab_cotidiana_id;
    }

    public void setCat_hab_cotidiana_id(int cat_hab_cotidiana_id) {
        this.cat_hab_cotidiana_id = cat_hab_cotidiana_id;
    }

    @NonNull
    public String getHabilidad_cotidiana_nombre() {
        return habilidad_cotidiana_nombre;
    }

    public void setHabilidad_cotidiana_nombre(@NonNull String habilidad_cotidiana_nombre) {
        this.habilidad_cotidiana_nombre = habilidad_cotidiana_nombre;
    }

    public boolean isHab_predeterminado() {
        return hab_predeterminado;
    }

    public void setHab_predeterminado(boolean hab_predeterminado) {
        this.hab_predeterminado = hab_predeterminado;
    }

    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
    }


    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}
