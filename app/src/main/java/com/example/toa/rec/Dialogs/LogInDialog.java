package com.example.toa.rec.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toa.rec.Api;
import com.example.toa.rec.LoginHandler;
import com.example.toa.rec.ObjectModels.Event;
import com.example.toa.rec.R;
import com.example.toa.rec.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static com.example.toa.rec.Api.CODE_GET_REQUEST;
import static com.example.toa.rec.Api.CODE_POST_REQUEST;
import static java.security.MessageDigest.getInstance;


public class LogInDialog extends Dialog implements View.OnClickListener{

    EditText emailField;
    EditText passwordField;
    public Activity activity;
    public Dialog d;
    public Button loginButton;
    public int id;
    public Event e;

    public LogInDialog(Activity a, int i) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        id = i;
    }

    public LogInDialog(Activity a, Event e) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.e = e;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.log_in_dialog);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                try {
                    MessageDigest md = getInstance("MD5");
                    md.reset();
                    md.update(StandardCharsets.UTF_8.encode(password + email));
                    password = String.format("%032x", new BigInteger(1, md.digest()));

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                LoginHandler LH = new LoginHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("ePin", password);


                PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_GET_USER, params, CODE_POST_REQUEST, getContext() );
                pn.execute();

                break;
            case R.id.btn_back:
                dismiss();
                break;
            default:
                break;
        }
}


    /*ALEX: Performs a request using the php scripts to the databsae*/
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;
        Context c;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode, Context c) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
            this.c = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject object = new JSONObject(s);

                if (!object.getBoolean("error")) {

                }

                if(object.getJSONObject("user").get("UID").equals(null)) {
                    Toast.makeText(c, "There is no such account", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(c, "You have been logged in", Toast.LENGTH_SHORT).show();
                    LoginHandler lh = new LoginHandler();
                    String email = emailField.getText().toString();
                    String UID = object.getJSONObject("user").getString("UID");
                    String ePin = object.getJSONObject("user").getString("ePin");
                    int balance = object.getJSONObject("user").getInt("balance");
                    int resetPin = object.getJSONObject("user").getInt("resetPin");
                    if(resetPin == 1) {
                        // open up a new dialog to reset
                        Toast.makeText(c, "You need to reset your pin", Toast.LENGTH_SHORT).show();
                        ResetDialog d = new ResetDialog(activity, id, email, ePin, UID, balance, resetPin, e);
                        dismiss();
                        d.show();

                    } else {
                        lh.saveLoginInfo(getContext(), email, ePin, UID, balance, resetPin, activity);
                        dismiss();
                        if (e != null) {
                            EventDetailsDialog d = new EventDetailsDialog(activity, e);
                            d.show();
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


}

