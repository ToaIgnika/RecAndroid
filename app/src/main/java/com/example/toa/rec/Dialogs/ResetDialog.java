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

public class ResetDialog extends Dialog implements View.OnClickListener{

    EditText resetPasswordField;
    EditText confirmPasswordField;
    public Activity activity;
    public Dialog d;
    public Button resetLoginButton;
    public int id;

    public ResetDialog(Activity a, int i) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        id = i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reset_dialog);

        resetPasswordField = findViewById(R.id.resetPasswordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        resetLoginButton = findViewById(R.id.resetLoginButton);
        resetLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetLoginButton:
                String resetPassword = resetPasswordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                if(resetPassword.equals(confirmPassword)) {
                    // execte request
                    System.out.println("equal");
                } else {
                     Toast.makeText(getContext(), "Make sure your passwords match!", Toast.LENGTH_SHORT).show();
                     System.out.println("not equal");
                }

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
            // progressBar.setVisibility(View.GONE);
            try {
                //JSONObject object =
                System.out.println("the values are: " + s);

                JSONObject object = new JSONObject(s);

                if (!object.getBoolean("error")) {
                    // Toast.makeText(c, object.getString("message"), Toast.LENGTH_SHORT).show();
                }

                if(object.getJSONObject("user").get("UID").equals(null)) {
                    Toast.makeText(c, "There is no such account", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(c, "LOGGING YOU IN BOI", Toast.LENGTH_SHORT).show();
                    LoginHandler lh = new LoginHandler();
//                    String email = resetEmailField.getText().toString();
                    String UID = object.getJSONObject("user").getString("UID");
                    String ePin = object.getJSONObject("user").getString("ePin");
                    int balance = object.getJSONObject("user").getInt("balance");
                    int active = object.getJSONObject("user").getInt("active");
                    if(active == 1) {
                        // open up a new dialog to reset
                        Toast.makeText(c, "you need to reset a pin", Toast.LENGTH_SHORT).show();
                    } else {
  //                      lh.saveLoginInfo(getContext(), email, ePin, UID, balance, active, activity);
    //                    System.out.println("The user email" + email);
                        System.out.println("The user ePin" + ePin);
                        dismiss();
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(c, "SHIT FUCKED UP", Toast.LENGTH_LONG).show();

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


