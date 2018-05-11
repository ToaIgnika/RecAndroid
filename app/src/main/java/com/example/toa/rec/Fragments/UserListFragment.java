package com.example.toa.rec.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toa.rec.Event;
import com.example.toa.rec.EventAdapter;
import com.example.toa.rec.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;


    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new EventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareEventData();


        return view;
    }

    private void prepareEventData() {

        /*
        Event movie = new Event(100,"Mad Max: Fury Road", "Action & Adventure");
        eventList.add(movie);

        movie = new Event(100,"Inside Out", "Animation, Kids & Family");
        eventList.add(movie);

        movie = new Event(100,"Star Wars: Episode VII - The Force Awakens", "Action");
        eventList.add(movie);

        movie = new Event(100,"Shaun the Sheep", "Animation");
        eventList.add(movie);*/


        mAdapter.notifyDataSetChanged();
    }

}
