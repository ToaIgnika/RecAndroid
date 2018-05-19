package com.example.toa.rec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.toa.rec.ObjectModels.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * EventViewer holds the event adapter
 */
public class EventViewer extends AppCompatActivity {
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new EventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareEventData();
    }

    private void prepareEventData() {
        mAdapter.notifyDataSetChanged();
    }

}
