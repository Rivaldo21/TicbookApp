package com.app.ticbook;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String SESSION_KEY = "session_user";
    private static final String PREF_NAME = "ticBook";

    private static final String USERNAME = "nama";
    private static final String IS_LOGIN = "is_login";
    private static final String TOKEN = "token";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    // Constructior

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public SessionManager(Context context, String PREF_NAME_PARAMS) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME_PARAMS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public int getSession() {
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void unsetSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }

    public void saveSPBool(String keySP, boolean value) {
        editor.putBoolean(keySP, value);
        editor.commit();
    }

    public void setUsername(String nama) {
        if (!nama.equals(""))
            saveSPString(USERNAME, nama);
    }

    public String getUsername(){
        return sharedPreferences.getString(USERNAME, "");
    }

    public void setToken(String token) {
        saveSPString(TOKEN, token);
    }

    public String getToken(){
        return sharedPreferences.getString(TOKEN, "");
    }

    public void setLogin(Boolean login) {
        saveSPBool(IS_LOGIN, login);
    }

    public Boolean getIsLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void saveSPString(String keySP, String value){
        editor.putString(keySP, value);
        editor.commit();
    }

}
