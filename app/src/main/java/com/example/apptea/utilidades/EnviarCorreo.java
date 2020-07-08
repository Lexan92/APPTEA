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

import android.app.Activity;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.apptea.R;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreo {

    Session session;


      public void Enviar(int codigo,String correo ) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        DesencriptarTexto desencriptarTexto = new DesencriptarTexto();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");


        try {
                session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(desencriptarTexto.getTextoDesencriptado("0W3NiBcvr27E7DIizD5QqBxRZ/T71E0kfEwK8nNkIRo="),
                            desencriptarTexto.getTextoDesencriptado("od3BfR8EOqVlGJiUDGFV4OX45161TuFE"));
                }
            });

            if (session != null) {
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(desencriptarTexto.getTextoDesencriptado("0W3NiBcvr27E7DIizD5QqBxRZ/T71E0kfEwK8nNkIRo=")));
                message.setSubject("Codigo de validacion");
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(correo));
                message.setContent("Tu codigo de validación es: "+codigo, "text/html; charset=utf-8");
                Transport.send(message);
            }

        } catch (Exception e) {
            Logger.getLogger(EnviarCorreo.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
