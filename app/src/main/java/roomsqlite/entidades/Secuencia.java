package roomsqlite.entidades;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "secuencia",foreignKeys = @ForeignKey(entity = HabilidadCotidiana.class,
        parentColumns = "habilidad_cotidiana_id",childColumns = "habilidad_cotidiana_id",
        onDelete = CASCADE, onUpdate = CASCADE))

public class Secuencia {
    @PrimaryKey
    private int secuencia_id;
    @NonNull
    private int secuencia_orden;
    @NonNull
    private int habilidad_cotidiana_id;
    @NonNull
    private int pictograma_id;
/*
    public Secuencia() {
    }*/

    public Secuencia(int secuencia_id, int secuencia_orden, int habilidad_cotidiana_id, int pictograma_id) {
        this.secuencia_id = secuencia_id;
        this.secuencia_orden = secuencia_orden;
        this.habilidad_cotidiana_id = habilidad_cotidiana_id;
        this.pictograma_id = pictograma_id;
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
}
