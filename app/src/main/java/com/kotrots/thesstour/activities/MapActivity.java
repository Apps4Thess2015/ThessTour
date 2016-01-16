package com.kotrots.thesstour.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kotrots.thesstour.R;
import com.kotrots.thesstour.otherClasses.JsonToObjects;
import com.kotrots.thesstour.otherClasses.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    ArrayList<Place> arrayList = new ArrayList<>();
    boolean near = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String places = getIntent().getExtras().getString("places");

        try {
            JSONArray jsonArray = new JSONArray(places);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList.add(JsonToObjects.jsonToPlace(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpMapIfNeeded();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            this.onLocationChanged(null);
        }
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
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
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomBy(15.0f));
        for (Place place : arrayList) {

            LatLng latLng = new LatLng(Float.valueOf(place.getLangitude()), Float.valueOf(place.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
            for(Place place : arrayList ){
                //TODO: Check if you visit a place
                Location placeLocation=new Location("placelocation");
                placeLocation.setLatitude(Double.parseDouble(place.getLangitude()));
                placeLocation.setLongitude(Double.parseDouble(place.getLongitude()));
                if(location.distanceTo(placeLocation)<25 && !near){

                    Intent intent = new Intent(this, DetailsActivity.class);
                    intent.putExtra("id", place.getId());
                    PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
                    Notification notification = new Notification.Builder(MapActivity.this)
                            .setContentTitle(place.getName())
                            .setContentText(place.getInfo())
                            .setSmallIcon(R.drawable.notif_icon)
                            .setSound(Uri.parse(String.valueOf(R.raw.notif_sound)))
                            .setContentIntent(pIntent)
                            .setAutoCancel(true).build();

                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(0, notification);

                    near = true;
                }
                else {
                    near = false;
                }
            }
        }
        else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.63282, 22.94695), 15.0f));
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

    }
}
