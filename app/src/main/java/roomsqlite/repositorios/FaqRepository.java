package roomsqlite.repositorios;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomsqlite.dao.FaqDao;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.Faq;

public class FaqRepository {

    private FaqDao  faqDao;
    private LiveData<List<Faq>> faqAll;

    public FaqRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        faqDao = db.faqDao();
        faqAll = faqDao.getAllFaq();
    }

    public LiveData<List<Faq>> getTodasFaq(){
        return faqAll;
    }

    public void insert(Faq faq){
        appDatabase.databaseWriteExecutor.execute(()-> faqDao.insert(faq));
    }

    public void update(Faq faq){
        appDatabase.databaseWriteExecutor.execute(()-> faqDao.updateFaq(faq));
    }

    public void delete(Faq faq){
        appDatabase.databaseWriteExecutor.execute(()-> faqDao.deleteFaq(faq));
    }




}
