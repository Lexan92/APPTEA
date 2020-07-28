package roomsqlite.entidades;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "secuencia",
        foreignKeys = { @ForeignKey(entity = HabilidadCotidiana.class, parentColumns = "habilidad_cotidiana_id",childColumns = "habilidad_cotidiana_id", onDelete = CASCADE, onUpdate = CASCADE),
                        @ForeignKey(entity = Pictograma.class, parentColumns = "pictograma_id", childColumns = "pictograma_id",onDelete = CASCADE,onUpdate = CASCADE)})

public class Secuencia implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "secuencia_id")
    private int secuencia_id;
    @NonNull
    private int secuencia_orden;
    @ColumnInfo(index = true)
    @NonNull
    private int habilidad_cotidiana_id;
    @NonNull
    @ColumnInfo(index = true)
    private int pictograma_id;
    @NonNull
    private boolean sec_predeterminado;
/*
    public Secuencia() {
    }*/

    public Secuencia(int secuencia_id, int secuencia_orden, int habilidad_cotidiana_id, int pictograma_id, boolean sec_predeterminado) {
        this.secuencia_id = secuencia_id;
        this.secuencia_orden = secuencia_orden;
        this.habilidad_cotidiana_id = habilidad_cotidiana_id;
        this.pictograma_id = pictograma_id;
        this.sec_predeterminado = sec_predeterminado;
    }

    public int getSecuencia_id() {
        return secuencia_id;
    }

    public void setSecuencia_id(int secuencia_id) {
        this.secuencia_id = secuencia_id;
    }

    public int getSecuencia_orden() {
        return secuencia_orden;
    }

    public void setSecuencia_orden(int secuencia_orden) {
        this.secuencia_orden = secuencia_orden;
    }

    public int getHabilidad_cotidiana_id() {
        return habilidad_cotidiana_id;
    }

    public void setHabilidad_cotidiana_id(int habilidad_cotidiana_id) {
        this.habilidad_cotidiana_id = habilidad_cotidiana_id;
    }

    public int getPictograma_id() {
        return pictograma_id;
    }

    public void setPictograma_id(int pictograma_id) {
        this.pictograma_id = pictograma_id;
    }

    public boolean isSec_predeterminado() {
        return sec_predeterminado;
    }

    public void setSec_predeterminado(boolean sec_predeterminado) {
        this.sec_predeterminado = sec_predeterminado;
    }
}
