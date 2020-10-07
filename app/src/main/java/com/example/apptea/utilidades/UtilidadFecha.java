package com.example.apptea.utilidades;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilidadFecha {

    public static Date obtenerHoraActual(String zonaHoraria) {
        String formato = "HH:mm:ss";
        return UtilidadFecha.obtenerFechaConFormato(formato, zonaHoraria);
    }

    public static Date obtenerFechaActual(String zonaHoraria) {
        String formato = "yyyy-MM-dd";
        return UtilidadFecha.obtenerFechaConFormato(formato, zonaHoraria);
    }


    @SuppressLint("SimpleDateFormat")
    public static Date obtenerFechaConFormato(String formato, String zonaHoraria) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);

        return date;
    }
}
