package com.moez.QKSMS.common.google;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kautsar on 13/12/17.
 */

public class PrefManager {

    private static final String PREF_NAME = "HSMSPref";
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }


    public void saveRegisterDetails(boolean condition) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isRegister", condition).apply();
    }

    public void saveLoginDetail(boolean condition) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", condition).apply();
    }

    public boolean isRegister() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isRegister", false);
    }

    public boolean isLogin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLogin", false);
    }
}
