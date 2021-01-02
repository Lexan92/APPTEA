package com.example.apptea.ui.configuracion;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.example.apptea.utilidades.AdministarSesion;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.N;

public class LocaleHelper {

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context);
        return setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = getPersistedData(context);
        return setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context);
    }

    private static String getPersistedData(Context context) {
        AdministarSesion administarSesion = new AdministarSesion(context);
        String idioma;
        if(administarSesion.getIdioma()==-1){
            idioma = Locale.getDefault().getLanguage();
            administarSesion.configuracionIdioma(-1);
            if(idioma.equals(new Locale ("es").getLanguage())|| idioma.equals(new Locale ("ES").getLanguage())){
                idioma="es";
                System.out.println("Idioma es español " + idioma);
            }
            else if(idioma.equals(new Locale ("en").getLanguage())|| idioma.equals(new Locale ("EN").getLanguage())){
                idioma="en";
                System.out.println("Idioma  es ingles " + idioma);
            }
            else if(!idioma.equals(new Locale ("en").getLanguage())|| !idioma.equals(new Locale ("EN").getLanguage())
                    ||!idioma.equals(new Locale ("es").getLanguage())|| !idioma.equals(new Locale ("ES").getLanguage())){
                idioma="es";
                System.out.println("Idioma es otro " + idioma);
            }

        }else
        if(administarSesion.getIdioma()==1){
            idioma="es";
        }
        else idioma="en";

        return idioma;
    }

    public static Context setLocale(Context c) {
        return updateResources(c, getLanguage(c));
    }

    public static Context setLocale(Context context, String language) {
        //persist(context, language);
        return updateResources(context,language);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);*/
    }

    /*@TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }*/

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (isAtLeastVersion(N)) {
            System.out.println("Utility.isAtLeastVersion(N)");
            setLocaleForApi24(config, locale);
            context = context.createConfigurationContext(config);
        } else if (isAtLeastVersion(JELLY_BEAN_MR1)) {
            System.out.println("Utility.isAtLeastVersion(JELLY_BEAN_MR1)");
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            context = context.createConfigurationContext(config);
        } else {
            System.out.println("else NADA");
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        System.out.println( "before return context "+res.getConfiguration());
        return context;
    }

    @RequiresApi(api = N)
    private static void setLocaleForApi24(Configuration config, Locale target) {
        Set<Locale> set = new LinkedHashSet<>();
        // bring the target locale to the front of the list
        set.add(target);

        LocaleList all = LocaleList.getDefault();
        for (int i = 0; i < all.size(); i++) {
            // append other locales supported by the user
            set.add(all.get(i));
        }

        Locale[] locales = set.toArray(new Locale[0]);
        config.setLocales(new LocaleList(locales));
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}
