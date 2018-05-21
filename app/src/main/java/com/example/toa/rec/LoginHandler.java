package com.example.toa.rec;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

/**
 * Defines a LoginLander which does all of the work regarding logging in and logged
 * in user details
 */
public class LoginHandler {

    String email;
    String UID;
    String ePin;

    /**
     * Saves the logged in info if a user has been logged in
     * @param c
     * @param email
     * @param password
     * @param UID
     * @param balance
     * @param resetPin
     * @param a
     */
    public void saveLoginInfo(Context c, String email, String password, String UID,  int balance, int resetPin,  Activity a) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("UID", UID);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putInt("balance", balance);
        editor.putInt("resetPin", resetPin);
        editor.apply();
        TextView userDisplay = a.findViewById(R.id.userDisplay);
        userDisplay.setText("Welcome: " + email);
        TextView balanceDisplay = a.findViewById(R.id.balanceDisplay);
        balanceDisplay.setText("Balance: " + balance);
        Button loginLogoutBtn = a.findViewById(R.id.loginLogoutButton);
        loginLogoutBtn.setText("Logout");
    }


    /**
     * Determines whether the user has been logged in or not
     *
     * @param c
     * @param a
     * @return
     */
    public boolean isLoggedIn(Context c, Activity a) {
        SharedPreferences sharedPreferences = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("email")){
            return false;
        } else if (sharedPreferences.getString("email","").equals(null)) {
            return false;
        } else {
            return true;
        }

    }

    public String getEmail(Context c) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", "");
        return email;
    }

    public String getPassword(Context c) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String password = sharedPref.getString("password", "");
        return password;
    }

    public void logout(Context c, Activity a) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", null);
        editor.putString("password", null);
        editor.putString("UID", null);
        editor.apply();
        TextView userDisplay = a.findViewById(R.id.userDisplay);
        userDisplay.setText("Welcome: guest");
        TextView balanceDisplay = a.findViewById(R.id.balanceDisplay);
        balanceDisplay.setText("");
        Button loginLogoutBtn = a.findViewById(R.id.loginLogoutButton);
        loginLogoutBtn.setText("Login");
    }

    public int getBalance(Context c){
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int balance = sharedPref.getInt("balance", 0);
        return balance;
    }

    public String getUID (Context c) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String uid = sharedPref.getString("UID", "");
        return uid;
    }



}
