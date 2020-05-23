package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
* AUTOR: ALEX
* 19/MAYO/2020
* CLASE ENTIDAD PARA LA CLASE CATEGORIA JUEGOS
* */
@Entity(tableName = CategoriaJuego.TABLE_NAME)
public class CategoriaJuego {

    public static final String TABLE_NAME = "categoria_juego";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name = "categoria_juego_id")

    private int categoriaJuegoId;
    @ColumnInfo(name = "categoria_juego_nombre")

    private String categoriaJuegoNombre;


    public CategoriaJuego(int categoriaJuegoId,  String categoriaJuegoNombre) {
        this.categoriaJuegoId = categoriaJuegoId;
        this.categoriaJuegoNombre = categoriaJuegoNombre;
    }

    public  int getCategoriaJuegoId() {
        return categoriaJuegoId;
    }

    public void setCategoriaJuegoId(int categoriaJuegoId) {
        this.categoriaJuegoId = categoriaJuegoId;
    }

    public  String getCategoriaJuegoNombre() {
        return categoriaJuegoNombre;
    }

    public void setCategoriaJuegoNombre(  String categoriaJuegoNombre) {
        this.categoriaJuegoNombre = categoriaJuegoNombre;
    }
}
