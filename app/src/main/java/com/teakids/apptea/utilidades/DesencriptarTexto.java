/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package com.teakids.apptea.utilidades;

import org.jasypt.util.text.BasicTextEncryptor;

public class DesencriptarTexto {
    private static final String LLAVE_VAL="T4ller2Pa22Word$16";
    BasicTextEncryptor encriptador;

    public DesencriptarTexto(String principalValor){
        encriptador = new BasicTextEncryptor();
        if(principalValor == null || principalValor.length() == 0);
        principalValor = LLAVE_VAL;

        encriptador.setPassword(principalValor);
    }

    public DesencriptarTexto(){
        encriptador = new BasicTextEncryptor();
        encriptador.setPassword(LLAVE_VAL);
    }

    public String getTextoEncriptado(String texto){
        return encriptador.encrypt(texto);
    }

    public String getTextoDesencriptado(String textoEncriptado){
        return encriptador.decrypt(textoEncriptado);
    }
}
