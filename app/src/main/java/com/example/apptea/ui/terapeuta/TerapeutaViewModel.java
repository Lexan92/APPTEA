package com.example.apptea.ui.terapeuta;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.airbnb.lottie.L;

import java.util.List;

import roomsqlite.entidades.Terapeuta;
import roomsqlite.repositorios.TerapeutaRepository;

public class TerapeutaViewModel extends AndroidViewModel {
    private TerapeutaRepository terapeutaRepository;
    private LiveData<List<Terapeuta>> terapeutaAll;

    public TerapeutaViewModel(Application application){
        super(application);
        terapeutaRepository = new TerapeutaRepository(application);
        terapeutaAll = terapeutaRepository.getTodosTerapeutas();
    }

    public LiveData<List<Terapeuta>> getTerapeutaAll() {return terapeutaAll; }

    public LiveData<List<Terapeuta>> getAllTerapeutaByPersona(int id){
        return terapeutaRepository.findTerapeutaByPersona(id);
    }

    public void insert (Terapeuta terapeuta){ terapeutaRepository.insert(terapeuta);}

    public void update(Terapeuta terapeuta){ terapeutaRepository.update(terapeuta);}

    public  void delete(Terapeuta terapeuta){ terapeutaRepository.delete(terapeuta);}
}
