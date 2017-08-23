package com.example.android.plm.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "PLM";

    private static final String SHOP_ACTIVE = "shop_active";

    private static final String IS_LOGIN = "is_login";
    private static final String ID = "id";
    private static final String UNIQUE_ID = "unique_id";
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phone_number";

    public MyPreferenceManager(Context context) {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    // Setter
    public void setShopActive(String shop_id) {
        editor.putString(SHOP_ACTIVE, shop_id);
        editor.commit();
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public void setID(int id) {
        editor.putInt(ID, id);
        editor.commit();
    }

    public void setUniqueId(String uniqueId) {
        editor.putString(UNIQUE_ID, uniqueId);
        editor.commit();
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public void setFirstname(String firstname) {
        editor.putString(FIRSTNAME, firstname);
        editor.commit();
    }

    public void setLastname(String lastname) {
        editor.putString(LASTNAME, lastname);
        editor.commit();
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public void setPhoneNumber(String phoneNumber) {
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.commit();
    }

    // Getter
    public String getShopActive() {
        return sharedPreferences.getString(SHOP_ACTIVE, null);
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public int getID() {
        return sharedPreferences.getInt(ID, 0);
    }

    public String getUniqueId() {
        return sharedPreferences.getString(UNIQUE_ID, null);
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, null);
    }

    public String getFirstname() {
        return sharedPreferences.getString(FIRSTNAME, null);
    }

    public String getLastname() {
        return sharedPreferences.getString(LASTNAME, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(PHONE_NUMBER, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

}
