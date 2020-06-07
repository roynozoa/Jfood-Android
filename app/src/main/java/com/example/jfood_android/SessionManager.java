package com.example.jfood_android;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //set login method
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    //get login method
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    //set email method
    public void setEmail(String email){
        editor.putString("KEY_EMAIL", email);
        editor.commit();
    }

    //get email method
    public String getEmail(){
        return sharedPreferences.getString("KEY_EMAIL", "");
    }

    //set id method
    public void setId (int id){
        editor.putInt("KEY_ID", id);
        editor.commit();
    }

    //get id method
    public int getId(){
        return sharedPreferences.getInt("KEY_ID", 0);
    }


    //set userName method
    public void setUserName(String userName){
        editor.putString("KEY_USERNAME", userName);
        editor.commit();
    }

    //get email method
    public String getUserName(){
        return sharedPreferences.getString("KEY_USERNAME", "");
    }

}
