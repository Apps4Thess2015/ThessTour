package com.kotrots.thesstour.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.kotrots.thesstour.R;
import com.kotrots.thesstour.serverConnection.AsyncHttpPost;
import com.kotrots.thesstour.serverConnection.AsyncResponse;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    Button btn_findPlaces;
    Spinner spner_youAre;
    Spinner spner_hours;

    String locationLat = "40.63702";
    String locationLon = "22.92204";

    HashMap<String, String> postData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getString(R.string.help_us));

        String[] hours = getResources().getStringArray(R.array.time);
        String[] youAre = getResources().getStringArray(R.array.youare);

        btn_findPlaces = (Button) findViewById(R.id.main_btn_findPlaces);
        spner_hours = (Spinner) findViewById(R.id.spinner_time);
        spner_youAre = (Spinner) findViewById(R.id.spinner_youare);

        ArrayAdapter<String> spnerTimeAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spnr_time_item, hours);
        spner_hours.setAdapter(spnerTimeAdapter);

        ArrayAdapter<String> spnerYouAreAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spnr_youare_item, youAre);
        spner_youAre.setAdapter(spnerYouAreAdapter);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location!=null) {
                    locationLat = String.valueOf(location.getLatitude());
                    locationLon = String.valueOf(location.getLongitude());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this, R.string.turn_on_gps, Toast.LENGTH_LONG).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        btn_findPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spner_youAre.getSelectedItem().toString();

                postData.put("lang", getResources().getString(R.string.lang));
                postData.put("hours", spner_hours.getSelectedItem().toString());
                postData.put("youAre", spner_youAre.getSelectedItem().toString());

                //TODO: Must get location from gps
                postData.put("$locationLat", locationLat);

                //TODO: Must get location from gps
                postData.put("$locationLon", locationLon);

                AsyncHttpPost asyncHttpPost = new AsyncHttpPost(postData);
                asyncHttpPost.delegate = MainActivity.this;
                asyncHttpPost.execute("getPlan");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_about){
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        else if(id == R.id.menu_help) {
            startActivity(new Intent(MainActivity.this, HelpActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output) {
        Intent intent = new Intent(MainActivity.this, VisitPlacesActivity.class);
        intent.putExtra("result", output);
        startActivity(intent);
    }

}
