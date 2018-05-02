package com.example.toa.rec.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toa.rec.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {


    public BrowseFragment() {

    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browse, container, false);

        // Get a reference for the week view in the layout.
        LinearLayout monday = (LinearLayout) v.findViewById(R.id.calendar_week_1);
        LinearLayout tuesday = (LinearLayout) v.findViewById(R.id.calendar_week_2);
        LinearLayout wednesday = (LinearLayout) v.findViewById(R.id.calendar_week_3);
        LinearLayout thursday = (LinearLayout) v.findViewById(R.id.calendar_week_4);
        LinearLayout friday = (LinearLayout) v.findViewById(R.id.calendar_week_5);
        LinearLayout[] weekdays = new LinearLayout[5];
        weekdays[0] = monday;
        weekdays[1] = tuesday;
        weekdays[2] = wednesday;
        weekdays[3] = thursday;
        weekdays[4] = friday;

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;

        for (int weekday = 0; weekday < 5; ++weekday) {
            for (int timeslot = 0; timeslot < 6; ++timeslot) {

                // create view for event cell
                View view = getLayoutInflater().inflate(R.layout.event_calendar_cell, null);
                // set width and height based on screen estate
                int width = v.getWidth();
                int height = v.getHeight();
                view.setMinimumWidth(width/5);
                view.setMinimumHeight(height/7);
                view.setId(weekday*timeslot);

                /**
                 * Handle weekday line creation
                 */
                if (timeslot == 0 && weekday == 0) {
                    setTextContent(view, "Monday");
                } else if (timeslot == 0 && weekday == 1) {
                    setTextContent(view, "Tuesday");
                } else if (timeslot == 0 && weekday == 2) {
                    setTextContent(view, "Wednesday");
                } else if (timeslot == 0 && weekday == 3) {
                    setTextContent(view, "Thursday");
                } else if (timeslot == 0 && weekday == 4) {
                    setTextContent(view, "Friday");
                } else {
                    // attach onclick listener to actual events
                    // TODO: need to handle logic for empty event cells
                    view.setOnClickListener(onCellClickListener);
                }

                // add event cell view to appropriate weekday
                weekdays[weekday].addView(view);
            }
        }


        return v;
    }


    /**
     * Changes color of the displayed event
     * @param event
     * @param c
     */
    private void setTextColor(View event, int c) {
        TextView tv = (TextView) event.findViewById(R.id.tv_event_info);
        tv.setTextColor(c);
    }

    /**
     * Changes text content of the displayed event
     * @param event
     * @param c
     */
    private void setTextContent(View event, String c) {
        TextView tv = (TextView) event.findViewById(R.id.tv_event_info);
        tv.setText(c);
    }

    /**
     * method to create an event preview window (dialog).
     * TODO: change parameter to Event type, properly style dialog window, handle login/etc
     * @param info
     */
    private void startDialog(String info) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setTitle("Weather Forcast:");
        alert.setMessage(info);
        //TextView myMsg = new TextView(this.getContext());
        //alert.setMessage(  parseForecast(((Vars) getApplication()).forecast));
        /*
        myMsg.setText(  parseForecast(((Vars) getApplication()).forecast));
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        myMsg.setLineSpacing(3,1);
        alert.setView(myMsg);
        */
        alert.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setCancelable(true);
        alert.show();
    }


    /**
     * Onclick listener for cells.
     * TODO: implement eventlist, assign ID corresponding to index in the list
     */
    View.OnClickListener onCellClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //if(v == tv[0]){
                //do whatever you want....
            //}
            startDialog("ID:" + v.getId());
        }
    };

}
