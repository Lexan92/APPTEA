package roomsqlite.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "faq")
public class Faq implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name = "faq_id")
    private int faq_id;
    @NonNull
    private String faq_info;

    private String faq_question;


    @Ignore
    public Faq() {
    }


    public Faq(int faq_id, @NonNull String faq_info, String faq_question) {
        this.faq_id = faq_id;
        this.faq_info = faq_info;
        this.faq_question = faq_question;
    }

    public int getFaq_id() {
        return faq_id;
    }

    public void setFaq_id(int faq_id) {
        this.faq_id = faq_id;
    }

    @NonNull
    public String getFaq_info() {
        return faq_info;
    }

    public void setFaq_info(@NonNull String faq_info) {
        this.faq_info = faq_info;
    }

    public String getFaq_question() {
        return faq_question;
    }

    public void setFaq_question(String faq_question) {
        this.faq_question = faq_question;
    }
}
