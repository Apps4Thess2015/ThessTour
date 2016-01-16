package com.kotrots.thesstour.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.kotrots.thesstour.R;
import com.kotrots.thesstour.otherClasses.JsonToObjects;
import com.kotrots.thesstour.otherClasses.Place;
import com.kotrots.thesstour.serverConnection.AsyncHttpPost;
import com.kotrots.thesstour.serverConnection.AsyncResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity implements AsyncResponse{

    HashMap<String, String> postData = new HashMap<>();
    TextView txtVw_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtVw_details = (TextView) findViewById(R.id.txtVw_details);

        postData.put("id", getIntent().getExtras().getString("id"));
        postData.put("lang", getResources().getString(R.string.lang));

        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(postData);
        asyncHttpPost.delegate = DetailsActivity.this;
        asyncHttpPost.execute("getDetails");

    }

    @Override
    public void processFinish(String output) {
        Place place = new Place();

        try {
            JSONObject jsonObject = new JSONObject(output);
            place = JsonToObjects.jsonToPlace(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("myLog", "Error");
        }

        setTitle(place.getName());
        txtVw_details.setText(place.getDetails());
    }
}
