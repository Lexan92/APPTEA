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
    @NonNull
    private int categoriaJuegoId;
    @ColumnInfo(name = "categoria_juego_nombre")
    @NonNull
    private String categoriaJuegoNombre;


    public CategoriaJuego(@NonNull int categoriaJuegoId,@NonNull  String categoriaJuegoNombre) {
        this.categoriaJuegoId = categoriaJuegoId;
        this.categoriaJuegoNombre = categoriaJuegoNombre;
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
}
