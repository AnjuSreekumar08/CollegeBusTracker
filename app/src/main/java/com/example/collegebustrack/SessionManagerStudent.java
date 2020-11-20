package com.example.collegebustrack;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagerStudent {


    SharedPreferences studentSession;
    SharedPreferences.Editor editor;
    Context context;


    public static final String is_login="isloggedin";
    public static final String key_adm="adm";
    public static final String key_branch="branch";
    public static final String key_email="email";
    public static final String key_sem="sem";
    public static final String key_name="name";
    public static final String key_busid="busid";
    public static final String key_password="password";


    public SessionManagerStudent(Context context1)
    {
        context=context1;
        studentSession=context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor=studentSession.edit();

    }
    public void createLoginSession(String name,String email,String branch,String adm,String sem,String busid,String password)
    {
        editor.putBoolean(is_login,true);
        editor.putString(key_name,name);
        editor.putString(key_email,email);
        editor.putString(key_branch,branch);
        editor.putString(key_adm,adm);
        editor.putString(key_sem,sem);
        editor.putString(key_busid,busid);
        editor.putString(key_password,password);

        editor.commit();

    }

    public HashMap<String,String> getsUserStudentDetailsFromSession(){
        HashMap<String,String> userData= new HashMap<String, String>();
        userData.put(key_name,studentSession.getString(key_name,null));
        userData.put(key_email,studentSession.getString(key_email,null));
        userData.put(key_branch,studentSession.getString(key_branch,null));
        userData.put(key_adm,studentSession.getString(key_adm,null));
        userData.put(key_sem,studentSession.getString(key_sem,null));
        userData.put(key_busid,studentSession.getString(key_busid,null));
        userData.put(key_password,studentSession.getString(key_password,null));

        return userData;

    }

    public boolean checkLogin() {
        if (studentSession.getBoolean(is_login, true)) {
            return true;
        } else
            return false;
    }

    public void logoutUserFromSession( ){

        editor.clear();
        editor.commit();
    }
}


