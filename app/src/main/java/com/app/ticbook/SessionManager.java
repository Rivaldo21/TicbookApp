package com.app.ticbook;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "ticBook";
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

    public void saveSPBool(String keySP, boolean value) {
        editor.putBoolean(keySP, value);
        editor.commit();
    }

    public void saveUserData(UserResponse response) {
        editor.putInt("user_id",response.getUserID());
        editor.putString("username", response.getUsername());
        editor.putString("first_name", response.getFirstName());
        editor.putString("last_name", response.getLastName());
        editor.putString("full_name", response.getFullName());
        editor.putString("email", response.getEmail());
        editor.putString("role", response.getRole());
        editor.putString("department", response.getDepartement());
        editor.apply();
    }

    public UserResponse getUser() {
        UserResponse response = new UserResponse();
        response.setUserID(sharedPreferences.getInt("user_id", 111305));
        response.setUsername(sharedPreferences.getString("username", ""));
        response.setFirstName(sharedPreferences.getString("first_name", ""));
        response.setLastName(sharedPreferences.getString("last_name", ""));
        response.setFullName(sharedPreferences.getString("full_name", ""));
        response.setEmail(sharedPreferences.getString("email", ""));
        response.setRole(sharedPreferences.getString("role", ""));
        response.setDepartement(sharedPreferences.getString("department", ""));
        return response;
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
