package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "respuesta_faq",foreignKeys = @ForeignKey(entity = Faq.class, parentColumns = "faq_id",
        childColumns = "faq_id", onDelete = CASCADE,onUpdate = CASCADE))

public class RespuestaFaq implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name="respuesta_faq_id")
    private int respuesta_faq_id;

    @ColumnInfo(index=true)
    @NonNull
    private int faq_id;

    @NonNull
    private String respuesta;

    @NonNull
    private int orden;

    private String answer;

    @Ignore
    public RespuestaFaq() {
    }

    public RespuestaFaq(int respuesta_faq_id, int faq_id, @NonNull String respuesta, String answer, int orden) {
        this.respuesta_faq_id = respuesta_faq_id;
        this.faq_id = faq_id;
        this.respuesta = respuesta;
        this.answer = answer;
        this.orden = orden;
    }

    public int getRespuesta_faq_id() {
        return respuesta_faq_id;
    }

    public void setRespuesta_faq_id(int respuesta_faq_id) {
        this.respuesta_faq_id = respuesta_faq_id;
    }

    public int getFaq_id() {
        return faq_id;
    }

    public void setFaq_id(int faq_id) {
        this.faq_id = faq_id;
    }

    @NonNull
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(@NonNull String respuesta) {
        this.respuesta = respuesta;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
