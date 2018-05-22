package com.example.toa.rec;

/**
 * Created by xianchen97 on 2018-05-08.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toa.rec.ObjectModels.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.toa.rec.Api.CODE_GET_REQUEST;
import static com.example.toa.rec.Api.CODE_POST_REQUEST;
import static java.lang.Integer.parseInt;

/**
 * The EventAdapter allows us to populate and modify a list of events
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>  {

    private List<Event> eventList;
    private Context mContext;
    private static ClickListener clickListener;

    public EventAdapter(List<Event> moviesList) {
        this.eventList = moviesList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, desc, date;
        public Button cancelBtn;


        public MyViewHolder(final View view)  {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.desc);
            date = (TextView) view.findViewById(R.id.date);
            cancelBtn = view.findViewById(R.id.cancelButton);
            mContext = view.getContext();
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event event = eventList.get(position);
        holder.title.setText(event.getClassName());
        holder.desc.setText("Description: " + event.getClassDescription());

       
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        long lstamp =Long.parseLong(event.getEventDay() + "000") ;
        Date eTime =new Date(lstamp);


        holder.date.setText(df.format(eTime));


        /**
         * Determines what to do when we click the "x" to cancel a class
         */
        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Cancel " + event.getClassName());
                builder.setMessage("Are you sure you wish to cancel this class?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HashMap<String, String> params = new HashMap<>();
                                SharedPreferences sharedPref = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                String UID = sharedPref.getString("UID", "");
                                params.put("UID", UID);
                                String eventID = event.getEventID();
                                params.put("eventID", eventID);
                                PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_REMOVE_EVENT, params, CODE_POST_REQUEST, mContext);
                                pn.execute();
                                eventList.remove(position);
                                notifyDataSetChanged();

                            }
                        });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /**
     * Performs a request using the php scripts to the database
     **/
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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(c, object.getString("The class has been removed from your schedule"), Toast.LENGTH_SHORT).show();
                    Activity a = (Activity) mContext;
                    LoginHandler lh = new LoginHandler();
                    lh.updateBalance(mContext,lh.getBalance(mContext)+1,a);
                } else {
                    System.out.println("there is an error" + object.getString("error"));
                }
            } catch (JSONException e) {

            }
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
}