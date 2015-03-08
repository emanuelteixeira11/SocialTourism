package com.tourism.social.socialtourism.utils.Place;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by emanuelteixeira on 05/03/15.
 */
public class Review implements Serializable {
    private double rating;
    private String authorName, authorUrl, text;
    private Calendar date;

    public Review(JSONObject jsonObject) {
        try {
            this.rating = jsonObject.getJSONArray("aspects").getJSONObject(0).getDouble("rating");
            this.authorName = jsonObject.getString("author_name");
            if (jsonObject.has("author_url"))
                this.authorUrl = jsonObject.getString("author_url");
            this.text = jsonObject.getString("text");
            date = new GregorianCalendar();
            date.setTimeInMillis(jsonObject.getLong("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public double getRating() {
        return rating;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format1.format(date.getTime());
    }
}
