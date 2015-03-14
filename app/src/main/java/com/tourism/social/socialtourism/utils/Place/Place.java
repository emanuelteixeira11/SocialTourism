package com.tourism.social.socialtourism.utils.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by emanuelteixeira on 02/03/15.
 */
public class Place implements Comparable<Place>, Serializable {
    private String id, name, reference;
    private String adress;
    private double rating = 0.0;
    private double lat;
    private double lng;
    private double distance;
    private double my_lat;
    private double my_lng;
    private boolean opnenNow;
    private int priceLevel = -1;
    private boolean hasDetails = false;
    private ArrayList<GoogleTypes> types;
    private String phoneNumber;
    private String website;
    private ArrayList<Review> listReviews;
    private boolean hasPhotos;
    private boolean visible = true;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean hasPhotos() {
        return hasPhotos;
    }

    public String getJsonPhotosArray() {
        return jsonPhotosArray;
    }

    private String jsonPhotosArray;

    public double getMy_lat() {
        return my_lat;
    }

    public double getMy_lng() {
        return my_lng;
    }

    public Place(JSONObject local, double my_lat, double my_lng){
        this.my_lat = my_lat;
        this.my_lng = my_lng;
        try {
            listReviews = new ArrayList<Review>();
            types = new ArrayList<GoogleTypes>();
            lat = local.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            lng = local.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            if (local.has("rating")){
                rating = local.getDouble("rating");
            }
            id = local.getString("id");
            name = local.getString("name");
            reference = local.getString("reference");
            if (local.has("opening_hours"))
                opnenNow = local.getJSONObject("opening_hours").getBoolean("open_now");
            if (local.has("price_level"))
                priceLevel = local.getInt("price_level");
            if (local.has("formatted_address"))
                adress = local.getString("formatted_address");
            else
                adress = local.getString("vicinity");
            JSONArray typesArray = local.getJSONArray("types");
            for (int i = 0; i < typesArray.length(); i++) {
                try {
                    types.add(GoogleTypes.valueOf(typesArray.get(i).toString().toUpperCase()));
                }catch (Exception ex)
                {
                    break;
                }
            }
            this.distance = this.calculateDistance(lat, lng);
            this.hasDetails();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private double calculateDistance(double lat, double lng) {
        double earthRadius = 6372795; //meters
        double dLat = Math.toRadians(getMy_lat()-lat);
        double dLng = Math.toRadians(getMy_lng()-lng);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(lng)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }

    public double getDistance() {
        return distance;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getReference() {
        return reference;
    }

    public ArrayList<GoogleTypes> getTypes() {
        return types;
    }

    public boolean isOpnenNow() {
        return opnenNow;
    }

    public String getAdress() {
        return adress;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public boolean hasDetails() {
        return hasDetails;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void addDetails(JSONObject object){
        this.hasDetails = true;
        try {
            JSONObject result = object.getJSONObject("result");
            this.adress = result.getString("formatted_address");
            this.phoneNumber = result.getString("international_phone_number");
            if (result.has("website"))
                this.website = result.getString("website");
            else
                this.website = result.getString("url");

            if (result.has("reviews"))
            {
                JSONArray jsonArray = result.getJSONArray("reviews");
                if (jsonArray.length() > 0)
                {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        this.listReviews.add(new Review(jsonArray.getJSONObject(i)));
                    }
                }
            }
            if (result.has("photos")){
                this.hasPhotos = true;
                this.jsonPhotosArray = result.getJSONArray("photos").toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Place place) {
        if (this.getDistance() > place.getDistance())
        {
            return 1;
        }
        else if (this.getDistance() < place.getDistance())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        if (Double.compare(place.distance, distance) != 0) return false;
        if (Double.compare(place.lat, lat) != 0) return false;
        if (Double.compare(place.lng, lng) != 0) return false;
        if (Double.compare(place.rating, rating) != 0) return false;
        if (!id.equals(place.id)) return false;
        if (!name.equals(place.name)) return false;
        if (!reference.equals(place.reference)) return false;

        return true;
    }

    public ArrayList<Review> getListReviews() {
        return listReviews;
    }
}
