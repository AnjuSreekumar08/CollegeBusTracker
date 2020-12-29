package com.example.collegebustrack;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;


    public static final String is_login="isloggedin";
    public static final String key_fullname="name";

    public static final String key_password="password";
    public static final String key_email="email";
    public static final String key_branch="branch";

    public SessionManager(Context context1)
    {
        context=context1;
        userSession=context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor=userSession.edit();

    }
    public void createLoginSession(String name,String password,String email,String branch)
    {
        editor.putBoolean(is_login,true);
        editor.putString(key_fullname,name);
        editor.putString(key_password,password);
        editor.putString(key_email,email);
        editor.putString(key_branch,branch);
        editor.commit();

    }

    public HashMap<String,String> getsUserDetailsFromSession(){
        HashMap<String,String> userData= new HashMap<String, String>();
        userData.put(key_fullname,userSession.getString(key_fullname,null));
        userData.put(key_password,userSession.getString(key_password,null));
        userData.put(key_email,userSession.getString(key_email,null));
        userData.put(key_branch,userSession.getString(key_branch,null));

        return userData;

    }

    public boolean checkLogin() {
        if (userSession.getBoolean(is_login, true)) {
            return true;
        } else
            return false;
    }

            public void logoutUserFromSession( ){

                editor.clear();
                editor.commit();
        }
    }


