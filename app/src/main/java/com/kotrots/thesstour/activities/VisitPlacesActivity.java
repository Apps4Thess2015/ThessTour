package com.kotrots.thesstour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kotrots.thesstour.R;
import com.kotrots.thesstour.otherClasses.JsonToObjects;
import com.kotrots.thesstour.otherClasses.Place;
import com.kotrots.thesstour.otherClasses.PlacesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisitPlacesActivity extends AppCompatActivity {

    ListView list;
    ArrayList<Place> arrayList;
    Button btn_goToMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_places);

        arrayList = new ArrayList<>();

        setTitle(getResources().getString(R.string.your_plan));

        final String result = getIntent().getExtras().getString("result");

        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList.add(JsonToObjects.jsonToPlace(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        list = (ListView) findViewById(R.id.listView_places);
        btn_goToMap = (Button) findViewById(R.id.btn_goToMap);

        PlacesAdapter adapter = new PlacesAdapter(VisitPlacesActivity.this, arrayList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtVw_id = (TextView) view.findViewById(R.id.txtVw_PlaceId);
                Intent intent = new Intent(VisitPlacesActivity.this, DetailsActivity.class);
                intent.putExtra("id", txtVw_id.getText().toString());
                startActivity(intent);
            }
        });

        btn_goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitPlacesActivity.this, MapActivity.class);
                intent.putExtra("places", result);
                startActivity(intent);
            }
        });

    }
}
