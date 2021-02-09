package com.teakids.apptea.ui.preguntasFrecuentes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import roomsqlite.entidades.Faq;
import roomsqlite.repositorios.FaqRepository;

public class FaqViewModel extends AndroidViewModel {


    private FaqRepository faqRepository;
    private LiveData<List<Faq>> faqAll;

    public FaqViewModel(@NonNull Application application) {
        super(application);
        faqRepository = new FaqRepository(application);
        faqAll = faqRepository.getTodasFaq();
    }

     public LiveData<List<Faq>> getFaqAll(){
        return faqAll;
    }

    public void insert(Faq faq){
        faqRepository.insert(faq);
    }

    public void update(Faq faq){
       faqRepository.update(faq);
    }

    public void delete(Faq faq){
       faqRepository.delete(faq);
    }

}
