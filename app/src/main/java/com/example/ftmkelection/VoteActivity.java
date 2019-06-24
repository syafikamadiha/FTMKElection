package com.example.ftmkelection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteActivity extends AppCompatActivity {

    //ip address = 172.20.10.3
    //http://172.20.10.3/ftmkelection/myapi.php

    private static final String candidateURL = "http://172.20.10.3/ftmkelection/myapi.php";
    private static final String voteURL = "http://172.20.10.3/ftmkelection/insertVote.php";
    RecyclerView recyclerViewCandidate;
    CandidateAdapter candidateAdapter;
    List<Candidate> candidateList;
    EditText editTextStudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        recyclerViewCandidate = findViewById(R.id.recyclerView);
        recyclerViewCandidate.setHasFixedSize(true);

        editTextStudentID = findViewById(R.id.editTextMatricNo);
        recyclerViewCandidate.setLayoutManager(new LinearLayoutManager(this));

        candidateList = new ArrayList<>();
        /*candidateList.add(
                new Candidate(
                        "candidate1",
                        "Syafika Madiha binti Suhaini",
                        R.drawable.ftmk_logo
                )
        );
        candidateList.add(
                new Candidate(
                        "candidate2",
                        "Izyan Noraisyah binti Darus",
                        R.drawable.ftmk_logo
                )
        );*/

        loadCandidate();

        candidateAdapter = new CandidateAdapter(VoteActivity.this, candidateList);
        recyclerViewCandidate.setAdapter(candidateAdapter);
    }

    private void loadCandidate() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, candidateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray candidate = new JSONArray(response);
                    for(int i=0; i<candidate.length(); i++){
                        JSONObject candidateObj = candidate.getJSONObject(i);

                        String candidateID = candidateObj.getString("candidateID");
                        String candidateName = candidateObj.getString("candidateName");
                        String imageID = candidateObj.getString("imageID");

                        Candidate createCandidate = new Candidate(candidateID, candidateName, imageID);
                        candidateList.add(createCandidate);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VoteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        recyclerViewCandidate.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewCandidate, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Candidate cand = candidateList.get(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VoteActivity.this);
                alertDialogBuilder.setMessage("Are you sure you want to vote " + cand.getCandidateName() + " with ID " + cand.getCandidateID() + "?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        if(TextUtils.isEmpty(editTextStudentID.getText()))
                                        {
                                            Toast.makeText(getApplicationContext(),  "Please enter your matric no first!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            fnSave(cand.getCandidateID());
                                        }
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void fnSave(final String candidateID) {

        final Vote voting = new Vote(editTextStudentID.getText().toString().trim(), candidateID);

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, voteURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),  "You have successfully vote " + candidateID + "!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VoteActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Unable to make connection to web service", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("studentID", voting.getStudentID());
                params.put("candidateID", voting.getCandidateID());
                return params;
            }
        };
        //RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        //requestQueue1.add(stringRequest);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
