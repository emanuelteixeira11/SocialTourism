package com.tourism.social.socialtourism.utils.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by emanuelteixeira on 02/03/15.
 */
public class ListPlaces implements Serializable{

    private ArrayList<Place> places;
    private String nextPageToken = "";
    private double myLat, myLng;

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public double getMyLat() {
        return myLat;
    }

    public double getMyLng() {
        return myLng;
    }

    public ListPlaces() {
        this.places = new ArrayList<Place>();
    }

    public void setNewPlacesList(JSONObject localsJson, double my_lat, double my_lng){
        this.myLat = my_lat;
        this.myLng = my_lng;
        try {
            if (localsJson.has("next_page_token"))
                this.nextPageToken = localsJson.getString("next_page_token");
            JSONArray jsonArray = localsJson.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                this.places.add(new Place(object, my_lat, my_lng));
            }
            Collections.sort(this.places);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Place> getPlaces() {
        return this.places;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
