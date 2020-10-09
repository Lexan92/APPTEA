package com.example.apptea.utilidades;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UtilidadFecha {

    @SuppressLint("SimpleDateFormat")
    public static String obtenerHora(Date date) {
        String formato = "HH:mm:ss";
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String obtenerFecha(Date date) {
        String formato = "dd-MM-yyyy";
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    public static Date obtenerFechaHoraActual() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }
}
