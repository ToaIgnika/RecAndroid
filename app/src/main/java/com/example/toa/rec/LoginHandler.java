package com.example.toa.rec;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toa.rec.Dialogs.LogInDialog;
import com.example.toa.rec.Fragments.BrowseFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import static com.example.toa.rec.Api.CODE_GET_REQUEST;
import static com.example.toa.rec.Api.CODE_POST_REQUEST;

public class LoginHandler {

    String email;
    String UID;
    String ePin;

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

    public void isValidLoginRequest(String username,  String password, Context c) {
        String email = "asdsa@gmail.com";
        String ePin =  "64fd40ac74b5aac63ee3307b0a99f774";
       // PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_USER, null, CODE_GET_REQUEST, c);
        //request.execute();
    }

    public boolean isLoggedIn(Context c, Activity a) {
        SharedPreferences sharedPreferences = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("email")){
            // login
            return false;
        } else if (sharedPreferences.getString("email","").equals(null)) {
            //login
            return false;
//            Dialog logInDialog = new LogInDialog(a,1 );
  //          logInDialog.show();
        } else {
            //logout
            System.out.println("returning true" + sharedPreferences.getString("email",""));

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



}
