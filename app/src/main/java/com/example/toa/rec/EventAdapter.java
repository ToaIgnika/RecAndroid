package com.example.toa.rec;

/**
 * Created by xianchen97 on 2018-05-08.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.toa.rec.Dialogs.EventDetailsDialog;
import com.example.toa.rec.Fragments.UserListFragment;

import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.title.setText(event.getClassName());
        holder.desc.setText("Description: " + event.getClassDescription());

        Date date = new Date();
        int unixTime = parseInt(event.getEventDay());
        date.setTime((long)unixTime*1000);
        holder.date.setText("Date: " + date.toString());

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open up a dialog saying are you sure you want to cancel this event?
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Cancel " + event.getClassName());
                builder.setMessage("Are you sure you wish to cancel this class?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
}