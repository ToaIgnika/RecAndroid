package com.example.toa.rec.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.toa.rec.Api;
import com.example.toa.rec.ObjectModels.Instructor;
import com.example.toa.rec.ObjectModels.RecClass;
import com.example.toa.rec.R;
import com.example.toa.rec.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.INPUT_METHOD_SERVICE;


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

    ArrayList<RecClass> classList;
    ArrayAdapter<RecClass> courseAdapter;


    ArrayList<Instructor> instructorList;
    ArrayAdapter<Instructor> instructorAdapter;


    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String [] classes = {"Pilates","Spin","Yoga",};
        String [] instructors = {"John", "Jake", "Jimmy"};

        classList = new ArrayList<RecClass>();
        instructorList = new ArrayList<Instructor>();


        final View view = inflater.inflate(R.layout.fragment_survey, container, false);

        ratingBar = view.findViewById(R.id.RatingBar);
        commentBox = view.findViewById(R.id.CommentBox);

        courseSpinner = view.findViewById(R.id.CourseDropDown);
        courseAdapter = new ArrayAdapter<RecClass>(this.getActivity(), android.R.layout.simple_spinner_item, classList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        courseSpinner.setAdapter(courseAdapter);


        instructorSpinner = view.findViewById(R.id.InstructorDropDown);
        instructorAdapter = new ArrayAdapter<Instructor>(this.getActivity(), android.R.layout.simple_spinner_item, instructorList);
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


        PerformNetworkRequest pn = new PerformNetworkRequest(Api.URL_GET_CLASSES, null, Api.CODE_GET_REQUEST);
        pn.execute();

        PerformNetworkRequest pn2 = new PerformNetworkRequest(Api.URL_GET_INSTRUCTORS, null, Api.CODE_GET_REQUEST);
        pn2.execute();

        courseSpinner.setFocusable(false);
        instructorSpinner.setFocusable(false);
        commentBox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    hideKeyboard(v);
                    return true; //this is required to stop sending key event to parent
                }
                return false;
            }
        });

        commentBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        return view;
    }

    private void hideKeyboard (View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null)
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*ALEX: Creates a review object and puts it in the database*/
    private void createReview() {
        //
        System.out.println("creating review");
        //String courseName = courseSpinner.getSelectedItem().toString().trim();
        //String instructorName = instructorSpinner.getSelectedItem().toString().trim();
        //SubCategory sc = dataAdapter.getItem(position);

        Instructor inst = instructorAdapter.getItem(instructorSpinner.getSelectedItemPosition());
        String instructorID = inst.getInstructorID();

        RecClass rc = courseAdapter.getItem(courseSpinner.getSelectedItemPosition());
        String classID = rc.getClassID();


        double rating = ratingBar.getRating();

        String reviewText  = commentBox.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("classID", classID);
        params.put("instructorID", instructorID);
        params.put("reviewText", reviewText);
        params.put("starRating", String.valueOf(rating));



        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_REVIEW, params, CODE_POST_REQUEST);
        request.execute();
        clearForm();
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
                    if(url == Api.URL_GET_CLASSES) {
                        prepareClassData(object);
                    }

                    if(url == Api.URL_GET_INSTRUCTORS) {
                        prepareInstructorData(object);
                    }
                } else {
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


    private void prepareClassData(JSONObject object) throws JSONException {

        classList.clear();

        JSONArray arr = object.getJSONArray("classes");
        //iterate thru them and add

        System.out.println("The object is" + object);

        System.out.println("The array is" + arr);
        System.out.println("The length is" + arr.length());
        // System.out.println("The first element is" + arr.get(0));

        for(int i = 0; i < arr.length(); i++) {
            System.out.println("There is an event" + arr.get(i));
            RecClass recClass = new RecClass();
            JSONObject classObject = (JSONObject) arr.get(i);
            recClass.fromJson(classObject);
            classList.add(recClass);
        }
        courseAdapter.notifyDataSetChanged();
    }

    private void prepareInstructorData(JSONObject object) throws JSONException {

        instructorList.clear();

        JSONArray arr = object.getJSONArray("instructors");
        //iterate thru them and add

        for(int i = 0; i < arr.length(); i++) {
            System.out.println("There is an event" + arr.get(i));
            Instructor inst = new Instructor();
            JSONObject classObject = (JSONObject) arr.get(i);
            inst.fromJson(classObject);
            instructorList.add(inst);
        }
        instructorAdapter.notifyDataSetChanged();
    }

    private void clearForm() {
        EditText et = (EditText) getView().findViewById(R.id.CommentBox);
        et.setText("");
    }
}
