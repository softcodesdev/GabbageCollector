package com.softcodes.gabbagecollector.constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.softcodes.gabbagecollector.Login;


public class SharedPrefManager {
    public static final String SHARED_PREF_NAME = "uwa";
    public static final String KEY_ID = "keyid";
    public static final String KEY_PASSWORD = "keypassword";
    public static final String KEY_EMAIL = "keyemail";

    public static final String KEY_ROLE = "keyuserrole";
    public static final String KEY_USERNAME = "keyusername";

    public static final String KEY_FNAME = "keyfname";
    public static final String KEY_LNAME = "keylastname";


    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getUserID());
        editor.putString(KEY_PASSWORD, user.getUserPassword());
        editor.putString(KEY_EMAIL, user.getUserEmail());
        editor.putString(KEY_ROLE, user.getUserRole());
        editor.putString(KEY_USERNAME,user.getUsername());

        editor.putString(KEY_FNAME, user.getFname());
        editor.putString(KEY_LNAME, user.getLname());

        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ROLE, null),
                sharedPreferences.getString(KEY_USERNAME, null),

                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_LNAME, null)

        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }
}