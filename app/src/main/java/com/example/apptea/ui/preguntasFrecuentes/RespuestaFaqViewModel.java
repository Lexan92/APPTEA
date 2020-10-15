package com.example.apptea.ui.preguntasFrecuentes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import roomsqlite.entidades.RespuestaFaq;
import roomsqlite.repositorios.RespuestaFaqRepository;

public class RespuestaFaqViewModel extends AndroidViewModel {

    private RespuestaFaqRepository respuestaFaqRepository;

    public RespuestaFaqViewModel(@NonNull Application application) {
        super(application);
        respuestaFaqRepository = new RespuestaFaqRepository(application);

    }

    public LiveData<List<RespuestaFaq>> getRespuestasFaqAll(int id){
        return respuestaFaqRepository.findRespuestaByFaq(id);
    }

    public void insert(RespuestaFaq respuestaFaq){
       respuestaFaqRepository.insert(respuestaFaq);
    }

    public void update(RespuestaFaq respuestaFaq){
        respuestaFaqRepository.update(respuestaFaq);
    }

    public void delete(RespuestaFaq respuestaFaq){
       respuestaFaqRepository.delete(respuestaFaq);
    }





}
