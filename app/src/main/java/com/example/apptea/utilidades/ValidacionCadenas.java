package com.example.apptea.utilidades;
import android.util.Log;
import org.apache.commons.text.WordUtils;

public class ValidacionCadenas {


    //funcion valida tamaño de una cadena, tomando espacios
    //parametro de entrada, cadena y tamaño a evaluar
    //retorna true si excede el tamaño de entrada y false caso contrario
    public static boolean validarTamaño(String cadena, int tamaño){
        boolean bandera = false;

        if (cadena.length()>tamaño){
            bandera = true;
        }
        return bandera;
    }


    //funcion capitaliza una cadena
    public static String capitalizaCadena(String cadena){
        String cadenaRetorno = null;
        cadenaRetorno = WordUtils.capitalizeFully(cadena);
        Log.d("cadena",cadenaRetorno);
        return cadenaRetorno;
    }
}
