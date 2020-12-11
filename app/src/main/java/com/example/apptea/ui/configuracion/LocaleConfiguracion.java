package com.example.apptea.ui.configuracion;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class LocaleConfiguracion extends Application {
    public static final String TAG = "App";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleHelper.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}
