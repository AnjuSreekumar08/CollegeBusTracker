package com.example.collegebustrack;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagerDriver {


    SharedPreferences driverSession;
    SharedPreferences.Editor editor;
    Context context;


    public static final String is_login="isloggedin";
    public static final String key_busid="bus";
    public static final String key_name="name";
    public static final String key_email="email";
    public static final String key_phone="phone";
    public static final String key_password="password";



    public SessionManagerDriver(Context context1)
    {
        context=context1;
        driverSession=context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor=driverSession.edit();

    }
    public void createLoginSession(String bus,String name,String email,String phone,String password)
    {
        editor.putBoolean(is_login,true);
        editor.putString(key_busid,bus);
        editor.putString(key_name,name);
        editor.putString(key_email,email);
        editor.putString(key_phone,phone);
        editor.putString(key_password,password);

        editor.commit();

    }

    public HashMap<String,String> getsUserDriverDetailsFromSession(){
        HashMap<String,String> userData= new HashMap<String, String>();
        userData.put(key_busid,driverSession.getString(key_busid,null));
        userData.put(key_name,driverSession.getString(key_name,null));
        userData.put(key_email,driverSession.getString(key_email,null));
        userData.put(key_phone,driverSession.getString(key_phone,null));
        userData.put(key_password,driverSession.getString(key_password,null));



        return userData;

    }

    public boolean checkLogin() {
        if (driverSession.getBoolean(is_login, true)) {
            return true;
        } else
            return false;
    }

    public void logoutUserFromSession( ){

        editor.clear();
        editor.commit();
    }
}


