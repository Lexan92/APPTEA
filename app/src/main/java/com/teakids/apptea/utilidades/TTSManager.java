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

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTSManager {
    private TextToSpeech mTts = null;
    private boolean isLoaded = false;
    AdministarSesion idioma ;

    public void init(Context context) {
        idioma = new AdministarSesion(context);
        try {
            mTts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            //ESTABLECIENDO IDIOMA
            Locale spanish = new Locale("es", "ES");
            Locale english = new Locale("en","US");

            if (status == TextToSpeech.SUCCESS) {
                int result;
                if(idioma.getIdioma()==1){
                    result = mTts.setLanguage(spanish);
                }else{
                    result = mTts.setLanguage(english);}

                //mTts.setPitch(1f); //tono de voz
                mTts.setSpeechRate(0.8f); //velocidad de voz

                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "Este Lenguaje no esta permitido");
                }
            } else {
                Log.e("error", "Fallo al Inicializar!");
            }
        }
    };

    public void shutDown() {
        mTts.shutdown();
    }

    public void addQueue(String text) {
        if (isLoaded)
            mTts.speak(text, TextToSpeech.QUEUE_ADD, null);
        else
            Log.e("error", "TTS Not Initialized");
    }

    public void initQueue(String text) {

        if (isLoaded)
            mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        else
            Log.e("error", "TTS Not Initialized");
    }

    public boolean isSpeaking(){
        return mTts.isSpeaking();
    }
}

