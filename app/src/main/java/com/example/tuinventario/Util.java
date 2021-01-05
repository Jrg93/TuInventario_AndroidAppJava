package com.example.tuinventario;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPrefs(SharedPreferences preferences){
        return preferences.getString("correo", "");
    }

    public static String getUserPassPrefs(SharedPreferences preferences){
        return preferences.getString("contraseña", "");
    }

    public static void saveOnPreferences(String correo, String pass, SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", correo);
        editor.putString("contraseña", pass);
        editor.commit();
        editor.apply();
    }

}
