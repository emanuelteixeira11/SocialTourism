package com.tourism.social.socialtourism.utils.AsynkTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.tourism.social.socialtourism.utils.Place.GooglePlacesUrlEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by emanuelteixeira on 06/03/15.
 */
public class GooglePlacesPhotoRequest extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = GooglePlacesUrlEncoder.getPhotoEncoded(strings[0], Integer.parseInt(strings[1]));
        Bitmap bitmap = null;
        try {
            InputStream is = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() == 400)
                return null;
            is = httpEntity.getContent();
            bitmap = BitmapFactory.decodeStream((InputStream) is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
