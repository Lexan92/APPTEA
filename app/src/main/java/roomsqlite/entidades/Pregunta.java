/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package roomsqlite.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Pregunta.TABLE_NAME, foreignKeys = @ForeignKey(entity = Juego.class, parentColumns = "juego_id", childColumns = "juego_id", onDelete = CASCADE, onUpdate = CASCADE))
public class Pregunta implements Parcelable {
    public static final String TABLE_NAME = "pregunta";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    private int pregunta_id;
    @ColumnInfo(index = true)
    private int juego_id;
    @NonNull
    private String titulo_pregunta;


    @Ignore
    public Pregunta(int pregunta_id, int juego_id, @NonNull String titulo_pregunta) {
        this.pregunta_id = pregunta_id;
        this.juego_id = juego_id;
        this.titulo_pregunta = titulo_pregunta;
    }


    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public int getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(int juego_id) {
        this.juego_id = juego_id;
    }

    @NonNull
    public String getTitulo_pregunta() {
        return titulo_pregunta;
    }

    public void setTitulo_pregunta(@NonNull String titulo_pregunta) {
        this.titulo_pregunta = titulo_pregunta;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pregunta_id);
        dest.writeInt(this.juego_id);
        dest.writeString(this.titulo_pregunta);
    }

    @Ignore
    private Pregunta(Parcel in) {
        this.pregunta_id = in.readInt();
        this.juego_id = in.readInt();
        this.titulo_pregunta = in.readString();
    }


    public Pregunta() {
    }


    @Ignore
    public static final Parcelable.Creator<Pregunta> CREATOR = new Parcelable.Creator<Pregunta>() {

        @Override

        public Pregunta createFromParcel(Parcel source) {
            return new Pregunta(source);
        }

        @Override

        public Pregunta[] newArray(int size) {
            return new Pregunta[size];
        }
    };
}
