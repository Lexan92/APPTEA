package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*
* AUTOR: ALEX
* 19/MAYO/2020
* CLASE ENTIDAD PARA LA CLASE CATEGORIA JUEGOS
* */
@Entity(tableName = CategoriaJuego.TABLE_NAME)
public class CategoriaJuego implements Serializable {

    public static final String TABLE_NAME = "categoria_juego";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name = "categoria_juego_id")
    @NonNull
    private int categoriaJuegoId;
    @ColumnInfo(name = "categoria_juego_nombre")
    @NonNull
    private String categoriaJuegoNombre;

    @NonNull
    @ColumnInfo(name = "predeterminado")
    private boolean predeterminado;

    public CategoriaJuego() {
    }

    public CategoriaJuego(@NonNull int categoriaJuegoId, @NonNull  String categoriaJuegoNombre, @NonNull boolean predeterminado) {
        this.categoriaJuegoId = categoriaJuegoId;
        this.categoriaJuegoNombre = categoriaJuegoNombre;
        this.predeterminado = predeterminado;
    }



    @NonNull
    public  int getCategoriaJuegoId() {
        return categoriaJuegoId;
    }

    public void setCategoriaJuegoId( @NonNull int categoriaJuegoId) {
        this.categoriaJuegoId = categoriaJuegoId;
    }

    @NonNull
    public  String getCategoriaJuegoNombre() {
        return categoriaJuegoNombre;
    }

    public void setCategoriaJuegoNombre( @NonNull String categoriaJuegoNombre) {
        this.categoriaJuegoNombre = categoriaJuegoNombre;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}
