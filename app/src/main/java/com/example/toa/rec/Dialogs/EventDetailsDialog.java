package com.example.toa.rec.Dialogs;

import android.app.Activity;
import android.app.Dialog;
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

import com.example.toa.rec.Event;
import com.example.toa.rec.LoginHandler;
import com.example.toa.rec.MainActivity;
import com.example.toa.rec.R;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            Toast.makeText(c, "user is logged in", Toast.LENGTH_LONG).show();
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
}

