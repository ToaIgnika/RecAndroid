package com.example.toa.rec.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.toa.rec.Api;
import com.example.toa.rec.Dialogs.EventDetailsDialog;
import com.example.toa.rec.Dialogs.LogInDialog;
import com.example.toa.rec.LoginHandler;
import com.example.toa.rec.MainActivity;
import com.example.toa.rec.R;
import com.example.toa.rec.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    Spinner courseSpinner;
    Spinner instructorSpinner;
    RatingBar ratingBar;
    EditText commentBox;


    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String [] classes = {"Pilates","Spin","Yoga",};
        String [] instructors = {"John", "Jake", "Jimmy"};




        final View view = inflater.inflate(R.layout.fragment_survey, container, false);

        ratingBar = view.findViewById(R.id.RatingBar);
        commentBox = view.findViewById(R.id.CommentBox);
        courseSpinner = view.findViewById(R.id.CourseDropDown);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, classes);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        courseSpinner.setAdapter(courseAdapter);


        instructorSpinner = view.findViewById(R.id.InstructorDropDown);
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, instructors);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        instructorSpinner.setAdapter(instructorAdapter);


        // Inflate the layout for this fragment

        Button submitButton = view.findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent myIntent = new Intent(view.getContext(), agones.class);
                //startActivityForResult(myIntent, 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you'd like to submit this review?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                createReview();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });

        Button loginButton = view.findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInDialog d = new LogInDialog(getActivity(), v.getId());
                d.show();

                LoginHandler loginHandler = new LoginHandler();
                //loginHandler.saveLoginInfo(view.getContext(),"test", "test", getActivity());
                System.out.println("The stored username is: " + loginHandler.getEmail(view.getContext()));

            }
        });



        return view;



    }

    /*ALEX: Creates a review object and puts it in the database*/
    private void createReview() {
      //
        System.out.println("creating review");
        String courseName = courseSpinner.getSelectedItem().toString().trim();
        String instructorName = instructorSpinner.getSelectedItem().toString().trim();
        double rating = ratingBar.getRating();

        String reviewText  = commentBox.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("courseName", courseName);
        params.put("instructorName", instructorName);
        params.put("reviewText", reviewText);
        params.put("starRating", String.valueOf(rating));



        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_REVIEW, params, CODE_POST_REQUEST);
        request.execute();

        System.out.println("created");
    }

    /*ALEX: Performs a request using the php scripts to the databsae*/
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
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


}
