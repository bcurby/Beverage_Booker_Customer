package com.beveragebooker.customer_app.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.User;

import java.util.ArrayList;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private ArrayList<MenuItem> mCartItems;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    //Get the user data and save it to shared preferences
    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("firstName", user.getFirstName());
        editor.putString("lastName", user.getLastName());
        editor.putString("phone", user.getPhone());

        editor.apply();
    }

    //Check to see whether the user is already logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        //Default value is -1
        //All user id numbers will be positive
        if(sharedPreferences.getInt("id", -1) != -1)
            return true;

        return false;
    }

    //Return the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("firstName", null),
                sharedPreferences.getString("lastName", null),
                sharedPreferences.getString("phone", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}
