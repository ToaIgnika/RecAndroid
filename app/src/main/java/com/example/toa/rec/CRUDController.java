package com.example.toa.rec;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CRUDController {
    private RequestQueue sRequestQueue;

    public CRUDController (Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
    }


    private void getEventList() {
        //
        String url = "";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("events");
                            ArrayList<Event> event_list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length() && i < 10; i++) {
                                JSONObject events = jsonArray.getJSONObject(i);
                                event_list.add(new Event().fromJson(events));
                            }
                            //return event_list;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        sRequestQueue.add(request);
    }
}
