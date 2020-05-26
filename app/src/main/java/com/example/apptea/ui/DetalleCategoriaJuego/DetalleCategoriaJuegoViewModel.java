/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.ui.DetalleCategoriaJuego;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetalleCategoriaJuegoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DetalleCategoriaJuegoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Opcion en construcción");
    }

    LiveData<String> getText() {
        return mText;
    }
}
