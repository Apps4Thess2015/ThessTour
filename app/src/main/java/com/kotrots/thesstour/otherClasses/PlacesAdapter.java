package com.kotrots.thesstour.otherClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kotrots.thesstour.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kostas on 23/12/2015.
 */
public class PlacesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Place> places;

    int hours = Integer.valueOf(getCurentTime().split(":")[0]);
    int minutes = Integer.valueOf(getCurentTime().split(":")[1]);

    public PlacesAdapter(Context context, ArrayList<Place> places){
        this.context = context;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return places.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_places_item, null);
        }
        TextView txtVw_placeName = (TextView) convertView.findViewById(R.id.txtVw_placeName);
        TextView txtVw_placeInfo = (TextView) convertView.findViewById(R.id.txtVw_PlaceInfo);
        ImageView imgVw_placeImg = (ImageView) convertView.findViewById(R.id.imgVw_PlaceImg);
        TextView txtVw_arriveTime = (TextView) convertView.findViewById(R.id.txtVw_arriveTime);
        TextView txtVw_spendTime = (TextView) convertView.findViewById(R.id.txtVw_spendTime);
        TextView txtVw_leaveTime = (TextView) convertView.findViewById(R.id.txtVw_leaveTime);
        TextView txtVw_placeId = (TextView) convertView.findViewById(R.id.txtVw_PlaceId);

        Place place_pos = places.get(position);

        txtVw_placeName.setText(place_pos.getName());
        txtVw_placeInfo.setText(place_pos.getInfo());
        txtVw_arriveTime.setText(String.valueOf(hours < 10 ? "0" + hours : hours) + ":" + String.valueOf(minutes < 10 ? "0" + minutes : minutes));
        txtVw_spendTime.setText(String.valueOf((int) (Double.valueOf(place_pos.getTime()) * 60)) + " Min");
        txtVw_leaveTime.setText(getTime(hours, minutes, (int) (Double.valueOf(place_pos.getTime()) * 60)));
        txtVw_placeId.setText(place_pos.getId());

        String imgUrl = "http://thesstour.padahoo.com/imgs/" + place_pos.getName() + ".png";
        Log.d("myLog", imgUrl);
        Picasso.with(context).load(imgUrl).into(imgVw_placeImg);

        return convertView;
    }

    private String getCurentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return ( sdf.format(cal.getTime()) );
    }

    private String getTime(int hours, int minutes, int time){

        minutes = minutes + time;

        if(minutes>=60){
            minutes = minutes - 60;
            hours = hours + 1;
        }

        if(hours>=24){
            hours = 0;
        }

        this.hours = hours;
        this.minutes = minutes;

        return String.valueOf(hours < 10 ? "0" + hours : hours) + ":" + String.valueOf(minutes < 10 ? "0" + minutes : minutes);
    }




}
