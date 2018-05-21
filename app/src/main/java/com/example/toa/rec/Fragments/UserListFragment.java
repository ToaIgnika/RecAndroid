package com.example.toa.rec.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.toa.rec.Api;
import com.example.toa.rec.Dialogs.EventDetailsDialog;
import com.example.toa.rec.EventAdapter;
import com.example.toa.rec.LoginHandler;
import com.example.toa.rec.ObjectModels.Event;
import com.example.toa.rec.R;
import com.example.toa.rec.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.toa.rec.Api.CODE_GET_REQUEST;
import static com.example.toa.rec.Api.CODE_POST_REQUEST;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;
    private TextView loginPlease;


    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        loginPlease = view.findViewById(R.id.loginPlease);
        LoginHandler lh = new LoginHandler();
        if (!lh.isLoggedIn(getContext(), getActivity())) {
            loginPlease.setText("Log in to view your schedule");
            /*
            loginPlease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogInDialog d = new LogInDialog(getActivity(), v.getId());
                    d.show();
                }
            });
            */

        } else {
            loginPlease.setVisibility(View.GONE);
        }


        recyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new EventAdapter(eventList);
        mAdapter.setOnItemClickListener(new EventAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println("onItemClick position" + position);
                EventDetailsDialog d = new EventDetailsDialog(getActivity(), eventList.get(position));
                d.show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        HashMap<String, String> params = new HashMap<>();
        SharedPreferences sharedPref = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String UID = sharedPref.getString("UID", "");
        System.out.println("Submitting UID " + UID);
        params.put("UID", UID);
        PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_GET_USER_EVENTS, params, CODE_POST_REQUEST,getContext());
        pn.execute();

        Button mail_button = (Button) view.findViewById(R.id.btn_mail_user_sched);
        mail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> prms = new HashMap<>();
                SharedPreferences sharedPref = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String UID = sharedPref.getString("UID", "");
                System.out.println("Submitting UID " + UID);
                prms.put("UID", UID);
                PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_MAIL_USER_EVENTS, prms, CODE_POST_REQUEST,getContext());
                pn.execute();
            }
        });

        return view;
    }

    private void prepareEventData(JSONObject object) throws JSONException {

        eventList.clear();

        JSONArray arr = object.getJSONArray("events");
        //iterate thru them and add

        System.out.println("The object is" + object);

        System.out.println("The array is" + arr);
        System.out.println("The length is" + arr.length());
        // System.out.println("The first element is" + arr.get(0));

        for(int i = 0; i < arr.length(); i++) {
            System.out.println("There is an event" + arr.get(i));
            Event event = new Event();
            JSONObject eventObject = (JSONObject) arr.get(i);
            event.fromJson(eventObject);
            eventList.add(event);
        }






        mAdapter.notifyDataSetChanged();
    }


    /*ALEX: Performs a request using the php scripts to the databsae*/
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String UID;
        HashMap<String, String> params;
        int requestCode;
        Context c;

        PerformNetworkRequest(String UID, HashMap<String, String> params, int requestCode, Context c) {
            this.UID = UID;
            this.params = params;
            this.requestCode = requestCode;
            this.c = c;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   progressBar.setVisibility(View.VISIBLE);
            System.out.println("We are in pre-execute");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // progressBar.setVisibility(View.GONE);

            System.out.println("in post execute" + s);
            try {
                //JSONObject object =
                JSONObject object = new JSONObject(s);
                //System.out.println("MAIL:" + s);

                if (!object.getBoolean("error")){ // && !object.getString("message").equals("m")) {
                    prepareEventData(object);
                } else {
                    System.out.println("there is an error" + object.getString("error"));
                }

            } catch (JSONException e) {
                System.out.println("Caught an error");
                eventList.clear();
                mAdapter.notifyDataSetChanged();
                e.printStackTrace();
            }

            mAdapter.notifyDataSetChanged();

        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(UID, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(UID);

            return null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Write down your refresh code here, it will call every time user come to this fragment.
            //If you are using listview with custom adapter, just call notifyDataSetChanged().

            HashMap<String, String> params = new HashMap<>();
            SharedPreferences sharedPref = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String UID = sharedPref.getString("UID", "");
            params.put("UID", UID);
            PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_GET_USER_EVENTS, params, CODE_POST_REQUEST,getContext());
            pn.execute();


        }
    }


}
