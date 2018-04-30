package com.example.toa.rec.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.toa.rec.MainActivity;
import com.example.toa.rec.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {


    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String [] classes = {"Pilates","Spin","Yoga",};
        String [] instructors = {"John", "Jake", "Jimmy"};


        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        Spinner courseSpinner = view.findViewById(R.id.CourseDropDown);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, classes);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        courseSpinner.setAdapter(courseAdapter);


        Spinner instructorSpinner = view.findViewById(R.id.InstructorDropDown);
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, instructors);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        instructorSpinner.setAdapter(instructorAdapter);


        // Inflate the layout for this fragment


        return view;



    }


}
