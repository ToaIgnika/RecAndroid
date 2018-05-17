package com.example.toa.rec.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toa.rec.Api;
import com.example.toa.rec.Event;
import com.example.toa.rec.LoginHandler;
import com.example.toa.rec.MainActivity;
import com.example.toa.rec.R;
import com.example.toa.rec.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.example.toa.rec.Api.CODE_GET_REQUEST;
import static com.example.toa.rec.Api.CODE_POST_REQUEST;

public class EventDetailsDialog extends Dialog implements View.OnClickListener{
    public Activity c;
    public Dialog d;
    public Button yes, no;
    public int id;
    Event e;

    public EventDetailsDialog(Activity a, Event e) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.e = e;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_details_dialog);
        //TextView tv = (TextView) findViewById(R.id.tv_instructor);
        //tv.setText("" + id);
        yes = (Button) findViewById(R.id.btn_register);
        no = (Button) findViewById(R.id.btn_back);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        setEvents();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                dismiss();
                registerClass();
                break;
            case R.id.btn_back:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void setEvents() {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd");
        long lstamp =Long.parseLong(e.getEventDay() + "000") ;
        Date eTime =new Date(lstamp);

        TextView holder;
        ImageView img_holder;
        holder = (TextView) findViewById(R.id.tv_instructor_name);
        holder.setText(e.getFirstname() + " " + e.getLastname());
        holder = (TextView) findViewById(R.id.tv_instructor_info);
        holder.setText(e.getBio());

        holder = (TextView) findViewById(R.id.tv_class_name);
        holder.setText(e.getClassName());
        holder = (TextView) findViewById(R.id.tv_class_date);
        holder.setText(df.format(eTime)  + ", " + e.getBeginHour() + ":" + e.getBeginMin() + "-" + e.getEndHour() + ":" + e.getEndMin());
        holder = (TextView) findViewById(R.id.tv_class_location);
        holder.setText(e.getClassLocation());
        holder = (TextView) findViewById(R.id.tv_class_details);
        holder.setText(e.getClassDescription());


        try {
            new DownloadImageTask((ImageView) findViewById(R.id.iv_instructor)).execute("https://www.w3schools.com/bootstrap4/img_avatar5.png");
            new DownloadImageTask((ImageView) findViewById(R.id.iv_class_cover)).execute("https://thumbs-prod.si-cdn.com/1aYkS8dARZZQSJMpPIY6sFlMUls=/800x600/filters:no_upscale():focal(423x276:424x277)/https://public-media.smithsonianmag.com/filer/c6/3b/c63ba994-68b6-4052-84bd-fb9eddce7cba/istock-499925702.jpg");
            //new DownloadImageTask((ImageView) findViewById(R.id.iv_instructor)).execute(e.getPhotoURL());
            //new DownloadImageTask((ImageView) findViewById(R.id.iv_class_cover)).execute(e.getClassImageURL());
        } catch (Exception e) {
            System.out.println("Img loader e: " + e);
        }

    }

    public void registerClass(){
        LoginHandler lh = new LoginHandler();
        if (!lh.isLoggedIn(c.getApplicationContext(), c)) {
            LogInDialog d = new LogInDialog(c, e);
            d.show();
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("uid", lh.getUID(c.getApplicationContext()));
            params.put("classid", e.getEventID());
            PerformNetworkRequest pnr = new PerformNetworkRequest(Api.URL_REGISTER_CLASS, params, CODE_POST_REQUEST);
            //PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_GET_USER, params, CODE_POST_REQUEST, getContext() );
            pnr.execute();
            Toast.makeText(c, "I tried to register", Toast.LENGTH_LONG).show();
        }

    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /*ALEX: Performs a request using the php scripts to the databsae*/
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        /*ALEX: This is the url of the php script to execute*/
        String url;
        /*ALEX: A hashmap to store the parameters the php method needs*/
        HashMap<String, String> params;
        /*ALEX: A request code to determine post or get*/
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        /*ALEX: Before the task finishes, we can do stuff here like a spinner*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);
        }

        /*ALEX: Once the task has finished executing we can do stuff here, i.e. load the display*/
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("XDXD: " + s);

            //  progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);

                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    /*ALEX: Loads the events from the json response from the database*/
                    //Toast.makeText(getContext(), "User is registered", Toast.LENGTH_SHORT).show();
                    //loadEvents(object.getJSONArray("events"));
                } else {
                    Toast.makeText(getContext(), object.getString("gg"), Toast.LENGTH_SHORT).show();
                    System.out.println("XDXD: " + s);
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

