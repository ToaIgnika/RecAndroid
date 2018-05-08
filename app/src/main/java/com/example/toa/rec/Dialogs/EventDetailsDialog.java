package com.example.toa.rec.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.toa.rec.R;

public class EventDetailsDialog extends Dialog implements View.OnClickListener{
    public Activity c;
    public Dialog d;
    public Button yes, no;
    public int id;

    public EventDetailsDialog(Activity a, int i) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        id = i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_details_dialog);
        TextView tv = (TextView) findViewById(R.id.tv_instructor);
        tv.setText("" + id);
        yes = (Button) findViewById(R.id.btn_register);
        no = (Button) findViewById(R.id.btn_back);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                dismiss();
                break;
            case R.id.btn_back:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
