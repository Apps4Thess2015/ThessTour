package com.kotrots.thesstour.otherClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kostas on 23/12/2015.
 */
public class JsonToObjects {

    public static Place jsonToPlace(JSONObject jsonObject) throws JSONException {
        Place place = new Place();
        place.setId(jsonObject.getString("id"));
        place.setName(jsonObject.getString("name"));
        place.setLangitude(jsonObject.getString("langitude"));
        place.setLongitude(jsonObject.getString("longitude"));
        place.setInfo(jsonObject.getString("info"));
        place.setTime(jsonObject.getString("time"));
        place.setDetails(jsonObject.getString("details"));

        return place;
    }
}
