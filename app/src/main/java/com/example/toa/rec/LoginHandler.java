package com.example.toa.rec;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toa.rec.Fragments.BrowseFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import static com.example.toa.rec.Api.CODE_GET_REQUEST;
import static com.example.toa.rec.Api.CODE_POST_REQUEST;

public class LoginHandler {

    String email;
    String ePin;

    public void saveLoginInfo(Context c, String email, String password, int balance, Activity a) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putInt("balance", balance);
        editor.apply();
        TextView userDisplay = a.findViewById(R.id.userDisplay);
        userDisplay.setText("Welcome: " + email);
        TextView balanceDisplay = a.findViewById(R.id.balanceDisplay);
        balanceDisplay.setText("Balance: " + balance);
    }

    public void isValidLoginRequest(String username,  String password, Context c) {
        String email = "asdsa@gmail.com";
        String ePin =  "64fd40ac74b5aac63ee3307b0a99f774";
       // PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_USER, null, CODE_GET_REQUEST, c);
        //request.execute();
    }

    public boolean isLoggedIn(Context c) {
        return true;
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

    public void logout(Context c) {
        SharedPreferences sharedPref = c.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", null);
        editor.putString("password", null);
   }



}
