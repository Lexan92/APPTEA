/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.example.apptea.utilidades;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class GenerarNumAleatorio {

    public static int getNumeroAleatorio(){// Metodo para generar numero entero aleatorio

        return (int)(Math.random()* 100000 + 1);//retorna un numero entero de 5 cifras
    }
}
