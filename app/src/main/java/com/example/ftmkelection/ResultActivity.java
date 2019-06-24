package com.example.ftmkelection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private static final String candidateURL = "http://172.20.10.3/ftmkelection/result.php";
    List<Vote> voteList;
    RecyclerView recyclerViewVote;
    VoteAdapter voteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerViewVote = findViewById(R.id.recyclerViewResult);
        recyclerViewVote.setHasFixedSize(true);
        recyclerViewVote.setLayoutManager(new LinearLayoutManager(this));

        voteList = new ArrayList<>();
        loadVote();
        voteAdapter = new VoteAdapter(ResultActivity.this, voteList);
        recyclerViewVote.setAdapter(voteAdapter);

    }

    private void loadVote() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, candidateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray vote = new JSONArray(response);
                    for(int i=0; i<vote.length(); i++){
                        JSONObject voteObj = vote.getJSONObject(i);

                        String candidateID = voteObj.getString("candidateID");
                        int voting = voteObj.getInt("voting");

                        Vote createVote = new Vote(candidateID, voting);
                        voteList.add(createVote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenu = item.getItemId();
        Intent intent = null;
        if(idMenu == R.id.menu_ftmk){
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
