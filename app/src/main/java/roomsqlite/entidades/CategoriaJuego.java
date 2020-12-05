package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    @ColumnInfo(index = true)
    @NonNull
    private int categoriaJuegoId;
    @NonNull
    private String categoriaJuegoNombre;
    private String categoryNameGame;
    @NonNull
    private boolean predeterminado;

    public CategoriaJuego() {
    }

    @Ignore
    public CategoriaJuego(int categoriaJuegoId, @NonNull String categoriaJuegoNombre, String categoryNameGame, boolean predeterminado) {
        this.categoriaJuegoId = categoriaJuegoId;
        this.categoriaJuegoNombre = categoriaJuegoNombre;
        this.categoryNameGame = categoryNameGame;
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

    public String getCategoryNameGame() {
        return categoryNameGame;
    }

    public void setCategoryNameGame(String categoryNameGame) {
        this.categoryNameGame = categoryNameGame;
    }
}
