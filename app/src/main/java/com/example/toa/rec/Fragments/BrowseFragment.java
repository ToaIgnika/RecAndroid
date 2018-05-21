package com.example.toa.rec.Fragments;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toa.rec.Api;
import com.example.toa.rec.Dialogs.EventDetailsDialog;
import com.example.toa.rec.ObjectModels.Event;
import com.example.toa.rec.R;
import com.example.toa.rec.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    /* ALEX: The code for a get request */
    private static final int CODE_GET_REQUEST = 1024;
    /*ALEX: The code for a post request */
    private static final int CODE_POST_REQUEST = 1025;
    /*ALEX: The list to hold event objects */
    List<Event> eventList;


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

        // will hold the list of events pulled from database
        eventList = new ArrayList<>();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browse, container, false);

        /*ALEX: This will read the events from the database and also call the method "load events" and "display events" to update
         * the layout*/
        //displayEvents(v);
        readEvents();
        return v;
    }

    /*ALEX: Reads all the events from the database */
    private void readEvents() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_EVENTS, null, CODE_GET_REQUEST);
        request.execute();
    }

    /* ALEX: Handles the asycnh tasks of network requests  */
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
            //  progressBar.setVisibility(View.GONE);
            System.out.println("HHH" + s);
            try {
                JSONObject object = new JSONObject(s);

                if (!object.getBoolean("error")) {
                    //Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    /*ALEX: Loads the events from the json response from the database*/
                    //Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                    loadEvents(object.getJSONArray("events"));
                } else {
                    //Toast.makeText(getContext(), object.getString("gg"), Toast.LENGTH_SHORT).show();

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

    /*ALEX: This first loads the events into our event list, then calls dislay events to put them into the layout*/
    private void loadEvents(JSONArray events) throws JSONException {
        eventList.clear();

        for (int i = 0; i < events.length(); i++) {
            JSONObject obj = events.getJSONObject(i);
            Event newEvent = new Event();
            newEvent.fromJson(obj);
            eventList.add(newEvent);
            SimpleDateFormat ft =
                    new SimpleDateFormat ("E yyyy.MM.dd");
            java.util.Date time=new java.util.Date((long)Integer.valueOf(newEvent.getEventDay())
                    *1000);
            //System.out.println("XXX:" + newEvent.getEventDay() + "/" + ft.format(time));
        }

        /*ALEX: Calls display events now that we have loaded them to change the schedule layout accordingly*/
        displayEvents(getView());
    }

    private void displayEvents(View v) {
        // Get a reference for the week view in the layout.
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) v.findViewById(R.id.horizontal_scroll);
        LinearLayout monday = (LinearLayout) v.findViewById(R.id.calendar_week_1);
        LinearLayout tuesday = (LinearLayout) v.findViewById(R.id.calendar_week_2);
        LinearLayout wednesday = (LinearLayout) v.findViewById(R.id.calendar_week_3);
        LinearLayout thursday = (LinearLayout) v.findViewById(R.id.calendar_week_4);
        LinearLayout friday = (LinearLayout) v.findViewById(R.id.calendar_week_5);
        LinearLayout saturday = (LinearLayout) v.findViewById(R.id.calendar_week_6);
        LinearLayout sunday = (LinearLayout) v.findViewById(R.id.calendar_week_7);
        LinearLayout monday2 = (LinearLayout) v.findViewById(R.id.calendar_week_8);
        LinearLayout tuesday2 = (LinearLayout) v.findViewById(R.id.calendar_week_9);
        LinearLayout wednesday2 = (LinearLayout) v.findViewById(R.id.calendar_week_10);
        LinearLayout thursday2 = (LinearLayout) v.findViewById(R.id.calendar_week_11);
        LinearLayout friday2 = (LinearLayout) v.findViewById(R.id.calendar_week_12);
        LinearLayout saturday2 = (LinearLayout) v.findViewById(R.id.calendar_week_13);
        LinearLayout sunday2 = (LinearLayout) v.findViewById(R.id.calendar_week_14);
        LinearLayout monday3 = (LinearLayout) v.findViewById(R.id.calendar_week_15);
        LinearLayout tuesday3 = (LinearLayout) v.findViewById(R.id.calendar_week_16);
        LinearLayout wednesday3 = (LinearLayout) v.findViewById(R.id.calendar_week_17);
        LinearLayout thursday3 = (LinearLayout) v.findViewById(R.id.calendar_week_18);
        LinearLayout friday3 = (LinearLayout) v.findViewById(R.id.calendar_week_19);
        LinearLayout saturday3 = (LinearLayout) v.findViewById(R.id.calendar_week_20);
        LinearLayout sunday3 = (LinearLayout) v.findViewById(R.id.calendar_week_21);

        LinearLayout[] weekdays = new LinearLayout[21];
        weekdays[0] = monday;
        weekdays[1] = tuesday;
        weekdays[2] = wednesday;
        weekdays[3] = thursday;
        weekdays[4] = friday;
        weekdays[5] = saturday;
        weekdays[6] = sunday;
        weekdays[7] = monday2;
        weekdays[8] = tuesday2;
        weekdays[9] = wednesday2;
        weekdays[10] = thursday2;
        weekdays[11] = friday2;
        weekdays[12] = saturday2;
        weekdays[13] = sunday2;
        weekdays[14] = monday3;
        weekdays[15] = tuesday3;
        weekdays[16] = wednesday3;
        weekdays[17] = thursday3;
        weekdays[18] = friday3;
        weekdays[19] = saturday3;
        weekdays[20] = sunday3;

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;

        setWeekdays(v, weekdays);
        setEvents(v, weekdays);

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

    // variable to track event time
    private long mLastClickTime = 0;

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
            //startDialog("ID:" + v.getId());
            // Preventing multiple clicks, using threshold of 1 second
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            EventDetailsDialog d = new EventDetailsDialog(getActivity(), eventList.get(v.getId()));
            d.show();
        }
    };

    private void setWeekdays(View v, LinearLayout[] weekdays) {
        for (int weekday = 0; weekday < 21; ++weekday) {
            // create view for event cell
            View view = getLayoutInflater().inflate(R.layout.event_calendar_cell, null);
            // create view for event cell

            // set width and height based on screen estate
            int width = v.getWidth();
            int height = v.getHeight();
            view.setMinimumWidth(width / 5);
            view.setMinimumHeight(height / 7);
            //view.setId(weekday * timeslot);

            // create instance of the calendar
            Calendar calendar = Calendar.getInstance();

            // get todays weekday
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            // change +/- 1 to edit the dates
            calendar.add(Calendar.DAY_OF_YEAR, weekday - day + 2);

            // get the date
            Date c = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd");
            String formattedDate = df.format(c);

            /**
             * Handle weekday line creation
             */
            if (weekday%7 == 0) {
                setTextContent(view, "Monday, " + formattedDate);
                setTextColor(view, Color.BLUE);
            } else if (weekday%7 == 1) {
                setTextContent(view, "Tuesday, " + formattedDate);
                setTextColor(view, Color.BLUE);
            } else if (weekday%7 == 2) {
                setTextContent(view, "Wednesday, " + formattedDate);
                setTextColor(view, Color.BLUE);
            } else if (weekday%7 == 3) {
                setTextContent(view, "Thursday, " + formattedDate);
                setTextColor(view, Color.BLUE);
            } else if (weekday%7 == 4) {
                setTextContent(view, "Friday, " + formattedDate);
                setTextColor(view, Color.BLUE);
            } else if (weekday%7 == 5) {
                setTextContent(view, "Saturday, " + formattedDate);
                setTextColor(view, Color.RED);
            } else if (weekday%7 == 6) {
                setTextContent(view, "Sunday, " + formattedDate);
                setTextColor(view, Color.RED);
            } else { }

            weekdays[weekday].addView(view);
        }
    }

    private void setEvents(View v, LinearLayout[] weekdays)  {
        for (int weekday = 0; weekday < 21; ++weekday) {


            // create instance of the calendar
            Calendar calendar = Calendar.getInstance();

            // get todays weekday
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            // change +/- 1 to edit the dates
            calendar.add(Calendar.DAY_OF_YEAR, weekday - day + 2);

            // get the date
            Date c = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd");
            String formattedDate = df.format(c);

            try {
                Date cc = df.parse(df.format(c));

                for (int i = 0; i < eventList.size(); ++i) {
                    long lstamp =Long.parseLong(eventList.get(i).getEventDay() + "000") ;
                    Date eTime =new Date(lstamp);
                    System.out.println("SHIT: " + eTime + "|" + c + " |XOXO| " + lstamp);
                    if (eTime.after(c)) {
                        //break;
                    }

                    if (df.format(c).equals(df.format(eTime))) {
                        System.out.println("Some shit happened today");
                        View view = createCell(v);
                        TextView eventInfo =  view.findViewById(R.id.tv_event_info);
                        eventInfo.setText(eventList.get(i).getClassName() + "\n"
                                + eventList.get(i).getClassLocation() + "\n"
                                + eventList.get(i).getBeginHour() + ":" + eventList.get(i).getBeginMin() + "-" + eventList.get(i).getEndHour() + ":" + eventList.get(i).getEndMin()
                                + eventList.get(i).getClassLocation() + "\n"
                                + eventList.get(i).getFirstname()
                        );
                        setTextColor(view, Color.parseColor(eventList.get(i).getHexColor()));
                        view.setOnClickListener(onCellClickListener);
                        view.setId(i);
                        weekdays[weekday].addView(view);
                    }
                }
            } catch (Exception e) {
                System.out.println("Fuked up shit" + e);
            }

        }
    }

    View  createCell(View v) {
        // create view for event cell
        View view = getLayoutInflater().inflate(R.layout.event_calendar_cell, null);
        // create view for event cell

        // set width and height based on screen estate
        int width = v.getWidth();
        int height = v.getHeight();
        view.setMinimumWidth(width / 5);
        view.setMinimumHeight(height / 7);
        //view.setId(weekday * timeslot);
        return view;
    }
}
